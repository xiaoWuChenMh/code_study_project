package com.future.java.nio;

import java.nio.ByteBuffer;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/9/17 10:48
 */
public class BytebufferTest {

    public static void main(String[] args) {
        BytebufferTest bytebufferTest = new BytebufferTest();
    }

    public void create(){
        System.out.println("将一个byte数组转为一个Bytebuffer：");
        byte[] metadata = "abc".getBytes();
        // 调用的是下面的构造器,参数默认值： offset=0, length=array.length
        ByteBuffer create_one = ByteBuffer.wrap(metadata);
        // array:新缓冲区将基于该字节数组创建;
        // offset:起始索引，不得为负且不得大于array.length;
        // length:长度，不能为负且不大于array.length-开始。;
        ByteBuffer create_two = ByteBuffer.wrap(metadata,1,5);
    }

}
