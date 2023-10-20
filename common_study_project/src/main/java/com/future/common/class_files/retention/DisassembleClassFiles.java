//package com.future.common.class_files;
//
//import com.future.common.class_files.metadata.ClassMetadata;
//import com.future.common.class_files.source.SourceManage;
//import com.github.javaparser.StaticJavaParser;
//import com.github.javaparser.ast.CompilationUnit;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @Description
// * @Author hma
// * @Date 2023/9/24 16:15
// */
//public class DisassembleClassFiles {
//
//
//    private static ClassMetadata metadata;
//
//    public static void main(String[] args) {
//        // 给定一个类的源代码文件路径
//        String filePath = "E:\\workspace\\IntelliJIDEA\\yuanma\\spark-source_code_read\\core\\src\\main\\java\\org\\apache\\spark\\memory\\";
//        String fileName = "TaskMemoryManager.java";
//        // 获取类的元数据
//        metadata = initMetadata(filePath,fileName);
//
//        lineReadFile(filePath,fileName);
//    }
//
//
//    /*
//    * @Description: 初始化元数据
//    * @Author: hma
//    * @Date: 2023/9/25 12:12
//    * @Param: [filePath, fileName]
//    * @return: com.future.common.class_files.metadata.ClassMetadata
//    */
//    public static ClassMetadata initMetadata(String filePath,String fileName){
//        File file = new File(filePath+fileName);
//        // 解析源代码文件
//        CompilationUnit cu = null;
//        try {
//            cu = StaticJavaParser.parse(file);
//            return new SourceManage().initMetadata(cu);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /*
//     * @Description: 按行读取文件
//     * @Author: hma
//     * @Date: 2023/9/24 15:53
//     * @Param: [filePath]
//     * @return: void
//     */
//    private static void lineReadFile(String filePath,String fileName){
//        boolean codeStart = false;
//        Queue<String> methodsTag = new LinkedList<>();
//        List<String> codetemStorage = new ArrayList<>();
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(filePath+fileName));
//            String line;
//            while ((line = br.readLine()) != null) {
//                // true，代表读取到声明类语句后的源代码了
//                if(codeStart){
//                    try{
//                        int a = methodsTag.size();
//                        // 判断是否为方法和类变量外的空行
//                        if(codetemStorage.size()==0 && line.trim().length()==0){
//                            System.out.println(line);
//                            continue;
//                        }
//                        if(line.trim().equals("* @return number of bytes successfully granted (<= N).")){
//                            System.out.print("");
//                        }
//                        // 判断是否为方法和类变量外的注释：
//                        if(codetemStorage.size()==0 && isComment(line)){
//                            System.out.println(line);
//                            continue;
//                        }
//                        if(line.contains("{") || line.contains("}")){
//                            System.out.print("");
//                        }
//                        // 判断是否为等待输出行
//                        if(isWaitLine(line,methodsTag)){
//                            codetemStorage.add(line);
//                            continue;
//                        }
//                        // 判断是否到达方法和变量结束标识
//                        if(isEndTag(line,methodsTag)){
//                            codetemStorage.add(line);
//                            cahtDesc(codetemStorage);
//                            //将cahtDesc中获取的内容和源代码拼接后输出
//                            continue;
//                        }else{
//                            codetemStorage.add(line);
//                        }
//                    }catch (Exception e){
//                        System.out.println("=============错误行：Start===============");
//                        System.out.println(line);
//                        System.out.println("=============错误行：End===============");
//                        throw e;
//                    }
//                }else {
//                    codeStart = codeStart==true ? true : classNameMacht(line).equals(metadata.getClassName());
//                    System.out.println(line);
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//    * @Description: 是否类名
//    * @Author: hma
//    * @Date: 2023/9/26 18:05
//    * @Param: [line]
//    * @return: java.lang.String
//    */
//    public static String classNameMacht(String line){
//        line = line.trim();
//
//        // 创建 Pattern 对象
//        Pattern pattern = Pattern.compile(ClassGrammarMatchRule.CLASS_MATCH);
//
//        // 创建 Matcher 对象
//        Matcher matcher = pattern.matcher(line);
//
//        // 判断是否匹配成功
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            return "";
//        }
//    }
//
//    /*
//    * @Description: 是否注释语句：以如下开头的： / 和 *
//    * @Author: hma
//    * @Date: 2023/9/25 16:45
//    * @Param: [line]
//    * @return: boolean
//    */
//    public static boolean isComment(String line){
//       String firstCahr =  line.trim().substring(0,1);
//       if(firstCahr.equals("/")){
//           return true;
//       }
//       if(firstCahr.equals("*")){
//            return true;
//        }
//       return false;
//    }
//
//    /*
//    * @Description: 是否为等待输出行，比如注解（@开头）、方法内注释、方法的有括号，方法内的空行
//    * @Author: hma
//    * @Date: 2023/9/25 16:46
//    * @Param: [line]
//    * @return: boolean
//    */
//    public static boolean isWaitLine(String line,Queue<String> methodsTag){
//        if(line.trim().length()==0){
//            return true;
//        }
//        String firstCahr =  line.trim().substring(0,1);
//        if(firstCahr.equals("@")){
//            return true;
//        }
//        if(line.contains("{") && line.contains("{}") ){
//            methodsTag.add("{");
//            return true;
//        }
//        if(firstCahr.equals("/")){
//            return true;
//        }
//        if(firstCahr.equals("*")){
//            return true;
//        }
//        return false;
//    }
//
//    /*
//    * @Description: 如何判断方法和变量结束标识
//    * @Author: hma
//    * @Date: 2023/9/26 11:19
//    * @Param: [line, methodsTag]
//    * @return: boolean
//    */
//    public static boolean isEndTag(String line,Queue<String> methodsTag){
//        String trimLine = line.trim();
//        String lastCahr =  String.valueOf(trimLine.charAt(trimLine.length() - 1));
//        if(line.contains("}")){
//            methodsTag.poll();
//        }
//        // 代表是变量，而非方法
//        if(methodsTag.size()==0 && lastCahr.equals(";")){
//            return true;
//        }
//        // 如果包含了}且methodsTag==0，那就说明是方法了
//        if(methodsTag.size()==0){
//            return true;
//        }
//        return false;
//    }
//
//    public static String cahtDesc(List<String> codetemStorage){
//        codetemStorage.forEach(code -> {
//            System.out.println(code);
//        });
//        codetemStorage.clear();
//        return "";
//    }
//}
