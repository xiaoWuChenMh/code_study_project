package com.future.common.class_files.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.future.chat.ChatAi;
import com.future.chat.ChatAiFactory;
import com.future.enums.ChatModelType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author v_hhuima
 * @Date 2023/10/7 16:48
 */
public class ClassTranslateChat {

    long timeout = 6000;
    String apiHost = "";
    String apiKey = "";
    ChatAi chatAi =  ChatAiFactory.createChatAi(ChatModelType.GPT_3_5_TURBO,apiHost,apiKey,timeout);

    /*
    * @Description: 属性源代码翻译
    * @Author: huima
    * @Date: 2023/10/8 12:03
    * @Param: [content]
    * @return: java.lang.String
    */
    public String fieldTran(String content){
        String chatResponse = sendAi(content);
        String field_effect = JSON.parseObject(chatResponse).getString("field_effect_for_spark");
        return "// "+field_effect+"\n";
    }

    /*
    * @Description: 方法源代码翻译
    * @Author: huima
    * @Date: 2023/10/8 12:03
    * @Param: [content]
    * @return: com.future.common.class_files.chat.MethodTranBean
    */
    public MethodTranBean methodTran(String content){
        content = content.toString().replaceAll("\\s+", " ");
        MethodTranBean methodTranBean = new MethodTranBean();
        String chatResponse = sendAi(content);
        JSONObject json = JSON.parseObject(chatResponse);
        JSONArray parameters = json.getJSONArray("parameters");
        JSONArray code_explain = json.getJSONArray("code_explain");
        String comment = json.getString("summarize");
        methodTranBean.setComment(comment);
        for (int i = 0; i < parameters.size(); i++) {
            String param_name = parameters.getJSONObject(i).getString("param_name");
            String param_description = parameters.getJSONObject(i).getString("param_description");
            methodTranBean.setParameter(param_name,param_description);
        }
        for (int i = 0; i < code_explain.size(); i++) {
            String code = code_explain.getJSONObject(i).getString("code");
            String explain = code_explain.getJSONObject(i).getString("explain");
            methodTranBean.setCodes(i,code,explain);
        }
        return methodTranBean;
    }

    /*
    * @Description: 将问题发送给ai
    * @Author: huima
    * @Date: 2023/10/8 12:19
    * @Param: [content]
    * @return: java.lang.String
    */
    private String sendAi(String content){
        String historyMessages = "[]";
        Map<String, String> result= chatAi.buildMessages(historyMessages
                ,content
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
                        "\t\t\"parameters\": [\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\t\"param_name\": \"方法参数名称\",\n" +
                        "\t\t\t\t\"param_type\": \"方法参数的类型\",\n" +
                        "\t\t\t\t\"param_description\": \"描述该参数的含义\"\n" +
                        "\t\t\t}\n" +
                        "\t\t],\n" +
                        "\t\t\"method_effect\":方法在类中的作用是什么，\n" +
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
                        "\"summarize\":将上面的代码解释，按先后执行顺序，以通顺的逻辑写出一段总结描述\n" +
                        "\t}\n" +
                        "—\n");        // 模型参数
        String modelPram = "{\"temperature\":\"0.7\",\"maxTokens\":\"2000\"}";
        // 发起会话
        String chatResponse = chatAi.chat(result.get("messages"), modelPram);
        System.out.println(chatResponse);
        System.out.println("模型输出完成");
        return chatResponse;
    }
}
