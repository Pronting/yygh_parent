package priv.pront.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import priv.pront.yygh.cmn.client.DictFeignClient;
import priv.pront.yygh.hosp.repository.HospitalRepository;
import priv.pront.yygh.hosp.service.HospitalService;
import priv.pront.yygh.model.hosp.Hospital;
import priv.pront.yygh.vo.hosp.HospitalQueryVo;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-23 09:32
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> paramMap) {

//      先将参数map集合转换对象Hospital
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);

//        判断数据库是否存在数据
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        if (hospitalExist != null) {
//        如果存在，进行修改
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else {
//        如果不存在，进行添加
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }

    //    医院列表(条件分页查询)
    @Override
    public Page<Hospital> selectHospitalPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
//        创建Pageable对象
        Pageable pageable = PageRequest.of(page - 1, limit);
//        构建一个条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
//        hospitalSetQueryVo转换Hospital对象
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);
//        创建Example对象
        Example<Hospital> example = Example.of(hospital, matcher);
//        调用方法实现查询
//        FIXME 远程查询写到循环里效率低
        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);
        pages.getContent().stream().forEach(item -> {
            this.setHospitalHosType(item);
        });

        return pages;
    }


    /**
     * 对医院等级封装
     *
     * @param hospital 每一个医院对象
     */
    private Hospital setHospitalHosType(Hospital hospital) {
//        根据dictCode 和 value值获取医院的等级名称
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());
//        查询省  市  地区
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());
        hospital.getParam().put("fullAddress", provinceString + cityString + districtString);
        hospital.getParam().put("hostypeString", hostypeString);
        return hospital;
    }

    @Override
    public void updateStatus(String id, Integer status) {
//        根据id先查询医院的信息
        Hospital hospital = hospitalRepository.findById(id).get();
//        设置修改信息
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    @Override
    public  Map<String,Object> getHospitalById(String id) {
        Map<String, Object> result = new HashMap<>();
        Hospital hospital = this.setHospitalHosType(hospitalRepository.findById(id).get());
        result.put("hospital", hospital);
        result.put("bookingRule", hospital.getBookingRule());
//        不需要重复返回
        hospital.setBookingRule(null);
        return result;


    }

    @Override
    public String getHospitalName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospital != null) {
            return hospital.getHosname();
        }
        return null;
    }

    @Override
    public List<Hospital> findByHosName(String hosname) {
        return hospitalRepository.findHopitalByHosnameLike(hosname);
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> result = new HashMap<>();
//        医院详情
        Hospital hospital = this.setHospitalHosType(this.getByHoscode(hoscode));
        result.put("hospital", hospital);
//        预约规则
        result.put("bookingRule", hospital.getBookingRule());
//        不需要重复返回
        hospital.setBookingRule(null);
        return result;
    }
}
