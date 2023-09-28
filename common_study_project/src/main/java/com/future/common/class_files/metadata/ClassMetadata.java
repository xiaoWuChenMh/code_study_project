package com.future.common.class_files.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author hma
 * @Date 2023/9/24 16:17
 */
@Getter
@Setter
public class ClassMetadata {

    // 包名
    private String packName = "";

    // 类名
    private String className = "";

    // 变量源代码
    private List<ClassField> Fields = new ArrayList<>();

    // 方法源代码
    private List<ClassMethod> Methods = new ArrayList<>();




}
