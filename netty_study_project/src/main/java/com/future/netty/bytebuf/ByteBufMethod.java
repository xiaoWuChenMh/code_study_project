package com.future.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * ByteBuf的各种操作方法
 *   readerIndex ： 读索引，字节被读取时，该属性递增
 *   writerIndex ： 写索引，字节被写入时，该属性递增
 *   CharsetUtil :  编码  Unpooled.copiedBuffer("Netty",CharsetUtil.UTF_8);
 *   读/写字符串：readLong/writeLong : 读/写8字节 ；  readInt/writeInt:读/写4字节
 */
public class ByteBufMethod {

    public static void main(String[] args) throws UnsupportedEncodingException {
        containerMethod();
        allWreiteAndRead();
        indexManage();
        fundMethod();
        copyMethod();
        readWriteMethod();
        ohterMethod();
    }

    /**
     * 容器相关方法
     */
    public static void containerMethod(){
        System.out.println("========================= 容器相关方法 =========================");
        ByteBuf buf = Unpooled.buffer();
        System.out.println("获取ByteBuf的可读字节数:"+buf.readableBytes());
        System.out.println("获取ByteBuf的可写字节数:"+buf.writableBytes());
        System.out.println("获取读索引的所在位置:"+buf.readerIndex());
        System.out.println("获取写索引所在位置:"+buf.writerIndex());
        System.out.println("获取ByteBuf的容量:"+buf.capacity()); //获取ByteBuf的容量，256是默认的初始容量，索引从0开始


        buf.discardReadBytes();//丢弃掉已经被读过的字节并回收空间，不建议频繁调用因为会有内存复制（可读字节必须移动到缓冲的开始位置）
        buf.writeLong(10).writeLong(99);
        System.out.println("写入16位字节的后的写索引在位置："+buf.writerIndex()+",再看是否可读："+buf.isReadable());
        System.out.println("读取8位字节，读取的内容："+buf.readLong()+",然后查看读索引所在位置："+buf.readerIndex());
        System.out.println("在读取最后的8位字节，读取的内容："+buf.readLong()+",再看是否可读："+buf.isReadable());
    }

    /**
     * 写入和读取所有的数据
     */
    public static void allWreiteAndRead(){
        System.out.println("========================= 写入和读取所有的数据 =========================");
        ByteBuf buf = Unpooled.buffer();
        Random ran = new Random(80);
        while (buf.writableBytes()>=4){
            int value = ran.nextInt(99);
            buf.writeInt(value);
            System.out.println("空闲空间："+buf.writableBytes()+"填入的值："+value);
        }
        System.out.println("获取ByteBuf的可读字节数:"+buf.readableBytes());
        System.out.println("获取写索引所在位置:"+buf.writerIndex());
        while (buf.isReadable()){ //是否可读
            System.out.println(buf.readInt());
        }
    }

    /**
     * 索引管理的方法
     */
    public static void indexManage(){
        System.out.println("========================= 索引管理方法 =========================");
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(10).writeInt(10);
        buf.readInt();
        System.out.printf("初始值：writeIndex的值 = %d ; readIndex的值  =%d; %n",buf.writerIndex(),buf.readerIndex());

        buf.resetReaderIndex(); //重置操作
        buf.resetWriterIndex(); //重置操作
        System.out.printf("重置后：writeIndex的值 = %d ; readIndex的值  =%d; %n",buf.writerIndex(),buf.readerIndex());

        buf.markReaderIndex(); //标记操作，不知道有什么用
        buf.markWriterIndex(); //标记操作，不知道有什么用
        System.out.printf("标记后：writeIndex的值 = %d ; readIndex的值  =%d; %n",buf.writerIndex(),buf.readerIndex());

        buf.writerIndex(100); //给定参数视为索引移动到给定值，不给参数视为获取当前索引值
        buf.readerIndex(99);  //给定参数视为索引移动到给定值，不给参数视为获取当前索引值
        System.out.printf("移动后：writeIndex的值 = %d ; readIndex的值  =%d; %n",buf.writerIndex(),buf.readerIndex());

        buf.clear(); //将writerIndex 和 readerIndex 都设置为0，不复制移动内存，因此比discardReadBytes要轻量
        System.out.printf("清空后：writeIndex的值 = %d ; readIndex的值  =%d; %n",buf.writerIndex(),buf.readerIndex());


    }

    /**
     * 查找方法
     */
    public static void fundMethod(){
        System.out.println("========================= 查找方法 =========================");
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(10).writeInt(2).writeInt(5).writeByte(13);
        int index = buf.forEachByte(ByteProcessor.FIND_CR); //acii码表中 13 是回车键
        System.out.println("查找回车符（\\r）的索引位置："+index);
    }

