package priv.pront.yygh.hosp.service.impl;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import priv.pront.yygh.hosp.repository.DepartmentRepository;
import priv.pront.yygh.hosp.service.DepartmentService;
import priv.pront.yygh.model.hosp.Department;
import priv.pront.yygh.vo.hosp.DepartmentQueryVo;

import java.util.Date;
import java.util.Map;


/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-26 12:24
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
//        创建Pageable对象，里面设置当前页和每页记录数 0是第一页
        Pageable pageable = PageRequest.of(page - 1, limit);
//        创建Example对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        Example<Department> example = Example.of(department, matcher);

        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void save(Map<String, Object> paramMap) {
//        paramMap 转换department对象
        String paramMapString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramMapString, Department.class);
//        根据医院编号和科室编号进行查询
        Department departmentExists = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());
        if (departmentExists != null) {
//            更新
            departmentExists.setUpdateTime(new Date());
            departmentExists.setIsDeleted(0);
            departmentRepository.save(departmentExists);
        } else {
//            添加
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public void remove(String hoscode, String depcode) {
//      先根据医院编号和科室编号查询出科室信息
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
//            调用方法删除
            departmentRepository.deleteById(department.getId());
        }
    }
}
