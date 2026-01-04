package cn.tedu.gate.initial.config;

import cn.tedu.gate.initial.service.BaseService;
import cn.tedu.gate.initial.service.impl.TestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ConfigTestService {


    @Resource
    @Qualifier("imageBaseService")
    private BaseService imageBaseService;

    @Resource
    @Qualifier("fileBaseService")
    private BaseService fileBaseService;



    @Bean(name = "generateAllService")
    public TestService generateAllService(){
        List<BaseService> lists = new ArrayList<>();
        lists.add(imageBaseService);
        lists.add(fileBaseService);
        return new TestService(lists);
    }

    @Bean(name = "generateFileService")
    public TestService generateFileService(){
        List<BaseService> lists = new ArrayList<>();
        lists.add(fileBaseService);
        return new TestService(lists);
    }


    @Bean(name = "generateImageService")
    public TestService generateImageService(){
        List<BaseService> lists = new ArrayList<>();
        lists.add(imageBaseService);
        return new TestService(lists);
    }
}
