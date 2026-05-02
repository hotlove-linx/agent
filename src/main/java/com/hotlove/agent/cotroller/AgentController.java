package com.hotlove.agent.cotroller;

import com.hotlove.agent.agent.ToolCallAgent;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class AgentController {

    private final ChatModel chatModel;

    @GetMapping("/chat")
    public String prompt(String prompt) {
        ToolCallAgent agent = ToolCallAgent.builder()
                .chatMemory(MessageWindowChatMemory.withMaxMessages(100))
                .chatModel(chatModel)
                .prompt(prompt)
                .build();

        agent.run();
        return "success";
    }

}
