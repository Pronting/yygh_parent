package priv.pront.yyph.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import priv.pront.yygh.enums.PaymentStatusEnum;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yygh.model.order.PaymentInfo;
import priv.pront.yyph.order.mapper.PaymentMapper;
import priv.pront.yyph.order.service.PaymentService;

import java.util.Date;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-20 20:10
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentInfo> implements PaymentService {


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
}
