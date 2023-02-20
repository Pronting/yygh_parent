package priv.pront.yyph.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yygh.model.order.PaymentInfo;

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
}
