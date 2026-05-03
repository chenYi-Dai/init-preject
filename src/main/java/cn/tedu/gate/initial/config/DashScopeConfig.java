package cn.tedu.gate.initial.config;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DashScopeConfig {

    @Value("${dashscope.api-key}")
    private String apiKey;

    @Bean
    public Generation dashScopeGeneration() throws NoApiKeyException, ApiException, InputRequiredException {
        // 设置API Key
        System.setProperty("DASHSCOPE_API_KEY", apiKey);
        return new Generation();
    }
}
