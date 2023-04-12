package priv.pront.yyph.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yygh.vo.order.OrderQueryVo;

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

    OrderInfo getOrder(String orderId);

    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    Boolean cancelOrder(Long orderId);
}