    /**
     * 复制操作，分两类(复制的内容只能是未读取的)：
     *  1、派生缓冲区，新的Bytebuf具有独立的读写和标记索引，但是内部存储是和源Bytebuf共享的；
     *  2、复制缓冲区，是完全的根据现有的Bytebuf复制一个真实的副本。
     */
    public static void copyMethod(){
        System.out.println("========================= 复制操作 =========================");
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty is Action rocks!",utf8);
        ByteBuf sliced = buf.slice(0,15); //派生缓冲区==>复制指定范围
        ByteBuf copy = buf.copy(0,15);
        System.out.println("派生缓冲区==>全部复制过来:"+buf.duplicate().toString(utf8));
        System.out.println("派生缓冲区==>剪切指定范围:"+buf.readSlice(10).toString(utf8));
        buf.readerIndex(0); //剪切指定范围后，原Bytebuf的readerIndex被移动了，这里给其还原
        System.out.println("派生缓冲区==>全部复制过来:"+buf.slice().toString(utf8));
        System.out.println("派生缓冲区==>复制指定范围:"+sliced.toString(CharsetUtil.UTF_8));

        System.out.println("真实复制==>全部复制过来:"+buf.copy().toString(CharsetUtil.UTF_8));
        System.out.println("真实复制==>复制指定范围:"+copy.toString(CharsetUtil.UTF_8));

        sliced.setByte(0,(byte)'J'); //更新索引0处的字节
        System.out.println("相等证明'派生缓冲区'和'原缓冲区'内部存储是共享的:" + (buf.getByte(0) == sliced.getByte(0)));

        copy.setByte(1,(byte)'J'); //更新索引1处的字节
        System.out.println("不相等证明'复制'和'原缓冲区'内部存储是不共享的:" + (buf.getByte(1) != copy.getByte(1)));
    }

    /**
     * 读写操作
     *    字符串占用2个字节
     *    Boolean占用2个字节
     *    Int 占用4个字节
     *    Long 占用8个字节
     */
    public static void readWriteMethod() throws UnsupportedEncodingException {
        System.out.println("========================= 读写操作 =========================");
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty is Action rocks!",utf8);
        System.out.println("读索引;"+buf.readerIndex()+",写索引:"+buf.writerIndex());

        System.out.println("------------ 不改变索引的的读(get)写(set)操作 ----------");
        System.out.println("返回给定索引处的字节："+(char) buf.getByte(0));
        buf.setBoolean(1,true);
        System.out.println("返回给定索引处的Boolean值："+ buf.getBoolean(1));
        buf.setInt(2,2);
        System.out.println("返回给定索引处的Int值："+ buf.getInt(2));
        System.out.println("ByteBuf内全部的值:"+buf.toString(utf8));
        System.out.println("读索引;"+buf.readerIndex()+",写索引:"+buf.writerIndex());

        System.out.println("------------ 改变索引的的读(read)写(write)操作 ----------");
        buf.readerIndex(2); //readerIndex移动到 int值 的开始索引位置
        System.out.println("返回一个int值："+ buf.readInt());
        System.out.println("读索引;"+buf.readerIndex()+",写索引:"+buf.writerIndex());
        System.out.println("读写除了byte外，也是有可以直接读写各种类型的方法");

        System.out.println("------------ 读写字符串----------");
        ByteBuf byteBuf = Unpooled.buffer();
        //写入字符串：字符串转为utf-8的字节(按utf-8解码)，向ByteBuf中写入字符串的字节长度，在写入字符串的全部字节。
        byte[] writeBytes = "star [测试字符串] end".getBytes("UTF-8");
        byteBuf.writeInt(writeBytes.length);
        byteBuf.writeBytes(writeBytes);
        //读取字符串：从ByteBuf中读取字符串的字节长度，按长度读取字节并编码位字符串
        int length = byteBuf.readInt();
        byte[] readBytes = new byte[length];
        byteBuf.readBytes(readBytes);
        System.out.println("从ByteBuf中读取的字符串："+new String(readBytes, "UTF-8"));;
    }

    /*
    * @Description: 其他方法
    * @Author: xiaowuchen
    * @Date: 2020/6/15 11:55
    * @Param: []
    * @return: void
    */
    public static void ohterMethod(){
        System.out.println("========================= 其他操作 =========================");
        ByteBuf buf = Unpooled.copiedBuffer("Netty \r is Action rocks! \r\n",CharsetUtil.UTF_8);
        System.out.println("至少有一个字节可供读取，就返回True，可以用于验证是否可读:"+buf.isReadable());
        System.out.println("至少有一个字节可被写入，就返回True，可以用于验证是否可写:"+buf.isWritable());
        System.out.println("返回可以被读取的字节数:"+buf.readableBytes());
        System.out.println("返回可以被写入的字节数:"+buf.writableBytes());
        System.out.println("返回byteBuf可容纳的字节数:"+buf.capacity());
        System.out.println("返回byteBuf可容纳的最大字节数:"+buf.maxCapacity());
        System.out.println("如果byteBuf是由数组支持，返回true:"+buf.hasArray());
        try {
            System.out.println("如果byteBuf是由数组支持，返回该数组，否则抛出异常:"+buf.array());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("池化的ByteBuf的引用计数:"+buf.refCnt());

        System.out.println("--------------------- 查找操作(查到第一位就返回其索引,未查到返回-1) ----------------------------");
        System.out.println("查找字母r坐在的索引："+buf.indexOf(buf.readerIndex(), buf.writerIndex(), "t".getBytes()[0]));
        System.out.println("查找回车符所在的索引："+buf.forEachByte(ByteProcessor.FIND_CR));
        buf.slice();
    }

}
