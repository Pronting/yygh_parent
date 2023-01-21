package priv.pront.yyph.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.order.OrderInfo;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-20 20:41
 */
public interface OrderService extends IService<OrderInfo> {

    /**
     * 生成挂号订单
     *
     * @param scheduleId
     * @param patientId
     * @return
     */
    Long saveOrder(String scheduleId, Long patientId);
}
