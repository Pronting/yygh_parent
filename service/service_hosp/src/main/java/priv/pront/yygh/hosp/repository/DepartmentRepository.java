package priv.pront.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import priv.pront.yygh.model.hosp.Department;

/**
 * @Description: 科室列表
 * @Author: pront
 * @Time:2022-11-26 10:37
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    //    DepartmentRepository会通过命名自动生成该方法的具体实现
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
