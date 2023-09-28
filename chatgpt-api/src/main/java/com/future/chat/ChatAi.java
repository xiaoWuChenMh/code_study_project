package com.future.chat;

import com.future.enums.ChatModelType;
import com.plexpt.chatgpt.listener.AbstractStreamListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public  abstract class ChatAi {


    protected Long timeout = 900L;

    protected List<String> apiKeys;

    protected String apiHost;

    protected ChatModelType model = ChatModelType.GPT_3_5_TURBO;

    public ChatAi model(ChatModelType model){
        this.model = model; // ChatModelType.valueOf(model);
        return this;
    }

    /*
    * @Description: 配置超时时间
    * @Author: hmai
    * @Date: 2023/6/19 14:29
    * @Param: timeout
    * @return: com.sky.future.chat.ChatAi
    */
    public ChatAi timeout(long timeout){
        this.timeout = timeout;
        return this;
    } ;

    /*
    * @Description: api的key
    * @Author: hmai
    * @Date: 2023/6/19 14:31
    * @Param: apikey
    * @return: com.sky.future.chat.ChatAi
    */
    public ChatAi apiKey(String apiKey){
        List<String> apiKeys= new ArrayList<>();
        apiKeys.add(apiKey);
        this.apiKeys = apiKeys;
        return this;
    };


    /*
     * @Description: api的key
     * @Author: hmai
     * @Date: 2023/6/19 14:31
     * @Param: apiKeys
     * @return: com.sky.future.chat.ChatAi
     */
    public ChatAi apiKey(List<String> apiKeys){
        this.apiKeys = apiKeys;
        return this;
    };

    /*
    * @Description: api地址
    * @Author: hmai
    * @Date: 2023/6/19 14:31
    * @Param: apiHost
    * @return: com.sky.future.chat.ChatAi
    */
    public ChatAi apiHost(String apiHost){
        this.apiHost = apiHost;
        return this;
    }


    /*
    * @Description: 初始化对话模型，需有各个对话模型子类去实现
    * @Author: hmai
    * @Date: 2023/6/19 14:41
    * @return: com.sky.future.chat.ChatAi
    */
    public ChatAi init(){
        return this;
    }


    /*
     * @Description: 用户发送的最新数据的tokens数
     * @Author: hmai
     * @Date: 2023/6/19 19:43
     * @Param: content 用户发送的内容
     * @Param: systemContent role角色是system时的内容信息
     * @return: boolean
     */
    public Integer useMessagesTokens(String content, String systemContent){
        return 0;
    }

    /*
     * @Description: AI模型回复的内容tokens数
     * @Author: hmai
     * @Date: 2023/6/19 19:43
     * @Param: content AI模型回复的内容
     * @return: boolean
     */
    public Integer replyMessagesTokens(String content){
        return 0;
    }



    /*
     * @Description: 用户发送的最新数据的tokens数是否超过了模型最大限制
     * @Author: hmai
     * @Date: 2023/6/19 19:43
     * @Param: content 用户发送的内容
     * @Param: systemContent role角色是system时的内容信息
     * @return: boolean
     */
    public boolean useMessagesTokensOutBounds(String content, String systemContent){
        return false;
    }

    /*
    * @Description:  构建待传入模型的信息
    *   1、将历史信息、新的聊天内容、role角色是system时的内容信息整成一个数据模型中；
    *   2、根据模型的tokens限制剪裁内容；
    * @Author: hmai
    * @Date: 2023/6/19 14:45
    * @Param: historyMessages 历史问答消息json字符串
    * @Param: newContent 用户输入的新聊天内容
    * @Param: systemContent role角色是system时的内容信息
    * @return: messages:构建待传入模型的json信息;tokens:信息的tokens数
    */
    public Map<String, String> buildMessages(String historyMessages, String newContent, String systemContent){
        return null;
    }


    /*
    * @Description: 对话
    *   需要各个模型将message转为适宜的数据结构，结合传入的模型参数构建消息体在传入模型中。
    * @Author: hmai 
    * @Date: 2023/6/19 15:03 
    * @Param: messages 待传入模型的对话信息（json字符串）
    * @Param: modelPram 模型参数（json字符串）
    * @return: 返回的字符串信息
    */
    public String chat(String messages, String modelPram){
        // 是否需要把token的计算也进来
        return null;
    }

    /*
    * @Description: 流式对话
    * @Author: hmai
    * @Date: 2023/6/19 15:17
    * @Param: messages 待传入模型的对话信息（json字符串）
    * @Param: modelPram 模型参数（json字符串）
    * @Param: StreamListener 流监听器
    * @return: void
    */
    public void chatStream(String messages, String modelPram, AbstractStreamListener StreamListener){
    }


    /*
    * @Description: 根据tokens计算花费，需有各个对话模型子类去实现；
    * @Author: hmai
    * @Date: 2023/6/19 15:33
    * @Param: token_request 用户发送信息的tokens数
    * @Param: token_response 模型返回信息的tokens数
    * @Param: pointWeightsPre 积分权重的单价(公式：1rmb兑换成积分数*价格权重（在接口价格基础上的浮动）)
    * @Param: exRate 汇率（模型的结算货币和人民币的汇率转化）
    * @return: java.lang.Integer
    */
    public Double billing(Integer token_request, Integer token_response, double pointWeightsPre, double exRate){
        // token_request*请求内容单个token的价格*【美刀和rmb的汇率】*【1rmb和系统聊天积分的比例*价格权重】
        // token_response*返回内容单个token的价格*【美刀和rmb的汇率】*【1rmb和系统聊天积分的比例*价格权重】
        // 例子：0.0001*7.2*（10000*价格权重）
        Double requestBilling = token_request*model.getRequestPrice()*exRate*pointWeightsPre;
        Double responseBilling = token_response*model.getResponsePrice()*exRate*pointWeightsPre;
        return requestBilling+responseBilling;
    }

    /*
    * @Description: 构建历史消息，将新的问答内容纳入到历史信息中，并返回
    * @Author: hmai
    * @Date: 2023/6/19 15:49
    * @Param: [chatMessage, aiReply]
    * @return: java.lang.String
    */
    public String buildHistoryMessage(String chatMessage, String aiReply){
        return null;
    }
}

