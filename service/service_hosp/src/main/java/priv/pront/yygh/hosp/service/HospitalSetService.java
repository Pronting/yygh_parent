package priv.pront.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.hosp.HospitalSet;
import priv.pront.yygh.vo.order.SignInfoVo;

/**
 * @Description: 医院设置的接口
 * @Author: pront
 * @Time:2022-11-11 09:34
 */
public interface HospitalSetService extends IService<HospitalSet> {

    /**
     * 根据传递过来的医院编码，查询数据库中的签名
     * @param hoscode 传递过来的医院编码
     * @return 医院签名
     */
    String getSignKey(String hoscode);


    /**
     * 获取签名信息
     * @param hoscode
     * @return
     */
    SignInfoVo getSignInfoVo(String hoscode);
}
