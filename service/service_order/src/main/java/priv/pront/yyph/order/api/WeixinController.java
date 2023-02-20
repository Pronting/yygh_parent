package priv.pront.yyph.order.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.yygh.common.result.Result;
import priv.pront.yyph.order.service.WeixinService;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-20 20:03
 */
@Api(tags = "微信相关的接口")
@RestController
@RequestMapping("/api/order/weixin")
public class WeixinController {

    @Autowired
    private WeixinService weixinService;

    @ApiOperation("生成微信支付的二维码")
    @GetMapping("createNative/{orderId}")
    public Result createNative(@PathVariable Long orderId){
        Map<String,Object> map = weixinService.createNative(orderId);
        return Result.ok(map);
    }



}
