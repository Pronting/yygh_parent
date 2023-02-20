package priv.pront.yyph.order.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import priv.pront.yygh.enums.PaymentTypeEnum;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yyph.order.service.OrderService;
import priv.pront.yyph.order.service.PaymentService;
import priv.pront.yyph.order.service.WeixinService;
import priv.pront.yyph.order.utils.ConstantPropertiesUtil;
import priv.pront.yyph.order.utils.HttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-20 20:04
 */
@Service
public class WeixinServiceImpl implements WeixinService {

    @Autowired
    private PaymentService paymentService;


    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> createNative(Long orderId) {
        try {
//            从redis获取数据
            Map payMap = (Map) redisTemplate.opsForValue().get(orderId.toString());
            if(payMap != null){
                return payMap;
            }
//        根据orderId 获取订单的信息
            OrderInfo order = orderService.getById(orderId);
//        向支付记录表中添加信息
            paymentService.savePaymentInfo(order, PaymentTypeEnum.WEIXIN.getStatus());
//        设置参数，调用微信生成二维码接口
//        把参数转换为XML格式，使用商户KEY进行加密
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtil.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtil.PARTNER);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            String body = order.getReserveDate() + "就诊" + order.getDepname();
            paramMap.put("body", body);
            paramMap.put("out_trade_no", order.getOutTradeNo());
            //paramMap.put("total_fee", order.getAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee", "1");// 为了测试，统一使用0.0元
            paramMap.put("spbill_create_ip", "127.0.0.1");
            paramMap.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            paramMap.put("trade_type", "NATIVE");
//        调用微信生成二维码接口
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
//        设置map里面的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtil.PARTNERKEY));
//           支持https请求
            client.setHttps(true);
            client.post();
//            微信那边返回相关数据
            String xml = client.getContent();
//            将xml转换为map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println("resultMap =" + resultMap);
//            封装返回结果集
            Map map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("totalFee", order.getAmount());
            map.put("resultCode", resultMap.get("result_code"));
            map.put("codeUrl", resultMap.get("code_url"));  //二维码的地址
            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            if (null != resultMap.get("result_code")) {
                redisTemplate.opsForValue().set(orderId.toString(), map, 120, TimeUnit.MINUTES);
            }
            return map;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
