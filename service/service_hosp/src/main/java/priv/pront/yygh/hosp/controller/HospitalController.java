package priv.pront.yygh.hosp.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.hosp.service.HospitalService;
import priv.pront.yygh.model.hosp.Hospital;
import priv.pront.yygh.vo.hosp.HospitalQueryVo;

/**
 * @Description: 远程模块调用所访问的控制器
 * @Author: pront
 * @Time:2022-11-28 11:53
 */
@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;


    @ApiOperation("医院列表，查询带分页")
    @GetMapping("list/{page}/{limit}")
    public Result listHospital(@PathVariable Integer page, @PathVariable Integer limit, HospitalQueryVo hospitalQueryVo) {

        Page<Hospital> pageModel = hospitalService.selectHospitalPage(page, limit, hospitalQueryVo);
        return Result.ok(pageModel);
    }
}
