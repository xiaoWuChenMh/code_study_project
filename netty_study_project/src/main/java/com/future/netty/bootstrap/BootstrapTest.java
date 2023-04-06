package com.future.netty.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description 客户端引导程序
 * @Author xiaowuchen
 * @Date 2020/6/19 16:15
 */
public class BootstrapTest {

    public static void main(String[] args) {
        EventLoopGroup group  = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();       //建立客户端引导程序实例
        bootstrap.group(group)                       //设置EventLoopGroup
                .channel(NioSocketChannel.class)     //指定要使用的Channel实现
                .handler(new SimpleChannelInboundHandler<ByteBuf>() { //设置用于处理Channel事件和数据的Inboun（ChannelInboundHandler）
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        System.out.println("消费传入的数据");
                    }
                });
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost",9090)); //连接到远程主机
        future.addListener(new ChannelFutureListener() {   //添加监控
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("连接已建立");
                }else{
                    System.out.println("尝试连接建立失败");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
