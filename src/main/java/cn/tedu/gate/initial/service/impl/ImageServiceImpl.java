package cn.tedu.gate.initial.service.impl;

import cn.tedu.gate.initial.entity.CheckDataVO;
import cn.tedu.gate.initial.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component("imageBaseService")
public class ImageServiceImpl implements BaseService {
    @Override
    public void checkInfo(CheckDataVO checkDataVO) {
        log.info("ImageServiceImpl check info");
    }
}
