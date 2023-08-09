package com.future.flink.watermark;

import com.future.flink.DataSource.CustomEventSource;
import com.future.flink.DataSource.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * 基于EventTime的定时器测试,也包含了有序流watermark生成器的测试
 */
public class EventTimeTimerTest {

    // Event 是自定义的一个数据源
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 接入source数据
        SingleOutputStreamOperator<Event> stream = env.addSource(new CustomEventSource())
                .assignTimestampsAndWatermarks(
                        // 注册flink内置的顺序流的watermark生成器
                        WatermarkStrategy.<Event>forMonotonousTimestamps()
                           .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                               @Override
                               public long extractTimestamp(Event element, long recordTimestamp) {
                                   return element.timestamp;
                               }
                           })
                );
        //基于keyedStream 定义事件事件定时器
        stream.keyBy(data -> true)  // data -> true 所有数据分到同一分区
                .process(new KeyedProcessFunction<Boolean, Event, Object>() {
                    @Override
                    public void onTimer(long timestamp, OnTimerContext ctx, Collector<Object> out) throws Exception {
;                       out.collect("定时器触发，触发时间：" + timestamp);
                    }

                    @Override
                    public void processElement(Event event, Context ctx, Collector<Object> out)
                            throws Exception {
                        out.collect("数据到达，时间戳为：" + ctx.timestamp());
                        // 立即获取当的watermark
                        out.collect(" 数据到达，水位线为： " + ctx.timerService().currentWatermark() + "\n -------分割线-------");
                        // 注册一个 10 秒后的定时器
                        ctx.timerService().registerEventTimeTimer(ctx.timestamp() + 10 * 1000L);
                    }
                })
                .print();
        env.execute();
    }


}
