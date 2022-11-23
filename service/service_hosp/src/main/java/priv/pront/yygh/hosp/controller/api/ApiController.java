package priv.pront.yygh.hosp.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.pront.yygh.hosp.service.HospitalService;

/**
 * @Description: 对外的若干方法
 * @Author: pront
 * @Time:2022-11-23 09:34
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;
}
