package priv.pront.yyph.order.service;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-20 20:04
 */
public interface WeixinService {
    Map<String, Object> createNative(Long orderId);

    Map<String, String> queryPayStatus(Long orderId);

    /***
     * 退款
     * @param orderId
     * @return
     */
    Boolean refund(Long orderId);

}
