package com.hotlove.agent;

import com.hotlove.agent.tool.NumberTools;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description TODO
 * @Author ljguo4
 * @Date 2026/4/30 17:06
 * @Version 1.0
 */
@SpringBootTest
public class ToolTest {

    @Test
    public void testTool() {
//        ToolSpecification toolSpecification = ToolSpecification.builder()
//                .name("getWeather")
//                .description("Returns the weather forecast for a given city")
//                .parameters(JsonObjectSchema.builder()
//                        .addStringProperty("city", "The city for which the weather forecast should be returned")
//                        .addEnumProperty("temperatureUnit", List.of("CELSIUS", "FAHRENHEIT"))
//                        .required("city") // 必填字段需要显式指定
//                        .build())
//                .build();
        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(NumberTools.class);


        ChatModel chatModel = OpenAiChatModel.builder()
                .baseUrl("")
                .modelName("")
                .apiKey("")
                .build();

        ChatRequest request = ChatRequest.builder()
                .toolSpecifications(toolSpecifications)
                .messages(UserMessage.from("1 + 1等于几?"))
                .build();

        ChatResponse chat = chatModel.chat(request);
        AiMessage aiMessage = chat.aiMessage();
        if (aiMessage.hasToolExecutionRequests()) {
            // 需要使用工具
            ToolExecutionRequest toolExecutionRequest = aiMessage.toolExecutionRequests().get(0);
            ToolExecutor toolExecutor = new DefaultToolExecutor("add", toolExecutionRequest);
            String add = toolExecutor.execute(toolExecutionRequest, "add");
        }


    }

}
