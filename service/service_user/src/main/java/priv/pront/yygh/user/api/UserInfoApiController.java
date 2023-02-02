package priv.pront.yygh.user.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.common.util.AuthContextHolder;
import priv.pront.yygh.model.user.UserInfo;
import priv.pront.yygh.user.service.UserInfoService;
import priv.pront.yygh.vo.user.LoginVo;
import priv.pront.yygh.vo.user.UserAuthVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-07 09:56
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("用户通过手机号登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        Map<String, Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

    @ApiOperation("用户认证")
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
//        第一个参数是用户的id,第二个参数是认证数据的vo对象
        userInfoService.serAuth(AuthContextHolder.getUserId(request), userAuthVo);
        return Result.ok();
    }

    @ApiOperation("根据用户id查询用户信息")
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo user = userInfoService.getById(userId);
        return Result.ok(user);
    }
}
