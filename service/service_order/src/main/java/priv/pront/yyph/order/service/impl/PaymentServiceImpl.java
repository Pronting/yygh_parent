package priv.pront.yyph.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.pront.yygh.common.helper.HttpRequestHelper;
import priv.pront.yygh.enums.OrderStatusEnum;
import priv.pront.yygh.enums.PaymentStatusEnum;
import priv.pront.yygh.enums.PaymentTypeEnum;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yygh.model.order.PaymentInfo;
import priv.pront.yygh.vo.order.SignInfoVo;
import priv.pront.yyph.hospital.client.HospitalFeignClient;
import priv.pront.yyph.order.mapper.PaymentMapper;
import priv.pront.yyph.order.service.OrderService;
import priv.pront.yyph.order.service.PaymentService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-20 20:10
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentInfo> implements PaymentService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HospitalFeignClient hospitalFeignClient;


    @Override
    public void savePaymentInfo(OrderInfo orderInfo, Integer paymentType) {
//        根据订单id和支付类型，查询支付记录表中是否存在相同订单
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderInfo.getId());
        wrapper.eq("payment_type", paymentType);
        if (baseMapper.selectCount(wrapper) > 0) {
            return;
        }
//        添加记录
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(orderInfo.getId());
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
        paymentInfo.setPaymentStatus(PaymentStatusEnum.UNPAID.getStatus());
//        支付内容
        String subject = new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd") + "|" + orderInfo.getHosname() + "|" + orderInfo.getDepname() + "|" + orderInfo.getTitle();
        paymentInfo.setSubject(subject);
//        支付金额
        paymentInfo.setTotalAmount(orderInfo.getAmount());
        baseMapper.insert(paymentInfo);


    }

    /**
     * 更新订单状态
     * @param out_trade_no 订单编码
     * @param resultMap 该订单下的相关信息
     */
    @Override
    public void paySuccess(String out_trade_no, Map<String, String> resultMap) {
//        根据订单编码得到支付记录
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", out_trade_no);
        wrapper.eq("payment_type", PaymentTypeEnum.WEIXIN.getStatus());
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);
//        更新支付记录信息
        paymentInfo.setPaymentStatus(PaymentStatusEnum.PAID.getStatus());
        paymentInfo.setTradeNo(resultMap.get("transaction_id"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(resultMap.toString());
        baseMapper.updateById(paymentInfo);
//        根据订单号得到订单信息,更新订单的信息
        OrderInfo orderInfo = orderService.getById(paymentInfo.getOrderId());
        orderInfo.setOrderStatus(OrderStatusEnum.PAID.getStatus());
        orderService.updateById(orderInfo);
//        调用医院相关接口，更新订单支付信息
        SignInfoVo signInfoVo = hospitalFeignClient.getSignInfoVo(orderInfo.getHoscode());
        Map<String, Object> map = new HashMap<>();
        map.put("hoscode",orderInfo.getHoscode());
        map.put("hosRecordId",orderInfo.getHosRecordId());
        map.put("timestamp", HttpRequestHelper.getTimestamp());
        String sign = HttpRequestHelper.getSign(map, signInfoVo.getSignKey());
        map.put("sign", sign);
//        发送请求
        JSONObject result = HttpRequestHelper.sendRequest(map, signInfoVo.getApiUrl() + "/order/updatePayStatus");



    }

    /**
     * 获取支付记录
     * @param orderId
     * @param paymentType
     * @return
     */
    @Override
    public PaymentInfo getPaymentInfo(Long orderId, Integer paymentType) {
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        wrapper.eq("payment_type", paymentType);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);
        return paymentInfo;
    }
}
