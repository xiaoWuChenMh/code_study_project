package com.future.flink.DataSource;

import com.future.flink.watermark.Event;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.sql.Timestamp;

/**
 * @Description
 * @Author v_hhuima
 * @Date 2023/8/9 16:30
 */
public class ClickSource implements SourceFunction<Event> {

    @Override
    public void run(SourceContext<Event> sourceContext) throws Exception {
        System.out.println("开始生成数据：" + new Timestamp(System.currentTimeMillis()));
        sourceContext.collect(new Event("Mary", "./home", System.currentTimeMillis()));
        sourceContext.collect(new Event("Alice", "./cart", System.currentTimeMillis()));
        Thread.sleep(1200L);
        sourceContext.collect(new Event("Alice", "./cart", System.currentTimeMillis()+1200L));
        Thread.sleep(100L);
        sourceContext.collect(new Event("Mary", "./home", System.currentTimeMillis()+100L));
        Thread.sleep(1200L);
        sourceContext.collect(new Event("Alice", "./cart", System.currentTimeMillis()+1200L));
        sourceContext.collect(new Event("Alice", "./zzzzd", System.currentTimeMillis()+1200L));
        sourceContext.collect(new Event("Mary", "./lild", System.currentTimeMillis()+1200L));
        Thread.sleep(4200L);
        sourceContext.collect(new Event("Alice", "./cmd", System.currentTimeMillis()+4200L));
        sourceContext.collect(new Event("Alice", "./cbd", System.currentTimeMillis()+4200L));
        System.out.println("结束生成数据：" + new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void cancel() {

    }
}
