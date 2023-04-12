package priv.pront.yyph.statistics.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.order.client.OrderFeignClient;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.vo.order.OrderCountQueryVo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-04-12 11:52
 */
@Api(tags = "统计管理接口")
@RestController
@RequestMapping("/admin/statistics")
public class StatisticsController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @ApiOperation("获取预约统计数据")
    @GetMapping("getCountMap")
    public Result getCountMap(OrderCountQueryVo orderCountQueryVo) {
        Map<String, Object> countMap = orderFeignClient.getCountMap(orderCountQueryVo);
        return Result.ok(countMap);
    }

}
