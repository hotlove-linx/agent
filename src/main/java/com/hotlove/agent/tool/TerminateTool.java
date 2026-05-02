package com.hotlove.agent.tool;

import com.hotlove.agent.agent.AgentState;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import lombok.Data;

import java.util.List;

@Data
public class TerminateTool implements Tool{

    private String status;

    @Override
    public ToolSpecification getToolSpecification() {
        return ToolSpecification.builder()
                .name(getClass().getName())
                .description("当请求被满足或助手无法继续完成任务时，终止交互。当您完成所有任务后，调用此工具以结束工作")
                .parameters(JsonObjectSchema.builder()
                        .addEnumProperty("status", List.of("success", "fail"))
                        .required("status")
                        .build())
                .build();
    }

    @Override
    public ToolResult runTool() {
        ToolResult toolResult = ToolResult.success("本次任务已经结束，即将结束交互，完成状态：%s".formatted(getStatus()));
        toolResult.setAgentState(AgentState.FINISHED);
        return toolResult;
    }
}
