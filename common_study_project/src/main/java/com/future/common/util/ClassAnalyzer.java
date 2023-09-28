package com.future.common.util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description 分析源代码，获取类全限定名、属性、方法
 * @Author huima
 * @Date 2023/7/19 15:44
 */
public class ClassAnalyzer {

    //用户名
    private String name="adsf";

    /*
    * @Description: 用户姓名
    * @Author: hma
    * @Date: 2023/7/19 20:00
    * @Param:
    * @return:
    */
    private Integer age=18;

    public static void main(String[] args) {

        // 给定一个类的源代码文件路径
        String filePath = "E:\\workspace\\IntelliJIDEA\\yuanma\\spark-source_code_read\\core\\src\\main\\java\\org\\apache\\spark\\memory\\";

        String fileName = "TaskMemoryManager.java";

//        lineReadFile(filePath,fileName);

        parseClassFile(filePath,fileName);

    }

    /*
    * @Description: 按行读取文件
    * @Author: hma
    * @Date: 2023/9/24 15:53
    * @Param: [filePath]
    * @return: void
    */
    private static void lineReadFile(String filePath,String fileName){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath+fileName));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * @Description: 解析Class文件，分别输出类属性和方法
    * @Author: hma
    * @Date: 2023/9/24 15:50
    * @Param: [file]
    * @return: void
    */
    private static void parseClassFile(String filePath,String fileName){

        try {
            // 读取Java源代码文件
            File file = new File(filePath+fileName);

            // 解析源代码文件
            CompilationUnit cu = StaticJavaParser.parse(file);

            // 获取类名
            String className = getClasName(cu);
            System.out.println("Class: " + className);

            // 获取属性名
            System.out.println("Fields:");
            getFields(cu).stream().forEach(field -> {
                System.out.println(field.toString());
            });

            // 获取方法名和方法内的所有语句
            System.out.println("Methods:");
            getMethods(cu).stream().forEach(method -> {
                System.out.println(method);
                System.out.println("=========================");
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    * @Description: 获取类名，包含包名
    * @Author: hma
    * @Date: 2023/7/19 19:42
    * @Param: [cu]
    * @return: java.lang.String
    */
    private static String getClasName(CompilationUnit cu){
        String packageName = cu.getPackageDeclaration().get().getNameAsString();
        String className = cu.getTypes().get(0).getNameAsString();
        return packageName+"."+className;
    }

    /*
    * @Description: 获取类的属性
    * @Author: hma
    * @Date: 2023/7/19 20:11
    * @Param: [cu]
    * @return: java.util.List<java.lang.StringBuilder>
    */
    private static List<String> getFields(CompilationUnit cu){
        List<String> result = new ArrayList<>();
        // 获取类的所有成员变量
        List<FieldDeclaration> fields = cu.findAll(FieldDeclaration.class);
        // 遍历所有成员变量并输出它们的注释和名称
        for (FieldDeclaration field : fields) {
            result.add(field.toString());
        }
        return result;
    }

    /*
    * @Description: 获取压缩后的方法源代码
    * @Author: hma
    * @Date: 2023/7/19 20:32
    * @Param: [cu]
    * @return: java.util.List<java.lang.String>
    */
    private static List<String> getMethods(CompilationUnit cu){
        List<String> result = new ArrayList<>();
        // 获取方法名和方法内的所有语句
        List<MethodDeclaration> methods = cu.getTypes().get(0).getMethods();
        System.out.println("Methods:");
        for (MethodDeclaration method : methods) {
            String methodCode = method.toString().replaceAll("\\s+", " ");
                    //.replaceAll("\"","\\\\\"");
            result.add(methodCode);
        }
        return result;
    }


}
