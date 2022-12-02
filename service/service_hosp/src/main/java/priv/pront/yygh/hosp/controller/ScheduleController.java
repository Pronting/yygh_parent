package priv.pront.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.hosp.service.ScheduleService;

import java.util.Map;

/**
 * @Description: 医院排班规则
 * @Author: pront
 * @Time:2022-12-02 15:37
 */
@Api(tags = "科室规则管理")
@RestController
@RequestMapping("/admin/hosp/schedule")
@CrossOrigin
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("根据医院编号和科室编号，查询排班规则")
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable long page, @PathVariable long limit, @PathVariable String hoscode, @PathVariable String depcode) {
        Map<String,Object> map = scheduleService.getRuleSchedule(page, limit, hoscode, depcode);
        return Result.ok(map);
    }
}
