package priv.pront.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-07 10:03
 */
@Configuration
@MapperScan(basePackages = "priv.pront.yygh.user.mapper")
public class UserConfig {
}
