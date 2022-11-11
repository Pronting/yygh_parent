package priv.pront.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import priv.pront.yygh.common.result.Result;
import priv.pront.yygh.hosp.service.HospitalSetService;
import priv.pront.yygh.model.hosp.HospitalSet;
import priv.pront.yygh.vo.hosp.HospitalSetQueryVo;

import java.util.List;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-11 09:41
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;


    /**
     * 查询医院设置表所有信息
     *
     * @return 医院集合列表
     */
    @ApiOperation("获取所有医院设置信息")
    @GetMapping("findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
//        默认返回JSON 数据，所添加的 jackson 完成转换
        return Result.ok(list);
    }

    @ApiOperation("逻辑删除医院设置信息")
    @DeleteMapping("{id}")
    public Result removeHospitalSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        return flag ? Result.ok() : Result.fail();
    }

    @ApiOperation("条件查询分页")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageHospitalSet(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
//        创建page 对象，里面传递current, limit
        Page<HospitalSet> page = new Page<>(current, limit);
//        构造条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }
//        调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);

//        返回结果
        return Result.ok(pageHospitalSet);
    }


//    添加医院设置


//    根据id获取医院设置

//    修改医院设置

//    批量删除医院设置
}
