package com.future.common.class_files.metadata;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author hma
 * @Date 2023/9/24 17:18
 */
@Getter
@Setter
public class ClassField {

   // 变量名
   private String fieldName;

   // 变量注释
   private String comment;

   // 持有变量的有效代码
   private String filedCode;

   
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
      str.append("属性： ").append("\\r\\n");
      str.append(comment).append("\\r\\n");
      str.append(filedCode).append("\\r\\n");
      return str.toString();
   }

   public String fristLine(){
      return filedCode.split("\\r\\n")[0];
   }

}
