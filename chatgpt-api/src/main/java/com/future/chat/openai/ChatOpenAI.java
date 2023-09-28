package com.future.chat.openai;

import com.alibaba.fastjson.JSON;
import com.future.OpenAiTikTokensUtil;
import com.future.chat.ChatAi;
import com.future.chat.constant.ChatOpenAiPram;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.AbstractStreamListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description openai的ChatGpt
 *  使用的sdk官网 ： https://github.com/PlexPt/chatgpt-java
 * @Author hmai
 * @Date 2023/6/19 12:10
 */
public class ChatOpenAI extends ChatAi {

    private EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();

    private OpenAiTikTokensUtil openAiTikTokensUtil = new OpenAiTikTokensUtil();

    private ChatGPTStream chatGPTStream;

    private ChatGPT chatGPT;

    @Override
    public ChatAi init() {
       chatGPT = ChatGPT.builder()
                .timeout(this.timeout)
                .apiKeyList(this.apiKeys)
                .apiHost(this.apiHost)
                .build()
                .init();
        chatGPTStream = ChatGPTStream.builder()
                .timeout(this.timeout)
                .apiKeyList(this.apiKeys)
                .apiHost(this.apiHost)
                .build()
                .init();
        return this;
    }

    @Override
    public Integer useMessagesTokens(String content, String systemContent) {
        List<Message> onlnyCurrentMessage = new ArrayList<>();
        Message useMessage = Message.of(null == content ? "" : content);
        Message systemMessage = Message.ofSystem(null == systemContent ? "" : systemContent);

        onlnyCurrentMessage.add(systemMessage);
        onlnyCurrentMessage.add(useMessage);
        return openAiTikTokensUtil.tokens(model,onlnyCurrentMessage);
    }

    @Override
    public Integer replyMessagesTokens(String content) {
        List<Message> onlnyCurrentMessage = new ArrayList<>();
        Message replyMessage = new Message("assistant",null == content ? "" : content);
        onlnyCurrentMessage.add(replyMessage);
        return openAiTikTokensUtil.tokens(model,onlnyCurrentMessage);
    }

    @Override
    public boolean useMessagesTokensOutBounds(String content, String systemContent) {
        Integer askTokens = useMessagesTokens(content,systemContent);
        return isOutBounds(askTokens);
    }


    /*
    * 判断是否超过界限
    */
    private boolean isOutBounds(Integer askTokens){
        if(askTokens>=model.getMaxContextLength()){
            return true;
        }
        return false;
    }


    @Override
    public Map<String, String> buildMessages(String historyMessages, String newContent, String systemContent) {
        List<Message> preSentMessages = jsonToMessages(historyMessages);
        Message newMessage = Message.of(null == newContent ? "" : newContent);
        Message systemMessage = Message.ofSystem(null == systemContent ? "" : systemContent);
        preSentMessages.add(newMessage);
        preSentMessages.add(0,systemMessage);
        return cutOutMessage(preSentMessages);
    }

    @Override
    public String chat(String messages, String modelPram) {
        ChatCompletion chatCompletion = getChatCompletion(messages,modelPram);
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        return response.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public void chatStream(String messages, String modelPram, AbstractStreamListener StreamListener) {
        ChatCompletion chatCompletion = getChatCompletion(messages,modelPram);
        chatGPTStream.streamChatCompletion(chatCompletion,StreamListener);
    }

    @Override
    public String buildHistoryMessage(String chatMessage, String aiReply) {
        List<Message> messages = jsonToMessages(chatMessage);
        if(null == messages || messages.size()==0){
            return "";
        }
        if(messages.get(0).getRole().equals(Message.Role.SYSTEM.getValue())){
            messages.remove(0);
        }
        if(null == aiReply || "".equals(aiReply)){
            return JSON.toJSONString(messages);
        }
        Message newMessage = new Message(Message.Role.ASSISTANT.getValue(), aiReply);
        messages.add(newMessage);
        return JSON.toJSONString(messages);
    }

    // ************************************************ 非继承的方法 ***************************************************************

    public ChatGPTStream getChatGPTStream(){
        return chatGPTStream;
    }

    public ChatGPT getChatGPT(){
        return chatGPT;
    }

    /*
    * @Description: 根据最大tokens数的限制裁剪待发送的messages
    * @Author: hmai 
    * @Date: 2023/6/19 19:22 
    * @Param: preSentMessages 待发送的messages
    * @return: messages:构建待传入模型的json信息;tokens:信息的tokens数
    */
    public Map<String, String> cutOutMessage(List<Message> preSentMessages) {
        Integer totalCountTokens = openAiTikTokensUtil.tokens(model,preSentMessages);
        while (totalCountTokens>model.getMaxContextLength() && totalCountTokens>0){
            Message message = preSentMessages.get(1);
            totalCountTokens-=openAiTikTokensUtil.tokens(model,message);
            preSentMessages.remove(1);
        }

        Map<String, String> result = new HashMap<>();
        result.put("messages",JSON.toJSONString(preSentMessages));
        result.put("tokens",totalCountTokens.toString());
        return result;
    }



    /*
    * @Description: json字符串转为模型需要的消息体
    * @Author: hmai
    * @Date: 2023/6/19 16:38
    * @Param: [jsonMessages]
    * @return: java.util.List<com.plexpt.chatgpt.entity.chat.Message>
    */
    public List<Message> jsonToMessages(String jsonMessages) {
        if(null == jsonMessages || "".equals(jsonMessages)){
            return new ArrayList<>();
        }
        return JSON.parseArray(jsonMessages, Message.class);
    }

    /**
     * 获取完整的openai模型的聊天消息体
     * @param messages
     * @param modelPram
     * @return
     */
    private ChatCompletion getChatCompletion(String messages, String modelPram){
        List<Message> preSentMessages = jsonToMessages(messages);
        ChatOpenAiPram chatOpenAiPram = JSON.parseObject(modelPram, ChatOpenAiPram.class);
        ChatCompletion.ChatCompletionBuilder chatCompletionBuilder = ChatCompletion.builder()
                .model(model.getName())
                .messages(preSentMessages);
        if(null == chatOpenAiPram){
            return  chatCompletionBuilder.build();
        }
        if(null!=chatOpenAiPram.getMaxTokens()){
            chatCompletionBuilder = chatCompletionBuilder.maxTokens(chatOpenAiPram.getMaxTokens());
        }
        if(null!=chatOpenAiPram.getTemperature()){
            chatCompletionBuilder = chatCompletionBuilder.temperature(chatOpenAiPram.getTemperature());
        }
        if(null!=chatOpenAiPram.getTopP()){
            chatCompletionBuilder = chatCompletionBuilder.topP(chatOpenAiPram.getTopP());
        }
        if(null!=chatOpenAiPram.getN()){
            chatCompletionBuilder = chatCompletionBuilder.n(chatOpenAiPram.getN());
        }
        return chatCompletionBuilder.build();
    }
}
