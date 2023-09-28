package com.future.chat;



import com.future.chat.openai.ChatOpenAI;
import com.future.enums.ChatGroupType;
import com.future.enums.ChatModelType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author hmai
 * @Date 2023/6/19 12:08
 */
public class ChatAiFactory {

    public static ChatAi createChatAi(ChatModelType model, String apiHost, String apiKey , Long timeout){
        List<String> apiKeys = new ArrayList<>();
        apiKeys.add(apiKey);
        return ChatAiFactory.createChatAi(model,apiHost,apiKeys,timeout);
    }

    public static ChatAi createChatAi(ChatModelType model, String apiHost, List<String> apiKeys , Long timeout){
        ChatAi chatAi = null;
        if (model.getGroup().equals(ChatGroupType.GPT_3_5)){
            chatAi = new ChatOpenAI();
        } else if(model.getGroup().equals(ChatGroupType.GPT_4)){
            chatAi = new ChatOpenAI();
        } else {
            return null;
        }
        chatAi.timeout(timeout).apiHost(apiHost).apiKey(apiKeys).init();
        return chatAi;
    }

}
