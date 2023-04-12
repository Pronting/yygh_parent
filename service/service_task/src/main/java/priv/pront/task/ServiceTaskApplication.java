package priv.pront.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-04-12 10:34
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源
@EnableDiscoveryClient
@ComponentScan(basePackages = {"priv.pront"})
public class ServiceTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceTaskApplication.class, args);
    }
}
