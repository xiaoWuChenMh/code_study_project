//package com.future.class_files;
//
//import com.future.common.class_files.rule.ClassGrammarMatchRule;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @Description
// * @Author hma
// * @Date 2023/9/24 16:24
// */
//public class ClassGrammarMatchRuleTest {
//
//    public static void main(String[] args) {
//        classNameMacht();
//    }
//
//
//    public static void classNameMacht(){
//        String packName = "public class TaskMemoryManager { ".trim();
//
//        // 创建 Pattern 对象
//        Pattern pattern = Pattern.compile(ClassGrammarMatchRule.CLASS_MATCH);
//
//        // 创建 Matcher 对象
//        Matcher matcher = pattern.matcher(packName);
//
//        // 判断是否匹配成功
//        if (matcher.find()) {
//            System.out.println("匹配成功");
//            // 提取匹配到的包名
//            System.out.println("类名：" + matcher.group(1));
//        } else {
//            System.out.println("匹配失败");
//        }
//    }
//
//    public static void packNameMacht(){
//        String packName = "package org.apache.spark.memory ;".trim();
//
//        // 创建 Pattern 对象
//        Pattern pattern = Pattern.compile(ClassGrammarMatchRule.PACK_MATCH);
//
//        // 创建 Matcher 对象
//        Matcher matcher = pattern.matcher(packName);
//
//        // 判断是否匹配成功
//        if (matcher.find()) {
//            System.out.println("匹配成功");
//            // 提取匹配到的包名
//            System.out.println("包名：" + matcher.group(1));
//        } else {
//            System.out.println("匹配失败");
//        }
//
//
////        boolean isMatch = Pattern.matches(ClassGrammarMatchRule.PACK_MATCH, packName);
////        if (isMatch) {
////            packName = packName.replace("package","")
////                    .replace(";","")
////                    .trim();
////            System.out.println("Match successful:"+packName);
////        } else {
////            System.out.println("Match unsuccessful");
////        }
//    }
//}
