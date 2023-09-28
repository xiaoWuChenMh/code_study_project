package com.future.enums;

import java.util.Optional;

/**
 * @Description 参考 com.knuddels.jtokkit.api.ModelType 生成自己的聊天模式类型。
 * @Author hma
 * @Date 2023/5/6 20:17
 */
public enum ChatModelType {

    GPT_4(ChatGroupType.GPT_4,"gpt-4",EncodingType.CL100K_BASE, 8192,0.0,0.0),
    GPT_4_32K(ChatGroupType.GPT_4,"gpt-4-32k", EncodingType.CL100K_BASE, 32768,0.0,0.0),
    GPT_3_5_TURBO(ChatGroupType.GPT_3_5,"gpt-3.5-turbo", EncodingType.CL100K_BASE, 4096,0.0,0.0),
    GPT_3_5_TURBO_16K(ChatGroupType.GPT_3_5,"gpt-3.5-turbo-16k", EncodingType.CL100K_BASE, 16384,0.0,0.0),

    GPT_3_5_TURBO_0613(ChatGroupType.GPT_3_5,"gpt-3.5-turbo-0613", EncodingType.CL100K_BASE, 4096,0.0,0.0);

    private final ChatGroupType group;
    private final String name;
    private final EncodingType encodingType;
    private final int maxContextLength;
    private final double requestPrice;
    private final double responsePrice;

    private ChatModelType(ChatGroupType group, String name, EncodingType encodingType, int maxContextLength, double requestPrice, double responsePrice) {
        this.group = group;
        this.name = name;
        this.encodingType = encodingType;
        this.maxContextLength = maxContextLength;
        this.requestPrice = requestPrice;
        this.responsePrice = responsePrice;
    }

    public ChatGroupType getGroup() {
        return this.group;
    }

    public String getName() {
        return this.name;
    }

    public EncodingType getEncodingType() {
        return this.encodingType;
    }

    public int getMaxContextLength() {
        return this.maxContextLength/10*8;
    }


    public double getRequestPrice() {
        return this.requestPrice;
    }

    public double getResponsePrice() {
        return this.responsePrice;
    }

    public static Optional<ChatModelType> fromName(String name) {
        ChatModelType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ChatModelType modelType = var1[var3];
            if (modelType.getName().equals(name)) {
                return Optional.of(modelType);
            }
        }

        return Optional.empty();
    }
}
