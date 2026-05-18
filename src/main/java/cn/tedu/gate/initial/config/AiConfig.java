package cn.tedu.gate.initial.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.defaultSystem("你是富融银行的一个助手，能帮忙回答用户在富融银行开户，转账等一些方面的问题").build();
    }
}
