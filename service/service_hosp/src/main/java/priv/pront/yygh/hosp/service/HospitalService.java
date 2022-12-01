package priv.pront.yygh.hosp.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import priv.pront.yygh.model.hosp.Hospital;
import priv.pront.yygh.vo.hosp.HospitalQueryVo;

import java.util.Map;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-23 09:32
 */
public interface HospitalService {

    /**
     * 上传医院接口
     * @param paramMap 医院信息参数
     */
    void save(Map<String, Object> paramMap);

    @ApiOperation("根据医院编号查询医院信息")
    Hospital getByHoscode(String hoscode);


    @ApiOperation("医院列表，查询带分页")
    Page<Hospital> selectHospitalPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Map<String,Object> getHospitalById(String id);
}
