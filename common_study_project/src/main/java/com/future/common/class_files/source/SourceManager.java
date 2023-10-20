package com.future.common.class_files.source;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.future.common.class_files.metadata.ClassField;
import com.future.common.class_files.metadata.ClassMethod;
import com.future.common.class_files.metadata.ClassMetadata;



/**
 * @Description
 * @Author huima
 * @Date 2023/10/7 16:27
 */
public class SourceManager {

   private ClassMetadata classMetadata;

   private SourceCodeRead sourceCodeRead = new SourceCodeRead();

    /*
     * @Description: 初始化源代码的元数据
     * @Author: hma
     * @Date: 2023/9/25 11:55
     * @Param: [cu]
     * @return: com.future.common.class_files.metadata.ClassMetadata
     */
    public ClassMetadata initMetadata(CompilationUnit cu){
        classMetadata = new ClassMetadata();
        classMetadata.setPackName(getPackageName(cu));
        classMetadata.setClassName(getClasName(cu));
        classMetadata.setFields(getFields(cu));
        classMetadata.setMethods(getMethods(cu));
        return classMetadata;
    }

    /*
    * @Description: 将前置源码写出，前置源码指的是类声明前的语句包含类声明
    * @Author: hma
    * @Date: 2023/10/7 16:34
    * @Param: []
    * @return:
    */
    public void writePrefixSorouce(String inputFile, BufferedWriter writer) throws IOException {
        String className = classMetadata.getClassName();
        sourceCodeRead.writePrefixSorouce(inputFile,writer,className);
    }

    /*
    * @Description: 获取元数据
    * @Author: hma
    * @Date: 2023/10/8 12:30
    * @Param: []
    * @return: com.future.common.class_files.metadata.ClassMetadata
    */
    public ClassMetadata getMetadata(){
        return classMetadata;
    }

    public void writerCode(BufferedWriter writer,String code) throws IOException {
        writer.write(code);
    }

    // ============================== 根据语法树拆解源代码，构建元数据 =============================================

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
        constructorCode(cu,result);
        methodCode(cu,result);
        return result;
    }

    /*
    * @Description: 构造器
    * @Author: hma
    * @Date: 2023/10/9 15:17
    * @Param: [cu]
    * @return: java.util.List<com.future.common.class_files.metadata.ClassMethod>
    */
    private void constructorCode(CompilationUnit cu,List<ClassMethod> result){
        List<ConstructorDeclaration> constructor = cu.getTypes().get(0).getConstructors();
        for (ConstructorDeclaration method : constructor) {
            List<String> params = new ArrayList<>();
            method.getParameters().forEach(parameter -> {
                String paramName = parameter.toString().split("\\s+")[1].trim();
                params.add(paramName);
            });

            String name = method.getName().toString();
            String code = method.getTokenRange().map(TokenRange::toString).orElse("");
            String comment = method.getComment().map(Comment::toString).orElse("");

            ClassMethod classMethod = new ClassMethod();
            classMethod.setName(name);
            classMethod.setComment(comment);
            classMethod.setParams(params);
            classMethod.setCode(code);
            result.add(classMethod);
        }
    }

    /*
    * @Description: 方法
    * @Author: hma
    * @Date: 2023/10/9 15:19
    * @Param: [cu, result]
    * @return: void
    */
    private void methodCode(CompilationUnit cu,List<ClassMethod> result){
        List<MethodDeclaration> methods = cu.getTypes().get(0).getMethods();
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
    }


}
