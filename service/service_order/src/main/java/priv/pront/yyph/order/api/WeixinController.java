package priv.pront.yyph.order.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.enums.PaymentTypeEnum;
import priv.pront.yyph.order.service.PaymentService;
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

    @Autowired
    private PaymentService paymentService;

    @ApiOperation("生成微信支付的二维码")
    @GetMapping("createNative/{orderId}")
    public Result createNative(@PathVariable Long orderId){
        Map<String,Object> map = weixinService.createNative(orderId);
        return Result.ok(map);
    }

    @ApiOperation("查询支付状态")
    @GetMapping("queryPayStatus/{orderId}")
    public Result queryPayStatus(@PathVariable Long orderId) {
//        查询状态，调用微信官方接口进行查询
        Map<String, String> resultMap = weixinService.queryPayStatus(orderId);
        if (resultMap == null) {
            return Result.fail().message("支付出错");
        }
//        支付判断
        if ("SUCCESS".equals(resultMap.get("trade_state"))) { //成功
            //更改订单状态，处理支付结果
            String out_trade_no = resultMap.get("out_trade_no"); // 得到订单编码
            paymentService.paySuccess(out_trade_no, resultMap);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中");


    }



}
