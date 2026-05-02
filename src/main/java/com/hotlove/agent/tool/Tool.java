package com.hotlove.agent.tool;

import dev.langchain4j.agent.tool.ToolSpecification;

public interface Tool {

    ToolSpecification getToolSpecification();

    ToolResult runTool();
}
