package priv.pront.yygh.user.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-07 10:03
 */
@Configuration
@MapperScan(basePackages = "priv.pront.yygh.user.mapper")
public class UserConfig {

    /**
     * 分页插件
     * @return 插件对象
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
