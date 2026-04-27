package com.hotlove.agent.tool;

import dev.langchain4j.service.UserMessage;

@AiService
public interface Assistant {
    String chat(@UserMessage String message);
}
