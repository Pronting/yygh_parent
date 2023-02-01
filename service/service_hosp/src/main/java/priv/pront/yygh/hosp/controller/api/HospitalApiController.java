package priv.pront.yygh.hosp.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.hosp.service.DepartmentService;
import priv.pront.yygh.hosp.service.HospitalService;
import priv.pront.yygh.model.hosp.Hospital;
import priv.pront.yygh.vo.hosp.DepartmentVo;
import priv.pront.yygh.vo.hosp.HospitalQueryVo;

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
}
