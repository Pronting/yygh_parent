package priv.pront.yygh.hosp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.pront.yygh.hosp.repository.HospitalRepository;
import priv.pront.yygh.hosp.service.HospitalService;

/**
 * @Description:
 * @Author: pront
 * @Time:2022-11-23 09:32
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
}
