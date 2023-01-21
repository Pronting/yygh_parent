package priv.pront.yygh.hosp.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.hosp.service.DepartmentService;
import priv.pront.yygh.hosp.service.HospitalService;
import priv.pront.yygh.hosp.service.HospitalSetService;
import priv.pront.yygh.hosp.service.ScheduleService;
import priv.pront.yygh.model.hosp.Hospital;
import priv.pront.yygh.model.hosp.Schedule;
import priv.pront.yygh.vo.hosp.DepartmentVo;
import priv.pront.yygh.vo.hosp.HospitalQueryVo;
import priv.pront.yygh.vo.hosp.ScheduleOrderVo;
import priv.pront.yygh.vo.order.SignInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-12-04 14:09
 */
@Api(tags = "用户系统api")
@RestController
@RequestMapping("/api/hosp/hospital")
public class HospitalApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation("查询医院列表功能")
    @GetMapping("findHospitalList/{page}/{limit}")
    public Result findHospitalList(@PathVariable Integer page, @PathVariable Integer limit, HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> hospitals = hospitalService.selectHospitalPage(page, limit, hospitalQueryVo);
        return Result.ok(hospitals);
    }


    @ApiOperation("根据医院名称进行模糊查询")
    @GetMapping("findByHosName/{hosname}")
    public Result findByHostName(@PathVariable String hosname) {
        List<Hospital> list = hospitalService.findByHosName(hosname);
        return Result.ok(list);
    }

    @ApiOperation("根据医院标号获取科室的所有信息")
    @GetMapping("department/{hoscode}")
    public Result index(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }


    @ApiOperation("根据医院标号获取医院预约挂号详情")
    @GetMapping("findHospitalDetail/{hoscode}")
    public Result item(@PathVariable String hoscode) {
        System.out.println("Api-controller-hoscde = " + hoscode);
        Map<String, Object> map = hospitalService.item(hoscode);
        return Result.ok(map);
    }


    @ApiOperation(value = "获取可预约排班数据")
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getBookingSchedule(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode) {
        System.out.println("getBookingSchedule-api被调用");
        return Result.ok(scheduleService.getBookingScheduleRule(page, limit, hoscode, depcode));
    }

    @ApiOperation(value = "获取排班数据")
    @GetMapping("auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    public Result findScheduleList(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode,
            @ApiParam(name = "workDate", value = "排班日期", required = true)
            @PathVariable String workDate) {
        System.out.println("findScheduleList-api被调用");
        return Result.ok(scheduleService.getDetailSchedule(hoscode, depcode, workDate));
    }

    @ApiOperation(value = "获取排班id获取排班数据")
    @GetMapping("getSchedule/{scheduleId}")
    public Result getSchedule(@PathVariable String scheduleId) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
        return Result.ok(schedule);
    }

    @ApiOperation(value = "根据排班id获取预约下单数据")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @ApiOperation(value = "获取医院签名信息")
    @GetMapping("inner/getSignInfoVo/{hoscode}")
    public SignInfoVo getSignInfoVo(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable("hoscode") String hoscode) {
        return hospitalSetService.getSignInfoVo(hoscode);
    }



}

