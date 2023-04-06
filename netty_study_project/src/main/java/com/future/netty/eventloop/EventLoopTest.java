package com.future.netty.eventloop;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoop;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/6/18 17:04
 */
public class EventLoopTest {

    public static void main(String[] args) {

    }


    private class testChannelHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
            Channel channel = ctx.channel();

            System.out.println("定时执行任务");
            ScheduledFuture<?> future1 = channel.eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("60秒后执行任务");
                }
            },60, TimeUnit.SECONDS);

            System.out.println("周期性的执行任务");
            ScheduledFuture<?> future2 = channel.eventLoop().scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println("调度任务在60秒后执行，并且以后没间隔50秒运行一次");
                }
            },60,50,TimeUnit.SECONDS);
            future2.cancel(false); //取消该任务，防止它再次运行；
        }
    }


}
