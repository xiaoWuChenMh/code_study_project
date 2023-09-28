package com.future.common.class_files.metadata;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description
 * @Author hma
 * @Date 2023/9/24 17:18
 */
@Getter
@Setter
public class ClassMethod {

    // 方法名
    private String name;

    // 返回类型
    private String returnType;

    // 参数
    private List<String> params;

    // 变量注释
    private String comment;

    // 持有方法的有效代码
    private String code;

    /*
     * @Description: 获取chat需要的内容
     * @Author: hma
     * @Date: 2023/9/25 11:51
     * @Param: []
     * @return: java.lang.String
     */
    public String getChatContent(String classPathName){
        StringBuilder str = new StringBuilder();
        str.append(classPathName);
        str.append("方法名： ").append("\\r\\n");
        str.append(comment).append("\\r\\n");
        str.append(code.replaceAll("\\s+", " ")).append("\\r\\n");
        return str.toString();
    }

    public String fristLine(){
        return code.split("\\r\\n")[0];
    }

}
