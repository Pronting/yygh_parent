package priv.pront.yygh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import priv.pront.yygh.common.exception.YyghException;
import priv.pront.yygh.common.helper.JwtHelper;
import priv.pront.yygh.common.result.ResultCodeEnum;
import priv.pront.yygh.model.user.UserInfo;
import priv.pront.yygh.user.mapper.UserInfoMapper;
import priv.pront.yygh.user.service.UserInfoService;
import priv.pront.yygh.vo.user.LoginVo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-07 09:58
 */

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {
//        从loginVo获取到输入的手机号和验证码
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();

//        判断手机号和验证码是否为空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

//        判断手机验证码和输入的验证码是否一致
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (!code.equals(redisCode)) {
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }
//        判断是否是第一次登录，根据手机号查询数据库，如果不存在相同的手机号就是第一次登录
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        UserInfo userInfo = baseMapper.selectOne(wrapper);
        if (userInfo == null) {
//            第一次使用这个手机号码，添加到数据库中
            userInfo = new UserInfo();
            userInfo.setName("");
            userInfo.setPhone(phone);
            userInfo.setStatus(1);
            baseMapper.insert(userInfo);
        }
//        校验是否被禁用
        if (userInfo.getStatus() == 0) {
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }
//        不是第一次，直接登录
//        TODO 记录登录
//        返回登录信息、用户名、token
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);
//        JWT Token的生成
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);
        return map;
    }
}
