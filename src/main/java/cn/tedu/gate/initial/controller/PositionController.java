package cn.tedu.gate.initial.controller;

import cn.tedu.gate.initial.dto.BaseResp;
import cn.tedu.gate.initial.req.PositionReq;
import cn.tedu.gate.initial.service.PositionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/location")
public class PositionController {

    @Resource
    private PositionService positionService;

    @ResponseBody
    @PostMapping("/getIp")
    public BaseResp getPosition(@RequestBody PositionReq req){
        BaseResp location = positionService.getLocation(req.getIp());
        return location;
    }
}
