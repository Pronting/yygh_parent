package priv.pront.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import priv.pront.yygh.model.hosp.Schedule;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-28 09:48
 */
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {
    Schedule getSchduelByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    List<Schedule> findScheduleByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);
}
