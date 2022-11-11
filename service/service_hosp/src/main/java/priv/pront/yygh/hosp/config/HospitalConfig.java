package priv.pront.yygh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 医院配置类
 * @Author: pront
 * @Time:2022-11-11 09:54
 */
@MapperScan("priv.pront.yygh.hosp.mapper")
@Configuration
public class HospitalConfig {
}
