package com.future.flink.watermark;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;

public class EventTimeTimerTest {

    // Event 是自定义的一个数据源
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 接入source数据，并注册flink内置的顺序流的watermark生成器
        SingleOutputStreamOperator<Event> stream = env.addSource(new CustomSource())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forMonotonousTimestamps()
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));
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

    /**
     * 自定义测试数数据源
     */
    public static class CustomSource implements SourceFunction<Event> {

        @Override
        public void run(SourceContext<Event> sourceContext) throws Exception {
            // 向flink的source发送一条测试数据
            sourceContext.collect(new Event("Mary", "./home", 1000L));
            Thread.sleep(5000L);
            // 发出10秒后的数据
            sourceContext.collect(new Event("Mary", "./home", 11000L));
            Thread.sleep(5000L);
            //发出10秒+1ms后的数据
            sourceContext.collect(new Event("Alice", "./cart", 11001L));
            Thread.sleep(5000L);
        }

        @Override
        public void cancel() {

        }
    }


}
