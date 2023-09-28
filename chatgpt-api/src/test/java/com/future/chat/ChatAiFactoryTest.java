package com.future.chat;



import com.future.enums.ChatModelType;

import java.util.Map;

public class ChatAiFactoryTest {

    public static void main(String[] args) {
        long timeout = 6000;
        String apiHost = "https://service-b6ohgcl8-1254280433.usw.apigw.tencentcs.com/release/";
        String apiKey = "";
        ChatAi chatAi =  ChatAiFactory.createChatAi(ChatModelType.GPT_3_5_TURBO,apiHost,apiKey,timeout);

        // 构建待传递给模型的信息
        String historyMessages = "[]";
        Map<String, String> result= chatAi.buildMessages(historyMessages
                ,"类名：package org.apache.spark.memory.TaskMemoryManager；方法：/** * Release N bytes of execution memory for a MemoryConsumer. */ public void releaseExecutionMemory(long size, MemoryConsumer consumer) { logger.debug(\"Task {} release {} from {}\", taskAttemptId, Utils.bytesToString(size), consumer); memoryManager.releaseExecutionMemory(size, taskAttemptId, consumer.getMode()); }"
                ,"你是spark源码贡献者，我需要你指导我阅读spark源代码。首先我会定义元的规则(每一个规则都以00编号开头)，必须保证每条元规则都得到执行\n" +
                        "001:你要以中文回答。\n" +
                        "002：我给你的代码中有双引号，需要你执行替换操作将双引号替换为（\\\\\\\\\\\"）\n" +
                        "然后我会按如下规则(每一个规则都以01编号开头)给你源码:\n" +
                        "011:类名：xxxx;属性：xxxxxx;\n" +
                        "012:类名：xxxxx;方法：xxxxx;\n" +
                        "而你要参考spark源码和源码上的注释，按如下规则(每一个规则都以02编号开头)给予我指导：\n" +
                        "021：如果是规则011，我需要你按长破折号内的格式输出 —\n" +
                        "\t{\n" +
                        "\t\t\"class_name\":告诉我类名是什么,\n" +
                        "\t\t\"field_name\":告诉我属性名是什么,\n" +
                        "\t\t\"field_effect\":该属性在类中的作用是什么，\n" +
                        "\t\t\"field_effect_for_spark\":该属性对spark来说有什么用，不要少于100字\n" +
                        "\t}\n" +
                        "—\n" +
                        "022：如果是规则012，我需要你按长破折号内的格式以json格式输出—\n" +
                        "\t{\n" +
                        "\t\t\"class_name\":告诉我类名是什么,\n" +
                        "\t\t\"method_name\":告诉我方法名是什么,\n" +
                        "\t\t\"method_effect\":方法在类中的作用是什么，\n" +
                        "\t\t\"method_effect_for_spark\":方法对spark来说有什么用，不要少于100字,\n" +
                        "\t\t\"code_explain\":       [\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\t\"code\"：一行代码,\n" +
                        "\t\t\t\t\"explain\"：改行代码的作用\n" +
                        "\t\t\t},\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\t\"代码\"：xxxxxxxxxxxxxxx,\n" +
                        "\t\t\t\t\"作用\"：xxxxxxxxxxxxxxx\n" +
                        "\t\t\t}\n" +
                        "\t\t]\n" +
                        "\t}\n" +
                        "—\n" +
                        "最后，告诉我是否执行了00编号开头的规则，然后解释原因");
        // 模型参数
        String modelPram = "{\"temperature\":\"0.7\",\"maxTokens\":\"2000\"}";
        // 发起会话
        String chatResponse = chatAi.chat(result.get("messages"), modelPram);
        System.out.println(chatResponse);
        System.out.println("模型输出完成");
        System.exit(0);
    }

}