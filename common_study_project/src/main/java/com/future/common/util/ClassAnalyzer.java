package com.future.common.util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
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
    * @Author: v_hhuima
    * @Date: 2023/7/19 20:00
    * @Param:
    * @return:
    */
    private Integer age=18;

    public static void main(String[] args) {

        // 给定一个类的源代码文件路径
        String filePath = "E:\\workspace\\IntelliJIDEA\\yuanma\\spark-source_code_read\\core\\src\\main\\scala\\org\\apache\\spark\\memory\\MemoryPool.scala";

        try {
            // 读取Java源代码文件
            File file = new File(filePath);

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
    * @Author: huima
    * @Date: 2023/7/19 19:42
    * @Param: [cu]
    * @return: java.lang.String
    */
    public static String getClasName(CompilationUnit cu){
        String packageName = cu.getPackageDeclaration().get().getNameAsString();
        String className = cu.getTypes().get(0).getNameAsString();
        return packageName+"."+className;
    }

    /*
    * @Description: 获取类的属性
    * @Author: huima
    * @Date: 2023/7/19 20:11
    * @Param: [cu]
    * @return: java.util.List<java.lang.StringBuilder>
    */
    public static List<String> getFields(CompilationUnit cu){
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
    * @Author: huima
    * @Date: 2023/7/19 20:32
    * @Param: [cu]
    * @return: java.util.List<java.lang.String>
    */
    public static List<String> getMethods(CompilationUnit cu){
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
