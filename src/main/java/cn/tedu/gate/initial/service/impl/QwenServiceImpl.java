package cn.tedu.gate.initial.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class QwenServiceImpl {

    private Generation generation;

    @Value("${dashscope.api-key}")
    private String apiKey;

    @Value("${dashscope.model-name:qwen-plus}")
    private String modelName;

    @PostConstruct
    public void init() {
        this.generation = new Generation();
    }

    /**
     * 发送消息给通义千问（单轮对话）
     * @param prompt 用户输入的问题
     * @return 通义千问的回复
     */
    public String chat(String prompt) {
        try {
            List<Message> messages = new ArrayList<>();
            messages.add(Message.builder()
                    .role(Role.USER.getValue())
                    .content(prompt)
                    .build());

            GenerationParam param = GenerationParam.builder()
                    .apiKey(apiKey)
                    .model(modelName)
                    .messages(messages)
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            GenerationResult result = generation.call(param);

            if (result != null && result.getOutput() != null) {
                GenerationOutput output = result.getOutput();
                if (output.getChoices() != null && !output.getChoices().isEmpty()) {
                    return output.getChoices().get(0).getMessage().getContent();
                }
            }
            return "未获取到有效回复";
        } catch (ApiException | InputRequiredException | NoApiKeyException e) {
            log.error("调用通义千问API失败", e);
            return "调用失败: " + e.getMessage();
        }
    }

    /**
     * 带历史记录的对话
     * @param messages 消息历史（包含用户和助手的消息）
     * @return 通义千问的回复
     */
    public String chatWithHistory(List<Map<String, String>> messages) {
        try {
            List<Message> messageList = new ArrayList<>();
            for (Map<String, String> msg : messages) {
                messageList.add(Message.builder()
                        .role(msg.get("role"))
                        .content(msg.get("content"))
                        .build());
            }

            GenerationParam param = GenerationParam.builder()
                    .apiKey(apiKey)
                    .model(modelName)
                    .messages(messageList)
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            GenerationResult result = generation.call(param);

            if (result != null && result.getOutput() != null) {
                GenerationOutput output = result.getOutput();
                if (output.getChoices() != null && !output.getChoices().isEmpty()) {
                    return output.getChoices().get(0).getMessage().getContent();
                }
            }
            return "未获取到有效回复";
        } catch (ApiException | InputRequiredException | NoApiKeyException e) {
            log.error("调用通义千问API失败", e);
            return "调用失败: " + e.getMessage();
        }
    }
}
