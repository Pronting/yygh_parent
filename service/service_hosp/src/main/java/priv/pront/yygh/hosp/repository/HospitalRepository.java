package priv.pront.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import priv.pront.yygh.model.hosp.Hospital;

import java.util.List;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-23 09:30
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {

    /**
     * 判断是否存在数据
     * @param hoscode 查询的医院编码
     * @return 存在的医院信息
     */
    Hospital getHospitalByHoscode(String hoscode);

    List<Hospital> findHopitalByHosnameLike(String hosname);
}
