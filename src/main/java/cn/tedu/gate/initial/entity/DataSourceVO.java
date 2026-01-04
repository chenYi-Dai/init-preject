package cn.tedu.gate.initial.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceVO {

    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.name}")
    private String name;

    @Value("${mysql.pwd}")
    private String password;

    @Value("${mysql.driver-class}")
    private String connectType;

}
