package priv.pront.yygh.hosp.service;

import io.swagger.annotations.ApiOperation;
import priv.pront.yygh.model.hosp.Hospital;

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
}
