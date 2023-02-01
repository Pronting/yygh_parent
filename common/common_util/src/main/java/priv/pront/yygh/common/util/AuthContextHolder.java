package priv.pront.yygh.common.util;

import priv.pront.yygh.common.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 获取当前用户信息的工具类
 * @Author: pront
 * @Time:2023-02-01 18:13
 */
public class AuthContextHolder {

    /**
     * 获取当前用户id
     *
     * @param request
     * @return
     */
    public static Long getUserId(HttpServletRequest request) {
//        从header中获取token
        String token = request.getHeader("token");
//        通过JWT获取userId
        return JwtHelper.getUserId(token);
    }

    /**
     * 获取当前用户名称
     *
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        String token = request.getHeader("token");
        return JwtHelper.getUserName(token);
    }
}
