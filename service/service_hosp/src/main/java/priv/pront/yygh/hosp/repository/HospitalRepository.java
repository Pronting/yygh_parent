package priv.pront.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import priv.pront.yygh.model.hosp.Hospital;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-23 09:30
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
}
