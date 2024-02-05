package cn.tedu.gate.initial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bj")
public class BeijingController {

    @GetMapping("/show")
    public String show(){

        return "这里是北京！";

    }


}
