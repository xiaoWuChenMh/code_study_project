package com.future.common.class_files;

import com.future.common.class_files.metadata.ClassField;
import com.future.common.class_files.metadata.ClassMetadata;
import com.future.common.class_files.metadata.ClassMethod;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @Description 分析源代码，获取类全限定名、属性、方法
 * @Author huima
 * @Date 2023/7/19 15:44
 */
public class ClassAnalyzer {

    /*
    * @Description: 获取代码的元数据
    * @Author: hma
    * @Date: 2023/9/25 11:55
    * @Param: [cu]
    * @return: com.future.common.class_files.metadata.ClassMetadata
    */
    public ClassMetadata getCodeMetadata(CompilationUnit cu){
        ClassMetadata classMetadata = new ClassMetadata();
        classMetadata.setPackName(getPackageName(cu));
        classMetadata.setClassName(getClasName(cu));
        classMetadata.setFields(getFields(cu));
        classMetadata.setMethods(getMethods(cu));
        return classMetadata;
    }


    /*
    * @Description: 获取包名
    * @Author: hma
    * @Date: 2023/9/24 17:15
    * @Param: [cu]
    * @return: java.lang.String
    */
    private String getPackageName(CompilationUnit cu) {
        return cu.getPackageDeclaration().get().getNameAsString();
    }

    /*
    * @Description: 获取类名，包含包名
    * @Author: hma
    * @Date: 2023/7/19 19:42
    * @Param: [cu]
    * @return: java.lang.String
    */
    private String getClasName(CompilationUnit cu){
        return cu.getTypes().get(0).getNameAsString();
    }

    /*
    * @Description: 获取类的属性
    * @Author: hma
    * @Date: 2023/7/19 20:11
    * @Param: [cu]
    * @return: java.util.List<java.lang.StringBuilder>
    */
    private List<ClassField> getFields(CompilationUnit cu){
        List<ClassField> result = new ArrayList<>();
        // 获取类的所有成员变量
        List<FieldDeclaration> fields = cu.findAll(FieldDeclaration.class);
        // 遍历所有成员变量并输出它们的注释和名称
        for (FieldDeclaration field : fields) {
            String comment = field.getComment().map(Comment::toString).orElse("");
            String filedCode = field.getTokenRange().map(TokenRange::toString).orElse("");
            String filedName = field.getVariables().getFirst().toString().split("=")[0].trim();

            ClassField classField = new ClassField();
            classField.setComment(comment);
            classField.setFiledCode(filedCode);
            classField.setFieldName(filedName);
            result.add(classField);
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
    private List<ClassMethod> getMethods(CompilationUnit cu){
        List<ClassMethod> result = new ArrayList<>();
        // 获取方法名和方法内的所有语句
        List<MethodDeclaration> methods = cu.getTypes().get(0).getMethods();
        System.out.println("Methods:");
        for (MethodDeclaration method : methods) {
            List<String> params = new ArrayList<>();
            method.getParameters().forEach(parameter -> {
                String paramName = parameter.toString().split("\\s+")[1].trim();
                params.add(paramName);
            });

            String name = method.getName().toString();
            String type = method.getType().toString();
            String comment = method.getComment().map(Comment::toString).orElse("");
            String code = method.getTokenRange().map(TokenRange::toString).orElse("");

            ClassMethod classMethod = new ClassMethod();
            classMethod.setName(name);
            classMethod.setReturnType(type);
            classMethod.setComment(comment);
            classMethod.setParams(params);
            classMethod.setCode(code);
            result.add(classMethod);
        }
        return result;
    }

}
