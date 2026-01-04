package cn.tedu.gate.initial.service.impl;

import cn.tedu.gate.initial.entity.CheckDataVO;
import cn.tedu.gate.initial.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class TestService {

    private List<BaseService> baseServices = new ArrayList<>();

    public TestService(List<BaseService> baseServices) {
        this.baseServices = baseServices;
    }

    public void test(CheckDataVO checkDataVO){
        for(BaseService baseService : baseServices){
            baseService.checkInfo(checkDataVO);
        }
    }

}
