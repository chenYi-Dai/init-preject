package cn.tedu.gate.initial.service.impl;

import org.springframework.ai.tool.annotation.Tool;  // 修正这里
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Tool(description = "根据城市名称获取实时天气")
    public String getWeatherByCity(String city) {
        System.out.println("正在调用工具查询 " + city + " 的天气...");
        return city + " 今天晴，气温25°C，微风。";
    }
}