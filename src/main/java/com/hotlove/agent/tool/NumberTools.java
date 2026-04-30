package com.hotlove.agent.tool;

import dev.langchain4j.agent.tool.Tool;

/**
 * @Description TODO
 * @Author ljguo4
 * @Date 2026/4/30 17:07
 * @Version 1.0
 */
public class NumberTools {

    @Tool("接收两个整数，并返回两数之和")
    public int add(int a, int b) {
        return a + b;
    }

    @Tool("接收两个证书，并返回两数之积")
    public int multi(int a, int b) {
        return a * b;
    }


}
