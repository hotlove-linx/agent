package com.hotlove.agent.tool;

import com.hotlove.agent.agent.AgentState;
import lombok.Data;

@Data
public class ToolResult {

    private AgentState agentState;

    private String result;

    private ToolStatus status;

    public static ToolResult success(String result) {
        ToolResult toolResult  = new ToolResult();
        toolResult.status = ToolStatus.SUCCESS; // 工具的执行状态
        toolResult.agentState = AgentState.RUNNING; // agent执行状态
        toolResult.result = result;
        return toolResult;
    }

    public static ToolResult error(String result) {
        ToolResult toolResult  = new ToolResult();
        toolResult.status = ToolStatus.ERROR; // 工具的执行状态
        toolResult.agentState = AgentState.RUNNING; // agent执行状态
        toolResult.result = result;
        return toolResult;
    }

}
