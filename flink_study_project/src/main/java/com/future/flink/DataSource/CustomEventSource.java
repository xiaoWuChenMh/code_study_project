package com.future.flink.DataSource;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Description
 * @Author hma
 * @Date 2023/8/9 16:28
 */
public class CustomEventSource implements SourceFunction<Event>{

    @Override
    public void run(SourceFunction.SourceContext<Event> sourceContext) throws Exception {
        // 向flink的source发送一条测试数据
        sourceContext.collect(new Event("Mary", "./home", 1000L));
        Thread.sleep(10000L);
        // 发出10秒后的数据
        sourceContext.collect(new Event("Mary", "./home", 11000L));
        Thread.sleep(1000L);
        //发出10秒+1ms后的数据
        sourceContext.collect(new Event("Alice", "./cart", 11001L));
        Thread.sleep(5000L);
    }

    @Override
    public void cancel() {

    }
}
