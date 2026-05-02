package com.hotlove.agent.agent;

import com.hotlove.agent.tool.ToolResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ReActAgent extends Agent{

    // 思考
    public abstract boolean think();

    // 行动
    public abstract ToolResult action();

    public ToolResult step() {
        if (!think()) {
            log.info("");
        }
        return action();
    }

}
