package priv.pront.yyph.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: pront
 * @Time:2023-01-20 20:36
 */
@SpringBootApplication
@ComponentScan("priv.pront")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"priv.pront"})
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}
