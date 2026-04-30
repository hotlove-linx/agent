package com.hotlove.agent.agent;

import com.hotlove.agent.tool.Tool;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.language.LanguageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author ljguo4
 * @Date 2026/4/30 16:03
 * @Version 1.0
 */
public abstract class Agent {

    // 模型
    public ChatModel chatModel;

    // 记忆
    public List<ChatMessage> memeroy = new ArrayList<>();

    // 工具
    public List<Tool> tools = new ArrayList<>();

    // 最大循环次数
    public final int MAX_STEP = 10;

    public int currentStep;

    public abstract Tool step();


}
