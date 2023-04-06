package com.future.netty.bootstrap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/6/19 18:27
 */
public class ServerBootstrapTest {

    public static void main(String[] args) {

        //boss线程，用来处理所监听端口的Socket请求，并为该请求新建一个子Channel（一个打开的socket对应一个channel）,然后转给worker线程；
        EventLoopGroup bossGroup  = new NioEventLoopGroup();
        //worker线程，用来处理客户端的请求信息
        EventLoopGroup workerGroup = bossGroup;
        ServerBootstrap bootstrap = new ServerBootstrap();  //建立服务端引导程序实例
        bootstrap.group(bossGroup,workerGroup)              //设置EventLoopGroup
                .channel(NioServerSocketChannel.class)      //指定要使用的Channel实现
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() { //设置用于处理子Channel事件和数据的Inboun
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                        System.out.println("处理入站信息");
                    }
                })
                .childHandler(new ChannelInitializer<SocketChannel>() { //添加多个Channelhandler

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() { //设置用于处理子Channel事件和数据的Inboun
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                System.out.println("---处理入站信息2");
                            }
                        });
                        pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() { //设置用于处理子Channel事件和数据的Inboun
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                System.out.println("---处理入站信息3");
                            }
                        });
                    }
                } );
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(9090));//设置要监听的端点
        future.addListener(new ChannelFutureListener() {  //添加监控
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("服务端建立成功");
                } else {
                    System.out.println("尝试建立连接失败");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
