package com.future.common.class_files.rule;

/**
 * @Description
 * @Author hma
 * @Date 2023/9/24 16:22
 */
public class ClassGrammarMatchRule {

    // 类的包名匹配
    public static final String PACK_MATCH = "^package\\s+([\\w|.]+)\\s*;$";

    // 类名匹配
    public static final String CLASS_MATCH  = "public\\s+class\\s+(\\w+)\\s+[{]*";

    // 变量匹配
    public static final String VARIABLE_MATCH = "(private|public|protected)?\\s+(static\\s+)?(final\\s+)?\\w+(\\[\\])*\\s+[\\w<>.,\\s]+\\s+\\w+(\\s*=\\s*[^;]+)?;";


}
