package priv.pront.yyph.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.order.PaymentInfo;
import priv.pront.yygh.model.order.RefundInfo;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-14 17:08
 */
public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
