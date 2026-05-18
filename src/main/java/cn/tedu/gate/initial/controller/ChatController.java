package cn.tedu.gate.initial.controller;

import cn.tedu.gate.initial.entity.MessageRequest;
import cn.tedu.gate.initial.service.impl.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatClient chatClient;

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

    @GetMapping("/flow")
    @Operation(summary = "普通聊天接口", description = "发送消息给通义千问并获取回复")
    public String chat(@RequestParam(value = "message",defaultValue = "你给我讲一个笑话") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/ask")
    @Operation(summary = "普通聊天接口", description = "发送消息给通义千问并获取回复")
    public String ask(
            @Parameter(description = "用户发送的消息", required = true, example = "你好")
            @RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}