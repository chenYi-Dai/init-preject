package cn.tedu.gate.initial.controller;

import cn.tedu.gate.initial.entity.MessageRequest;
import cn.tedu.gate.initial.service.impl.WeatherService;
import io.swagger.annotations.Api;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@Api(tags = "通义千问接口")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, WeatherService weatherService) {
        this.chatClient = builder
                .defaultTools(weatherService)
                .build();
    }

    @PostMapping("/ai/chat")
    public String chat(@RequestBody MessageRequest request) {
        return chatClient.prompt()
                .user(request.getMessage())
                .call()
                .content();
    }
}