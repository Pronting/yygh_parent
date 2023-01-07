package priv.pront.yygh.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.user.service.UserInfoService;
import priv.pront.yygh.vo.user.LoginVo;

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

    @ApiOperation("用户手机号登录接口")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        Map<String, Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

}
