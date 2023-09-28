package com.future.chat.constant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description
 * @Author hmai
 * @Date 2023/6/19 21:09
 */
@Getter
@Setter
@ToString
public class ChatOpenAiPram {

    private Double temperature = 1D;

    private Double topP = 1D;

    private Integer n = 1;

    private Integer maxTokens = 3000;


}