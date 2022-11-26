package priv.pront.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.pront.yygh.hosp.mapper.HospitalSetMapper;
import priv.pront.yygh.hosp.service.HospitalSetService;
import priv.pront.yygh.model.hosp.HospitalSet;

/**
 * @Description: HospitalService的实现类
 * @Author: pront
 * @Time:2022-11-11 09:36
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper,HospitalSet> implements HospitalSetService {
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode", hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }

//    ServiceImpl 已经 注入了 HospitalSetMapper
}
