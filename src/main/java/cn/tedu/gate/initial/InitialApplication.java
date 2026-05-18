package cn.tedu.gate.initial;

import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        SeataAutoConfiguration.class // 排除Seata自动配置
})
public class InitialApplication {

    public static void main(String[] args) {
        SpringApplication.run(InitialApplication.class, args);
    }

}
