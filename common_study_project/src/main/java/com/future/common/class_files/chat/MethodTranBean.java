package com.future.common.class_files.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author v_hhuima
 * @Date 2023/10/7 18:00
 */
@Getter
public class MethodTranBean {

    private List<String> parameters = new ArrayList<>();

    private String comment;

    private Map<String,String> codes = new HashMap<>();

    public void setComment(String comment){
        this.comment = "// "+comment+"\n";
    }

    public void setParameter(String name,String translation){
        String value = "// @ "+name+":"+translation+"\n";
        parameters.add(value);
    }

    public void setCodes(int index,String code,String translation){
        String key = index+"_"+code;
        codes.put(key,"// "+translation+"\n");
    }



}
