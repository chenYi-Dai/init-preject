package cn.tedu.gate.initial.service;

import cn.tedu.gate.initial.dto.BaseResp;
import cn.tedu.gate.initial.dto.IPLocationResp;
import cn.tedu.gate.initial.util.IPUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class PositionService {

    @Resource
    private IPUtil ipUtil;

    public BaseResp getLocation(String ip){
        String jsonObject = ipUtil.ipSearch(ip);
        BaseResp<IPLocationResp> baseResp =  new BaseResp<>();
        baseResp = JSONObject.parseObject(jsonObject, BaseResp.class);
        log.info("ip location info | {}",JSONObject.toJSONString(baseResp));
        return baseResp;
    }
}
