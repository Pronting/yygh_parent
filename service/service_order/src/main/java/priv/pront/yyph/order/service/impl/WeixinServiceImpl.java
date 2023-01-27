package priv.pront.yyph.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import priv.pront.yygh.enums.PaymentTypeEnum;
import priv.pront.yygh.enums.RefundStatusEnum;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yygh.model.order.PaymentInfo;
import priv.pront.yygh.model.order.RefundInfo;
import priv.pront.yyph.order.service.OrderService;
import priv.pront.yyph.order.service.PaymentService;
import priv.pront.yyph.order.service.RefundInfoService;
import priv.pront.yyph.order.service.WeixinService;
import priv.pront.yyph.order.utils.ConstantPropertiesUtil;
import priv.pront.yyph.order.utils.HttpClient;

import java.util.Date;
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

    @Autowired
    private RefundInfoService refundInfoService;

    @Override
    public Map<String, Object> createNative(Long orderId) {
        try {
//            从redis获取数据
            Map payMap = (Map) redisTemplate.opsForValue().get(orderId.toString());
            if (payMap != null) {
                return payMap;
            }
//        根据orderId 获取订单的信息
            OrderInfo order = orderService.getById(orderId);
//        向支付记录表中添加信息
            paymentService.savePaymentInfo(order, PaymentTypeEnum.WEIXIN.getStatus());
//        设置参数，调用微信生成二维码接口
//        把参数转换为XML格式，使用商户KEY进行加密
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", "wx74862e0dfcf69954");
            paramMap.put("mch_id", "1558950191");
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
            System.out.println("value 注入的方式： " + ConstantPropertiesUtil.PARTNERKEY);  // fixme 属性注入不成功 只能通过手写地方式
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
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
            System.out.println(resultMap.get("code_url"));
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

    /**
     * 调用微信接口实现支付状态的查询
     *
     * @param orderId 订单编号
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(Long orderId) {
        try {
//            根据orderId 获取订单信息
            OrderInfo orderInfo = orderService.getById(orderId);
//            封装提交参数
            Map<String, String> paramMap = new HashMap();
            paramMap.put("appid", "wx74862e0dfcf69954");
            paramMap.put("mch_id", "1558950191");
            paramMap.put("out_trade_no", orderInfo.getOutTradeNo());
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
//            设置请求内容
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true); // 支持https请求
            client.post();
//            得到微信接口返回的数据  XML --> MAP
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            System.out.println("支付状态resultMap: " + resultMap);
//            将接口的数据进行返回显示
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 微信退款操作
     *
     * @param orderId
     * @return
     */
    @Override
    public Boolean refund(Long orderId) {
        try {
//        获取支付记录信息
            PaymentInfo paymentInfo = paymentService.getPaymentInfo(orderId, PaymentTypeEnum.WEIXIN.getStatus());
//        向退款记录表中添加信息
            RefundInfo refundInfo = refundInfoService.saveRefundInfo(paymentInfo);
//        判断当前订单数据是否已经退款
            if (refundInfo.getRefundStatus().intValue() == RefundStatusEnum.REFUND.getStatus().intValue()) {
                return true;
            }
//        调用微信接口实现退款
//        封装所需要的参数
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", "wx74862e0dfcf69954");       //公众账号ID
            paramMap.put("mch_id", "1558950191");   //商户编号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("transaction_id", paymentInfo.getTradeNo()); //微信订单号
            paramMap.put("out_trade_no", paymentInfo.getOutTradeNo()); //商户订单编号
            paramMap.put("out_refund_no", "tk" + paymentInfo.getOutTradeNo()); //商户退款单号
//       paramMap.put("total_fee",paymentInfoQuery.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
//       paramMap.put("refund_fee",paymentInfoQuery.getTotalAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee", "1");
            paramMap.put("refund_fee", "1");
            String paramXml = WXPayUtil.generateSignedXml(paramMap, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb");
//          设置微信调用接口内容
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/secapi/pay/refund");
            client.setXmlParam(paramXml);
            client.setHttps(true);
//            设置证书信息
            client.setCert(true);
            client.setCertPassword("C:\\Project\\priv\\pront\\IDEA\\yygh_parent\\service\\service_order\\src\\main\\resources\\cert\\apiclient_cert.p12");
            client.post();
//            接收返回数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            if ( WXPayConstants.SUCCESS.equalsIgnoreCase(resultMap.get("result_code"))) {
                refundInfo.setCallbackTime(new Date());
                refundInfo.setTradeNo(resultMap.get("refund_id"));
                refundInfo.setRefundStatus(RefundStatusEnum.REFUND.getStatus());
                refundInfo.setCallbackContent(JSONObject.toJSONString(resultMap));
                refundInfoService.updateById(refundInfo);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
