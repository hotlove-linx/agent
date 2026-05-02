package com.hotlove.agent.agent;

import com.hotlove.agent.tool.FileSaverTool;
import com.hotlove.agent.tool.TerminateTool;
import com.hotlove.agent.tool.Tool;
import com.hotlove.agent.tool.ToolResult;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description TODO
 * @Author ljguo4
 * @Date 2026/4/30 16:03
 * @Version 1.0
 */
@Data
@Slf4j
public abstract class Agent {

    // 模型
    public abstract ChatModel getChatModel();

    // 记忆
    public abstract ChatMemory getChatMemory();

    public abstract String getPrompt();

    // 工具
    public List<Tool> tools = List.of(new FileSaverTool(), new TerminateTool());

    // 最大循环次数
    public int maxSteps = 10;

    public int currentStep = 1;

    public abstract ToolResult step();

    public void run() {
        AgentState state = AgentState.RUNNING;
        while (currentStep < maxSteps && state != AgentState.FINISHED) {
            log.info("开始执行步骤: {}/{}", currentStep, maxSteps);
            ToolResult toolResult = step();
            currentStep++;
            state = toolResult.getAgentState();
            if (currentStep >= maxSteps) {
                log.info("current step exceeded max steps：{}", currentStep);
            }
        }
    }


}
