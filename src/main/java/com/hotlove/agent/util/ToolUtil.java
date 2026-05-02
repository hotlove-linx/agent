package com.hotlove.agent.util;

import com.hotlove.agent.tool.Tool;
import com.hotlove.agent.tool.ToolResult;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ToolUtil {

    public static ToolResult runTool(ToolExecutionRequest toolExecutionRequest) {
        try {
            Class<?> clazz = Class.forName(toolExecutionRequest.name());

            Tool tool = (Tool) JsonUtil.toJsonObject(toolExecutionRequest.arguments(), clazz);

            return tool.runTool();
        } catch (ClassNotFoundException e) {
            return ToolResult.error(e.getMessage());
        }
    }

}
