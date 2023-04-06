package com.future.netty.bytebuf;

import io.netty.buffer.*;
import sun.security.util.Length;

import java.nio.charset.Charset;

/*
* @Description:
*     三种类型ByteBuf的创建方式（ByteBuf的分配）：堆缓冲区、直接缓冲区、复合缓冲区
*     由以下类提供该功能：
*           Unpooled
*           ByteBufAllocator接口的实现类: PooledByteBufAllocator  、 UnpooledByteBufAllocator
* @Author: xiaowuchen
* @Date: 2020/6/15 11:56
* @Param:
* @return:
*/
public class CreateByteBuf {
    public static void main(String[] args) {
        System.out.println("Channel通过 alloc方法可以获取ByteBufAllocator类");
        HeepBuffer();
        directBuffer();
        compositeBuffer();

        System.out.println("-----=========-------=========-----");

        readWriteString();
    }

    //堆缓冲区，最常用的把数据存储在JVM的堆空间中。这种模式也被称为支撑数据（通过校验是否有支撑数组来判断是否为堆缓冲）
    public static void HeepBuffer(){
        //默认初始容量和最大容量的  未池化的 基于堆内存存储的ByteBuf,内部调用的 UnpooledByteBufAllocator（非池化的ByteBufAllocator）
        ByteBuf heapBuffer1 = Unpooled.buffer();
        //默认初始容量和最大容量的  池化过的 基于堆内存存储的ByteBuf
        ByteBuf heapBuffer2 = new PooledByteBufAllocator().heapBuffer();
        if(heapBuffer1.hasArray()){
            System.out.printf("%s 是支撑数组%n ; ","heapBuffer1");
            heapBuffer1.writeLong(9);
            byte[] array = heapBuffer1.array(); //获取支撑数据的引用
            int arrayOffset = heapBuffer1.arrayOffset();
            int readerindex = heapBuffer1.readerIndex();
            int length = heapBuffer1.readableBytes();
            System.out.printf("数组偏移位=%d ; 读索引的位置=%d ;可读字节数=%d;内容=%d %n ",arrayOffset,readerindex,length,heapBuffer1.readLong());
        }
        if(heapBuffer2.hasArray()){
            System.out.printf("%s 是支撑数组%n","heapBuffer2");
        }
    }

    //直接缓冲区,驻留在常规的会被垃圾回收的堆外，缺点——分配释放昂贵
    public static void directBuffer(){
        //默认初始容量和最大容量的  未池化的 基于直接内存存储的ByteBuf,内部调用的 UnpooledByteBufAllocator（非池话的ByteBufAllocator）
        ByteBuf directBuffer1 = Unpooled.directBuffer();
        //默认初始容量和最大容量的  池化过的 基于直接内存存储的ByteBuf
        ByteBuf directBuffer2 = new PooledByteBufAllocator().directBuffer();
        if(!directBuffer1.hasArray()){
            directBuffer1.writeLong(10);
            System.out.printf("%s 非支撑数组 ; ","directBuffer1");
            int length = directBuffer1.readableBytes(); //获取可读字符数
            byte[] array = new byte[length]; //分配一个具有该长度的新数组
            directBuffer1.getBytes(directBuffer1.readerIndex(),array); //将字节复制到该数组
            System.out.println("从直接缓冲区中读取数据"+ array.toString()); // directBuffer1.readLong()也可以获取到
        }
        if(!directBuffer2.hasArray()){
            System.out.printf("%s 非支撑数组 ;%n ","directBuffer2");
        }
    }

    //复合缓冲区，可以存储多个ByteBuf实例，将多个缓冲区虚拟的表示为单个缓冲区
    public static void compositeBuffer(){
        //默认最大容量的  池化的 复合缓冲区
        CompositeByteBuf compBuf = new PooledByteBufAllocator().compositeBuffer();
        //默认最大容量的  未池化的 复合缓冲区
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf headBuf = Unpooled.directBuffer();
        ByteBuf bodyBuf = Unpooled.directBuffer();
        headBuf.writeLong(10);
        bodyBuf.writeLong(100);

        messageBuf.addComponents(headBuf,bodyBuf); //将ByteBuf实例追加到CompositeByteBuf中
        messageBuf.removeComponent(0); //删除位于索引位置为0的ByteBuf
        for (ByteBuf buf : messageBuf){
            System.out.println(buf.toString());
        }

        //访问CompositeByteBuf中的数据，类似于访问直接缓冲区的模式，都是先把数据复制到一个新的数组中
        int length = messageBuf.readableBytes();
        byte[] array = new byte[length];
        messageBuf.getBytes(messageBuf.readerIndex(),array);
        System.out.println(new String(array));
    }

    /**
     * 读写字符串
     */
    public static void readWriteString(){
        Charset utf8 = Charset.forName("UTF-8");
        //初始化写入数据
        ByteBuf buf = Unpooled.copiedBuffer("Netty is Action rocks!",utf8);//返回了一个复制了给定数据的ByteBuf,并对其进行编码

        System.out.println("输出整个ByteBuf内的数据："+buf.toString(utf8));
        System.out.println("输出索引0对应的数据："+(char) buf.getByte(0));


    }
}
