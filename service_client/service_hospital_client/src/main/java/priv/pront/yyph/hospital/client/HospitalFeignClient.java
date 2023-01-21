package priv.pront.yyph.hospital.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import priv.pront.yygh.vo.hosp.ScheduleOrderVo;
import priv.pront.yygh.vo.order.SignInfoVo;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-20 21:15
 */
@FeignClient(value = "service-hosp")
@Repository
public interface HospitalFeignClient {

    @ApiOperation(value = "根据排班id获取预约下单数据")
    @GetMapping("/api/hosp/hospital/inner/getScheduleOrderVo/{scheduleId}")
    ScheduleOrderVo getScheduleOrderVo(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable("scheduleId") String scheduleId);


    @ApiOperation(value = "根据医院编号获取医院签名信息")
    @GetMapping("/api/hosp/hospital/inner/getSignInfoVo/{hoscode}")
    SignInfoVo getSignInfoVo(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable("hoscode") String hoscode);
}
