package priv.pront.yyph.user.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import priv.pront.yygh.model.user.Patient;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-20 20:54
 */
@FeignClient(value = "service-user")
@Repository
public interface PatientFeignClient {

    @ApiOperation("根据就诊人id获取就诊人信息")
    @GetMapping("/api/user/patient/inner/get/{id}")
    Patient getPatientOrder(@PathVariable("id") Long id);

}
