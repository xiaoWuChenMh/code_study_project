package com.future.netty.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/6/16 17:51
 */
public class ChannelInBoundTest extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //丢弃已接收的消息 [ReferenceCounted.release()](ByteBuf实现了ReferenceCounted接口)
        //有一个实现是把null作为新值
        //SimpleChannelInboundHandler将会自动释放资源，不需要显示的声明
        ReferenceCountUtil.release(msg);

    }

    /*
    * @Description: 入站的异常处理
    */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(); //打印异常堆栈信息
        ctx.close(); //关闭channel
    }
}
