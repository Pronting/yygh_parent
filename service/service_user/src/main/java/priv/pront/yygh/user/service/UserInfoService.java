package priv.pront.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.user.UserInfo;
import priv.pront.yygh.vo.user.LoginVo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-07 09:57
 */
public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> loginUser(LoginVo loginVo);

    /**
     * 判断用户是否存在
     * @return
     */
    UserInfo selectWxInfoOpenId(String openid);
}
