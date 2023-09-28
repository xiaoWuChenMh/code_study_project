package com.future.chat;


import com.future.enums.ChatModelType;

import java.math.BigDecimal;

/**
 * @Description
 *   https://openai.com/pricing
 *   https://platform.openai.com/tokenizer
 * @Author huima
 * @Date 2023/7/12 15:52
 */
public class ChatBilling {

    private static BigDecimal thousand = new BigDecimal(1000);

    private static BigDecimal exchangeRate = new BigDecimal(7.1888);

    /*
    * @Description: 获取花费
    * @Author: huima
    * @Date: 2023/7/12 16:24
    * @Param: [modelType, contextToken, replyToken]
    * @return: java.math.BigDecimal
    */
    public static BigDecimal billing(String model, int contextToken, int replyToken){
        BigDecimal dollar = null;
        BigDecimal contextBilling = new BigDecimal(contextToken).divide(thousand);
        BigDecimal replyBilling = new BigDecimal(replyToken).divide(thousand);
        if(model.equals(ChatModelType.GPT_3_5_TURBO.getName())){
            BigDecimal inputPrice = new BigDecimal(0.0015);
            BigDecimal outPrice = new BigDecimal(0.002);
            dollar =contextBilling.multiply(inputPrice).add(replyBilling.multiply(outPrice));
        } else if(model.equals(ChatModelType.GPT_3_5_TURBO_16K.getName())){
            BigDecimal inputPrice = new BigDecimal(0.003);
            BigDecimal outPrice = new BigDecimal(0.004);
            dollar =contextBilling.multiply(inputPrice).add(replyBilling.multiply(outPrice));
        }else if(model.equals(ChatModelType.GPT_4.getName())){
            BigDecimal inputPrice = new BigDecimal(0.03);
            BigDecimal outPrice = new BigDecimal(0.06);
            dollar =contextBilling.multiply(inputPrice).add(replyBilling.multiply(outPrice));
        }else if(model.equals(ChatModelType.GPT_4_32K.getName())){
            BigDecimal inputPrice = new BigDecimal(0.06);
            BigDecimal outPrice = new BigDecimal(0.12);
            dollar =contextBilling.multiply(inputPrice).add(replyBilling.multiply(outPrice));
        }else{
            // 默认按gpt-3.5-turbo-16k资费计算
            BigDecimal inputPrice = new BigDecimal(0.003);
            BigDecimal outPrice = new BigDecimal(0.004);
            dollar =contextBilling.multiply(inputPrice).add(replyBilling.multiply(outPrice));
        }
        // 美元转rmb
        return dollar.multiply(exchangeRate);
    }
}
