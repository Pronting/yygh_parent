package priv.pront.yygh.cmn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.pront.yygh.cmn.service.DictService;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.model.cmn.Dict;

import java.util.List;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-17 17:15
 */
@Api(value="数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation(value="根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict>list =  dictService.findChildData(id);
        return Result.ok(list);
    }
}
