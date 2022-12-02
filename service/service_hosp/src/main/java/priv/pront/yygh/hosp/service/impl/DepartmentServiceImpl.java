package priv.pront.yygh.hosp.service.impl;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import priv.pront.yygh.hosp.repository.DepartmentRepository;
import priv.pront.yygh.hosp.service.DepartmentService;
import priv.pront.yygh.model.hosp.Department;
import priv.pront.yygh.vo.hosp.DepartmentQueryVo;
import priv.pront.yygh.vo.hosp.DepartmentVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-26 12:24
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @ApiOperation("分页查询科室")
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

    @ApiOperation("保存科室")
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

    @ApiOperation("删除科室")
    @Override
    public void remove(String hoscode, String depcode) {
//      先根据医院编号和科室编号查询出科室信息
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
//            调用方法删除
            departmentRepository.deleteById(department.getId());
        }
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
//        创建list集合，用于做种数据封装
        List<DepartmentVo> result = new ArrayList<>();
//        根据医院标号，查询医院所有科室信息
        Department departmentQuery = new Department();
        departmentQuery.setHoscode(hoscode);
        Example<Department> example = Example.of(departmentQuery);
//        所有科室列表信息
        List<Department> departmentList = departmentRepository.findAll(example);
//        根据大科室标号 bigcode 分组，获取每个大科室里面的下级子科室
        Map<String, List<Department>> departmentMap = departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
//        遍历Map集合
        for (Map.Entry<String, List<Department>> entry : departmentMap.entrySet()) {
//            大科室编号
            String bigCode = entry.getKey();
//            大科室编号的全部数据
            List<Department> department1List = entry.getValue();
//            封装大科室
            DepartmentVo department1Vo = new DepartmentVo();
            department1Vo.setDepcode(bigCode);
            department1Vo.setDepname(department1List.get(0).getBigname());
//            封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for (Department department : department1List) {
                DepartmentVo department2Vo = new DepartmentVo();
                department2Vo.setDepcode(department.getDepcode());
                department2Vo.setDepname(department.getDepname());
//          封装到list集合里面去
                children.add(department2Vo);

//                将小科室的List集合放到大科室的children中去

            }
            department1Vo.setChildren(children);
//        放到追钟的result中去
            result.add(department1Vo);
        }
        return result;
    }
}
