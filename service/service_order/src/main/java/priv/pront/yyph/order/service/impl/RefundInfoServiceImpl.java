package priv.pront.yyph.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.pront.yygh.enums.RefundStatusEnum;
import priv.pront.yygh.model.order.PaymentInfo;
import priv.pront.yygh.model.order.RefundInfo;
import priv.pront.yyph.order.mapper.RefundInfoMapper;
import priv.pront.yyph.order.service.RefundInfoService;

import java.util.Date;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-14 17:09
 */
@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {


//    保存退款记录
    @Override
    public RefundInfo saveRefundInfo(PaymentInfo paymentInfo) {
//        判断是否优重复数据添加
        QueryWrapper<RefundInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", paymentInfo.getOrderId());
        wrapper.eq("payment_type", paymentInfo.getPaymentType());
        RefundInfo refundInfo = baseMapper.selectOne(wrapper);
        if (refundInfo != null) {
//            有相同数据直接进行返回
            return refundInfo;
        }
        refundInfo = new RefundInfo();
        refundInfo.setCreateTime(new Date());
        refundInfo.setOrderId(paymentInfo.getOrderId());
        refundInfo.setPaymentType(paymentInfo.getPaymentType());
        refundInfo.setOutTradeNo(paymentInfo.getOutTradeNo());
        refundInfo.setRefundStatus(RefundStatusEnum.UNREFUND.getStatus());
        refundInfo.setSubject(paymentInfo.getSubject());
        baseMapper.insert(refundInfo);
        return refundInfo;

    }
}
