package com.future.netty.channel;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/6/17 17:35
 */
public class ChannelOutBoundTest extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Channel channel = ctx.channel();
        ChannelFuture future =channel.write(Unpooled.copiedBuffer("one sdf", CharsetUtil.UTF_8));

        System.out.println("出站的一般的异常处理，使用方法的参数2,为其添加监听器");
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(!channelFuture.isSuccess()){
                    channelFuture.cause().printStackTrace();//打印异常堆栈信息
                    channelFuture.channel().close();//关闭channel
                }
            }
        });


        System.out.println("出站的细致处理，使用出站的返回Future,为其添加监听器");
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(!channelFuture.isSuccess()){
                    channelFuture.cause().printStackTrace();//打印异常堆栈信息
                    channelFuture.channel().close();//关闭channel
                }
            }
        });
    }
}
