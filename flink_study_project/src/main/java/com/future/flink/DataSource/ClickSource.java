package com.future.flink.DataSource;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.sql.Timestamp;

/**
 * @Description
 * @Author hma
 * @Date 2023/8/9 16:30
 */
public class ClickSource implements SourceFunction<Event> {

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        System.out.println("开始生成数据：" + new Timestamp(System.currentTimeMillis()));
        sourceContext.collect(new Event("Mary", "./home", System.currentTimeMillis()));
        sourceContext.collect(new Event("Alice", "./cart", System.currentTimeMillis()));
        Thread.sleep(1200L);
        sourceContext.collect(new Event("Alice", "./cart", System.currentTimeMillis()));
        Thread.sleep(100L);
        sourceContext.collect(new Event("Mary", "./home", System.currentTimeMillis()));
        Thread.sleep(1200L);
        sourceContext.collect(new Event("Alice", "./cart", System.currentTimeMillis()));
        sourceContext.collect(new Event("Alice", "./zzzzd", System.currentTimeMillis()));
        sourceContext.collect(new Event("Mary", "./lild", System.currentTimeMillis()));
        Thread.sleep(4200L);
        sourceContext.collect(new Event("Alice", "./cmd", System.currentTimeMillis()));
        sourceContext.collect(new Event("Alice", "./cbd", System.currentTimeMillis()));
        Thread.sleep(5000L);
        sourceContext.collect(new Event("Alice", "./cmd", System.currentTimeMillis()));
        sourceContext.collect(new Event("Alice", "./cbd", System.currentTimeMillis()));
        System.out.println("结束生成数据：" + new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void cancel() {

    }
}
