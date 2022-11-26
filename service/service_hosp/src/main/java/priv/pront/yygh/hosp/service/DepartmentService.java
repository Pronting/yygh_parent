package priv.pront.yygh.hosp.service;


import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import priv.pront.yygh.model.hosp.Department;
import priv.pront.yygh.vo.hosp.DepartmentQueryVo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-26 12:24
 */
public interface DepartmentService {

    @ApiOperation("查询科室接口")
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    @ApiOperation("添加科室信息")
    void save(Map<String, Object> paramMap);

    @ApiOperation("删除科室接口")
    void remove(String hoscode, String depcode);
}
