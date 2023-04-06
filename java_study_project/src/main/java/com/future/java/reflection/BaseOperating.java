package com.future.java.reflection;


/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/8/17 10:14
 */
public class BaseOperating {

    public static void main(String[] args) {
        BaseOperating baseOperating = new BaseOperating();
        String sdf = simpleName(baseOperating.getClass());
        System.out.println(sdf);

    }

    private static String simpleName(Class<?> clazz){
        String name = clazz.getName(); //获取类对象的全量限定名
        name = name.replaceAll("\\$[0-9]+", "\\$");
        int start = name.lastIndexOf(36); //ascii=36=$
        if (start == -1) {
            start = name.lastIndexOf(46); //获取最后一个点的位置；ascii=46=.
        }
        return name.substring(start + 1); //截取字符串获取类名
    }

}



