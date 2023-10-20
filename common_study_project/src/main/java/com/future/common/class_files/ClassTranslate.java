package com.future.common.class_files;

import com.future.common.class_files.chat.ClassTranslateChat;
import com.future.common.class_files.chat.MethodTranBean;
import com.future.common.class_files.metadata.ClassField;
import com.future.common.class_files.metadata.ClassMethod;
import com.future.common.class_files.source.SourceManager;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.*;
import java.util.Map;

/**
 * @Description
 * @Author v_hhuima
 * @Date 2023/10/7 16:47
 */
public class ClassTranslate {

    // 方法内的代码段，需要以；号分割
    SourceManager sourceManager;

    ClassTranslateChat classTranslateChat =  new ClassTranslateChat();



    public void codeTranslation(String inputPath, String writerPath) throws IOException {
        File inputFile = new File(inputPath);
        // java语法解析器
        CompilationUnit cu = StaticJavaParser.parse(inputFile);
        //初始化源代码管理器
        sourceManager = new SourceManager();
        sourceManager.initMetadata(cu);
        // 将前置源码写出
        BufferedWriter writer = new BufferedWriter(new FileWriter(writerPath));
        sourceManager.writePrefixSorouce(inputPath,writer);
        //翻译所有的类变量
        boolean isError = fieldTran(writer);
        if(isError){
            return;
        }
        // 翻译所有的类方法
        methodTran(writer);
    }

    /*
    * @Description:  翻译所有的类变量
    * @Author: hma
    * @Date: 2023/10/9 14:07
    * @Param: [writer]
    * @return: boolean
    */
    private boolean fieldTran(BufferedWriter writer){
//        String classPathName = sourceManager.getMetadata().getClassName();
//        for (ClassField classField : sourceManager.getMetadata().getFields()) {
//            String fieldCode = classField.getChatContent(classPathName);
//            String tranCode = classTranslateChat.fieldTran(fieldCode);
//            try {
//                if(classField.getComment().length()!=0){
//                    sourceManager.writerCode(writer,classField.getComment());
//                }
//                sourceManager.writerCode(writer,tranCode);
//                sourceManager.writerCode(writer,classField.getFiledCode());
//                sourceManager.writerCode(writer,"\n");
//            } catch (IOException e) {
//                e.printStackTrace();
//                writeClose(writer);
//                return true;
//            }
//        }
        return false;
    }

    /*
    * @Description:翻译所有的类方法
    * @Author: hma
    * @Date: 2023/10/9 14:11
    * @Param: [writer]
    * @return: boolean
    */
    private void methodTran(BufferedWriter writer){
        String classPathName = sourceManager.getMetadata().getClassName();
        try {
            for (ClassMethod classMethod : sourceManager.getMetadata().getMethods()) {
                String methodCode = classMethod.getChatContent(classPathName);
                if(!methodCode.contains("skip(long n)")){
                    continue;
                }
                MethodTranBean tranCodeBean = classTranslateChat.methodTran(methodCode);
                //输出方法注释
                if(null != classMethod.getComment() && classMethod.getComment().length()!=0){
                    sourceManager.writerCode(writer,classMethod.getComment());
                }
                sourceManager.writerCode(writer,tranCodeBean.getComment());
                //输出方法参数描述
                for (String parameter : tranCodeBean.getParameters()) {
                    sourceManager.writerCode(writer,parameter);
                }
                //输出方法内代码
                int validIndex = 0;
                Map<String,String> codeTrans = tranCodeBean.getCodes();
                String[] codes = classMethod.getCode().split("\n");
                for (int i = 0; i < codes.length; i++) {
                    String code = codes[i];
                    String key = validIndex+"_"+code.replaceAll("\\s+", " ").trim();
                    String tranStr = codeTrans.get(key);
                    if(null != tranStr){
                        validIndex+=1;
                        sourceManager.writerCode(writer,tranStr);
                        sourceManager.writerCode(writer,code);
                    } else {
                        sourceManager.writerCode(writer,code);
                    }
                }
                sourceManager.writerCode(writer,"\n");
            }
            sourceManager.writerCode(writer,"}\n");
        } catch (IOException e) {
            e.printStackTrace();
            writeClose(writer);
        }finally {
            writeClose(writer);
        }
    }

    private void writeClose(BufferedWriter writer){
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "E:\\workspace\\IntelliJIDEA\\yuanma\\spark-source_code_read\\core\\src\\main\\java\\org\\apache\\spark\\io\\";
        String inputFileName = "NioBufferedFileInputStream.java";
        String writeFileName = "NioBufferedFileInputStream_Tran.java";

        ClassTranslate classTranslate = new ClassTranslate();
        try {
            classTranslate.codeTranslation(filePath+inputFileName,filePath+writeFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
