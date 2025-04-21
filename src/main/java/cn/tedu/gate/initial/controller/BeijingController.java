package cn.tedu.gate.initial.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bj")
@Api(tags = "线上对公开户批量处理")
public class BeijingController {

    @GetMapping("/show")
    @ApiOperation(value = "show", notes = "开户检查补偿接口")
    public String show(){

        return "这里是北京！";

    }


}
