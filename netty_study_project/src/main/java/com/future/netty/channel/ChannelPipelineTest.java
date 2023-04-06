package com.future.netty.channel;

import io.netty.channel.*;

/**
 * @Description
 * @Author xiaowuchen
 * @Date 2020/6/18 10:38
 */
public class ChannelPipelineTest extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel channel) throws Exception {

        //获取pipeline
        ChannelPipeline pipeline = channel.pipeline();
        ChannelHandler one = pipeline.get("类型和名称");
        pipeline.context(one); //参数handler的名称和引用对象
        pipeline.names();

    }
}
