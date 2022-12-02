package priv.pront.yygh.hosp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.hosp.service.DepartmentService;
import priv.pront.yygh.vo.hosp.DepartmentVo;

import java.util.List;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-12-02 14:41
 */
@Api(tags = "科室管理")
@RestController
@RequestMapping("/admin/hosp/department")
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    @ApiOperation("查询医院的所有列表")
    @GetMapping("getDepList/{hoscode}")
    public Result getDepList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.ok(list);
    }
}
