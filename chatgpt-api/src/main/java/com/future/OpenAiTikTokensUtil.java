package com.future;

import com.future.enums.ChatGroupType;
import com.future.enums.ChatModelType;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.plexpt.chatgpt.entity.chat.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description
 * @Author hmai
 * @Date 2023/6/19 18:01
 */
public class OpenAiTikTokensUtil {

    private static EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();


    /*
     * @Description:  通过模型名称计算messages获取编码数组
     * 参考处理逻辑：
     *   官网：https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb
     *   github：https://github.com/Grt1228/chatgpt-java/blob/1.0.14/Tokens_README.md
     * @Author: hmai
     * @Date: 2023/6/19 17:00
     * @Param: []
     * @return: java.lang.Integer
     */
    public static Integer tokens(ChatModelType model, List<Message> messages) {
        Encoding encoding = registry.getEncodingForModel(model.getName()).get();

        int tokensPerMessage = 0;
        int tokensPerName = 0;
        //3.5统一处理
        if (model.getGroup().equals(ChatGroupType.GPT_3_5)) {
            tokensPerMessage = 4;
            tokensPerName = -1;
        }
        //4.0统一处理
        if (model.getGroup().equals(ChatGroupType.GPT_4)) {
            tokensPerMessage = 3;
            tokensPerName = 1;
        }
        int sum = 0;

        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()) {
            Message message = iterator.next();
            sum += tokensPerMessage;
            sum += encoding.countTokens(message.getContent());
            sum += encoding.countTokens(message.getRole());
            //传递的数据中没有name
//            sum += tokens(encoding, msg.getName());
//            if (StrUtil.isNotBlank(msg.getName())) {
//                sum += tokensPerName;
//            }
        }
        sum += 3;
        return sum;
    }

    public Integer tokens(ChatModelType model, Message message) {
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        return tokens(model,messages);

    }
}
