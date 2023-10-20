package com.future.common.class_files.source;


import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author v_hhuima
 * @Date 2023/10/7 16:05
 */
public class SourceCodeRead {

    // 类的包名匹配
    public static final String pachMatch= "^package\\s+([\\w|.]+)\\s*;$";

    // 类名匹配
    public static final String classMatch  = "public\\s+[final]*\\s*class\\s+(\\w+)\\s+[extends|\\s|\\w|{]*";

    // 变量匹配
    public static final String variableMatch = "(private|public|protected)?\\s+(static\\s+)?(final\\s+)?\\w+(\\[\\])*\\s+[\\w<>.,\\s]+\\s+\\w+(\\s*=\\s*[^;]+)?;";


    /*
    * @Description:
    *    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
    * @Author: huima
    * @Date: 2023/10/7 16:11
    * @Param: [inputFile, outputFile, className]
    * @return: void
    */
    public void writePrefixSorouce(String inputFile,BufferedWriter writer,String className) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        while ((line = reader.readLine()) != null) {
            if(classNameMacht(line).equals(className)){
                writer.write(line);
                writer.newLine(); // 写入换行符
                break;
            }
            writer.write(line);
            writer.newLine(); // 写入换行符
        }
        reader.close();
    }

    /*
     * @Description: 是否类名
     * @Author: hma
     * @Date: 2023/9/26 18:05
     * @Param: [line]
     * @return: java.lang.String
     */
    private String classNameMacht(String line){
        line = line.trim();
        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(classMatch);
        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(line);
        // 判断是否匹配成功
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
