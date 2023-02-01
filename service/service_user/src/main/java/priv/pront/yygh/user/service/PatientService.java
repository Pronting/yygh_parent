package priv.pront.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import priv.pront.yygh.model.user.Patient;
import priv.pront.yygh.model.user.UserInfo;

import java.util.List;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-02-01 22:45
 */
public interface PatientService extends IService<Patient> {

    /**
     * 获取就诊人列表
     *
     * @param userId
     * @return
     */
    List<Patient> findAllUserId(Long userId);


    /**
     * 根据就诊人id获取就诊人信息
     * @param id
     * @return
     */
    Patient getPatientId(Long id);
}
