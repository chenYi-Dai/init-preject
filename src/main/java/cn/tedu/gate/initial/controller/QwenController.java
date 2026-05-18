//package cn.tedu.gate.initial.controller;
//
//
//import cn.tedu.gate.initial.service.impl.QwenServiceImpl;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/qwen")
//@Api(tags = "通义千问接口")
//public class QwenController {
//
//    @Autowired
//    private QwenServiceImpl qwenService;
//
//    @PostMapping("/chat")
//    @ApiOperation("与通义千问对话（单轮）")
//    public String chat(@RequestBody String prompt) {
//        return qwenService.chat(prompt);
//    }
//
//
//    @PostMapping("/chat-with-history")
//    @ApiOperation("与通义千问对话（带历史记录）")
//    public String chatWithHistory(@RequestBody List<Map<String, String>> messages) {
//        return qwenService.chatWithHistory(messages);
//    }
//
//    @GetMapping("/health")
//    @ApiOperation("健康检查")
//    public String health() {
//        return "通义千问服务连接正常";
//    }
//}
