package priv.pront.yygh.user.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sun.deploy.net.URLEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.pront.yygh.common.helper.JwtHelper;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.model.user.UserInfo;
import priv.pront.yygh.user.service.UserInfoService;
import priv.pront.yygh.user.util.ConstantPropertiesUtil;
import priv.pront.yygh.user.util.HttpClientUtils;

import java.io.UnsupportedEncodingException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 微信操作的接口
 * @Author: pront
 * @Time:2023-01-31 13:10
 */
@Api(tags = "微信操作的接口")
@Controller
@RequestMapping("/api/ucenter/wx")
public class WeixinApiController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("返回生成二维码需要的参数")
    @ResponseBody
    @GetMapping("getLoginParam")
    public Result genQrConnection() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("appid", ConstantPropertiesUtil.WX_OPEN_APP_ID);
            map.put("scope", "snsapi_login");
            String wxOpenRedirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
            URLEncoder.encode(wxOpenRedirectUrl, "utf-8");
            map.put("redirect_uri", wxOpenRedirectUrl);
            map.put("state", System.currentTimeMillis() + "");
            return Result.ok(map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("微信扫描后,获取扫码人信息")
    @GetMapping("callback")
    public String callback(String code, String state) {
//      1。获取临时票据 code
        System.out.println("code:" + code);
//      2、使用code和appid以及appscrect换取access_token
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");

        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code);

        try {
//            使用HttpClient请求地址
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo: " + accessTokenInfo);
//            从返回字符串获取openid / access_token
            JSONObject jsonObject = JSONObject.parseObject(accessTokenInfo);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");

//            判断数据库中已存在微信扫码人信息，根据openid判断
            UserInfo userInfo = userInfoService.selectWxInfoOpenId(openid);
            if (userInfo == null) {
//            3、运用access_token、openid请求官方地址，获取扫码人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String resultInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultInfo: " + resultInfo);
                JSONObject resultUserInfoJson = JSONObject.parseObject(resultInfo);
//            解析用户信息
//            用户昵称
                String nickname = resultUserInfoJson.getString("nickname");
//            用户头像
                String headmgurl = resultUserInfoJson.getString("headmgurl");

//            获取扫码人信息添加到db
                userInfo = new UserInfo();
                userInfo.setName(nickname);
                userInfo.setOpenid(openid);
                userInfo.setStatus(1);

                userInfoService.save(userInfo);
            }

//            返回name和token的字符串
            Map<String, String> map = new HashMap<>();
            String name = userInfo.getName();
            if (StringUtils.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if (StringUtils.isEmpty(name)) {
                name = userInfo.getPhone();
            }
            map.put("name", name);
            /**
             * !判断userInfo是否有手机号，如果手机号为空，返回openid,如果手机号不为空，返回openid值为空字符串
             * !最后在前端进行判断，如果openid不为空，需要进行绑定手机号，反之不需要
             */
            if (StringUtils.isEmpty(userInfo.getPhone())) {
                map.put("openid", userInfo.getOpenid());
            } else {
                map.put("openid", "");
            }
//            使用JWT生成Token
            String token = JwtHelper.createToken(userInfo.getId(), name);
            map.put("token", token);
//            跳转到前端页面中去
            return "redirect:" +
                    ConstantPropertiesUtil.YYGH_BASE_URL +
                    "/weixin/callback?token=" + map.get("token") +
                    "&openid=" + map.get("openid") +
                    "&name=" + URLEncoder.encode(map.get("name"),"utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
