package priv.pront.yygh.msm.service;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-08 10:22
 */
public interface MsmService {
    /**
     * 发送手机验证码
     * @param phone 手机号码
     * @param code 手机验证码
     * @return
     */
    boolean send(String phone, String code);
}
