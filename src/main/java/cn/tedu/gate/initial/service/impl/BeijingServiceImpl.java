package cn.tedu.gate.initial.service.impl;

import cn.tedu.gate.initial.entity.CheckDataVO;
import cn.tedu.gate.initial.service.BaseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BeijingServiceImpl {



    @Resource
    @Qualifier("generateImageService")
    private TestService baseService;

    public void test(CheckDataVO checkDataVO){
        baseService.test(checkDataVO);
    }
}
