package cn.tedu.gate.initial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication

public class InitialApplication {

    public static void main(String[] args) {
        SpringApplication.run(InitialApplication.class, args);
    }

}
