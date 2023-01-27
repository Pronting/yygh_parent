package priv.pront.yyph.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yygh.model.order.PaymentInfo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-20 20:10
 */
public interface PaymentService extends IService<PaymentInfo> {


    /**
     * 添加信息
     * @param order
     * @param status
     */
    void savePaymentInfo(OrderInfo order, Integer status);

    void paySuccess(String out_trade_no, Map<String, String> resultMap);

    /**
     * 获取支付记录
     * @param orderId
     * @param paymentType
     * @return
     */
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);

}
