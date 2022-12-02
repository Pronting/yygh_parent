package priv.pront.yygh.hosp.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import priv.pront.yygh.model.hosp.Schedule;
import priv.pront.yygh.vo.hosp.ScheduleQueryVo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-28 09:49
 */
public interface ScheduleService {

    @ApiOperation("上传排班")
    void save(Map<String, Object> paramMap);

    @ApiOperation("查询排班")
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    @ApiOperation("删除排班")
    void remove(String hoscode, String hosScheduleId);

    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);
}
