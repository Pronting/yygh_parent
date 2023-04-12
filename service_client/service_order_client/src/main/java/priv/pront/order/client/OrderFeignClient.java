package priv.pront.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import priv.pront.yygh.vo.order.OrderCountQueryVo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-28 14:57
 */
@FeignClient(value = "service-order")
@Repository
public interface OrderFeignClient {

    /**
     * 获取订单统计数据
     */
    @PostMapping("/api/order/orderInfo/inner/getCountMap")
    Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo);



}
