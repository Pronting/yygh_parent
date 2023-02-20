package priv.pront.yyph.order.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.common.util.AuthContextHolder;
import priv.pront.yygh.enums.OrderStatusEnum;
import priv.pront.yygh.model.order.OrderInfo;
import priv.pront.yygh.vo.order.OrderQueryVo;
import priv.pront.yyph.order.service.OrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-20 20:40
 */
@Api(tags = "订单相关接口")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderApiController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("生成挂号订单")
    @PostMapping("auth/submitOrder/{scheduleId}/{patientId}")
    public Result saveOrders(@PathVariable String scheduleId, @PathVariable Long patientId) {
        Long orderId = orderService.saveOrder(scheduleId, patientId);
        return Result.ok(orderId);
    }

    @ApiOperation("根据订单id查询订单信息")
    @GetMapping("auth/getOrder/{orderId}")
    public Result getOrders(@PathVariable String orderId){
        OrderInfo orderInfo = orderService.getOrder(orderId);
        return Result.ok(orderInfo);
    }

    @ApiOperation(value = "获取订单列表")
    @GetMapping("auth/{page}/{limit}")
    public Result index(@PathVariable Long page, @PathVariable Long limit, OrderQueryVo orderQueryVo, HttpServletRequest request) {
//        设置当前用户id
        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        IPage<OrderInfo> pageModel = orderService.selectPage(pageParam, orderQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取订单状态")
    @GetMapping("auth/getStatusList")
    public Result getStatusList() {
        return Result.ok(OrderStatusEnum.getStatusList());
    }





}
