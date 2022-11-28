package priv.pront.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import priv.pront.yygh.hosp.repository.ScheduleRepository;
import priv.pront.yygh.hosp.service.ScheduleService;
import priv.pront.yygh.model.hosp.Department;
import priv.pront.yygh.model.hosp.Schedule;
import priv.pront.yygh.vo.hosp.ScheduleQueryVo;

import java.util.Date;
import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-28 09:49
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @ApiOperation("上传排班接口")
    @Override
    public void save(Map<String, Object> paramMap) {
//        paramMap 转换schedule对象
        String paramMapString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(paramMapString, Schedule.class);
//        根据医院编号和排班编号进行查询
        Schedule scheduleExists = scheduleRepository.getSchduelByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());
        if (scheduleExists != null) {
//            更新
            scheduleExists.setUpdateTime(new Date());
            scheduleExists.setIsDeleted(0);
            scheduleExists.setStatus(1);
            scheduleRepository.save(scheduleExists);
        } else {
//            添加
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }

    }

    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        Pageable pageable = PageRequest.of(page - 1, limit);
//        创建Example对象
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo, schedule);
        schedule.setIsDeleted(0);
        schedule.setStatus(1);
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule, matcher);

        Page<Schedule> all = scheduleRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String hosScheduleId) {
//        查询数据库看是否该信息
//        根据医院编号和排班编号查询出信息
        Schedule schedule = scheduleRepository.getSchduelByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if (schedule != null) {
            scheduleRepository.deleteById(schedule.getId());
        }
    }
}
