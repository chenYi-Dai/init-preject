package cn.tedu.gate.initial.controller;

import cn.tedu.gate.initial.entity.MessageRequest;
import cn.tedu.gate.initial.function.LocationNameFunction;
import cn.tedu.gate.initial.service.impl.WeatherService;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.spec.DashScopeApiSpec;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {


    @Autowired
    private ChatModel chatModel;

    @Resource
    private LocationNameFunction locationNameFunction;


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

    @GetMapping("/fc")
    @Operation(summary = "人名统计", description = "发送消息给通义千问并获取回复")
    public String functionLn(@RequestParam(value = "message",defaultValue = "你给我讲一个笑话") String message) {

        ToolCallback[] tools = ToolCallbacks.from(locationNameFunction);

        // 2. 配置本次请求可以使用的工具
        var options = ToolCallingChatOptions.builder()
                .toolCallbacks(tools)
                .build();
        Prompt prompt = new Prompt(message, options);

        ChatResponse call = chatModel.call(prompt);
        return call.getResult().getOutput().getText();

    }

    @GetMapping("/flow")
    @Operation(summary = "普通聊天接口", description = "发送消息给通义千问并获取回复")
    public String chat(@RequestParam(value = "message",defaultValue = "你给我讲一个笑话") String message) {

//        List<DashScopeApiSpec.FunctionTool> tools = new ArrayList<>();
//        DashScopeApiSpec.FunctionTool tool = new DashScopeApiSpec.FunctionTool();
//        DashScopeChatOptions chatOptions = new DashScopeChatOptions();
//        chatOptions.setTools();




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