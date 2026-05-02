package com.hotlove.agent.agent;

import com.hotlove.agent.tool.Tool;
import com.hotlove.agent.tool.ToolResult;
import com.hotlove.agent.util.ToolUtil;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import opennlp.tools.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Builder
public class ToolCallAgent extends ReActAgent{

    private ChatMemory chatMemory;

    private ChatModel chatModel;

    private String prompt;

    public final String SYSTEM_PROMPT = """
            你是一个全能的AI助手，可以解决用户提出的任何问题。你可以调用各种工具来完成各种复杂的请求。
            你可以进行编程，浏览网页，进行网页信息检索，进行处理等。
            """;

    public final String THINK_PROMPT = """
            你可以使用以下工具与计算机交互：
            - PythonExecute: 执行 Python 代码，与计算机系统交互，进行数据处理
            - FileSaverTool: 本地保存文件，例如 txt、py、html 等
            - BowerScreenshotTool: 使用浏览器进行截屏和截图
            - WebCrawlingTool: 对网页的内容进行解析和提取文本
            - SearchTool: 执行网页信息检索
            - TerminateTool: 使用此工具可以表明已完成请求
            根据用户需求，主动选择最适合的工具或工具组合。对于复杂任务，可以将问题拆解，并逐步使用不同工具来解决。在使用用每个工具后，清楚地解释执行结果，并建议下一步行动
            """;

    public List<ToolExecutionRequest> toolExecutionRequests = new ArrayList<>();

    /**
     * 思考要不要调用工具
     * @return
     */
    @Override
    public boolean think() {

        if (getCurrentStep() == 1) {
            getChatMemory().add(SystemMessage.from(SYSTEM_PROMPT));
            getChatMemory().add(UserMessage.from(getPrompt() + "," + THINK_PROMPT));
        } else {
            getChatMemory().add(UserMessage.from(THINK_PROMPT));
        }

        // 请求ai大模型分析任务需要那些工具
        ChatResponse chatResponse = getChatModel().chat(ChatRequest.builder()
                .messages(getChatMemory().messages())
                .toolSpecifications(getTools().stream().map(Tool::getToolSpecification).toList())
                .build());

        if (!StringUtil.isEmpty(chatResponse.aiMessage().text())) {
            log.info("AI开始思考：{}", chatResponse.aiMessage().text());
        }

        if (chatResponse.aiMessage().hasToolExecutionRequests()) {
            toolExecutionRequests = chatResponse.aiMessage().toolExecutionRequests();
            log.info("准备调用工具,选择了{}个工具准备执行", chatResponse.aiMessage().toolExecutionRequests().size());
        }
        return chatResponse.aiMessage().hasToolExecutionRequests();
    }

    @Override
    public ToolResult action() {

        ToolResult toolResult = null;
        for (ToolExecutionRequest toolExecutionRequest : this.toolExecutionRequests) {
            log.info("准备执行工具：{}", toolExecutionRequest.name());
            toolResult = ToolUtil.runTool(toolExecutionRequest);
            String result = "Observed output of cmd '%s', executed : %s".formatted(toolExecutionRequest.name(), toolResult.getResult());
            getChatMemory().add(AiMessage.aiMessage(result));
        }

        return toolResult;
    }

    @Override
    public ChatModel getChatModel() {
        return chatModel;
    }

    @Override
    public ChatMemory getChatMemory() {
        return chatMemory;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }
}
