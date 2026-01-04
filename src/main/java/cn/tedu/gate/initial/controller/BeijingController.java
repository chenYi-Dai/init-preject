package cn.tedu.gate.initial.controller;

import cn.tedu.gate.initial.entity.CheckDataVO;
import cn.tedu.gate.initial.service.impl.BeijingServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bj")
public class BeijingController {

    @Resource
    private BeijingServiceImpl service;
    @GetMapping("/show")
    public String show(){
        CheckDataVO build = CheckDataVO.builder().age("age").name("test").fileUrl("asdf").fileUrl("ajhsdgjysaf").build();
        service.test(build);
        return "这里是北京！";

    }


}
