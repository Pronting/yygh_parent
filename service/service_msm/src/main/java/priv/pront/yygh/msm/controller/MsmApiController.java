package priv.pront.yygh.msm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.msm.service.MsmService;
import priv.pront.yygh.msm.utils.RandomUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-08 10:21
 */
@Api(tags = "手机验证码API")
@RestController
@RequestMapping("/api/msm")
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("发送手机验证码")
    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone) {
//        key -->手机号  value --->验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
//         从redis里面获取到验证码，如果获取到，返回ok
            return Result.ok();
        }
//        如果从redis获取不到，生成验证码，通过整合短信服务进行发送
        code = RandomUtil.getSixBitRandom();
        boolean isSend = msmService.send(phone, code);
        if (isSend) {
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.ok();
        }else{
            return Result.fail().message("发送短信失败");
        }
//        生成的验证码放到redis里面，设置有效时间

    }

}
