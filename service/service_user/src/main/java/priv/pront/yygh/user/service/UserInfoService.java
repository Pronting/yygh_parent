package priv.pront.yygh.user.service;

import priv.pront.yygh.vo.user.LoginVo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-07 09:57
 */
public interface UserInfoService {
    Map<String, Object> loginUser(LoginVo loginVo);
}
