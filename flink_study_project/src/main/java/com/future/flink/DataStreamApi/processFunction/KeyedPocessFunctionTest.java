package com.future.flink.DataStreamApi.processFunction;

import com.future.flink.DataSource.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;

/**
 * @Description   KeyedPocessFunction测试程序,在代码中通过new一个KeyedProcessFunction实例，并重新方法实现。
 * @Author hma
 * @Date 2023/9/7 12:30
 */
public class KeyedPocessFunctionTest {

    public static void main(String[] args) throws Exception {
        // 获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 配置分区数
        env.setParallelism(1);
        // 配置数据源
        DataStreamSource<Event> sourceData = env.addSource(new KeyedPocessCustomSource());
        // 配置Watermarks
        SingleOutputStreamOperator<Event> stream = sourceData
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forMonotonousTimestamps()
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>(){
                            @Override
                            public long extractTimestamp(Event event, long l) {
                                return event.timestamp;
                            }
                        }));
        // 生成keyStream,其中 data -> true 会所有数据分到同一分区，因此当前的流中key的类型是boolean
        KeyedStream<Event,Boolean> keyStreamData = stream.keyBy(data -> true);

        // 使用KeyedPocessFunction函数处理流入的每条数据，并执行注册定时器。
        keyStreamData.process(new KeyedProcessFunction<Boolean, Event, Object>() {
                    // 设置定时器触发后执行的逻辑
                    @Override
                    public void onTimer(long timestamp, OnTimerContext ctx, Collector<Object> out) throws Exception {
                        out.collect("定时器被触发了，触发时间：" + timestamp);
                    }

                    // 每条数据进来触发的逻辑，并配置一个定时器。
                    @Override
                    public void processElement(Event event, Context ctx, Collector<Object> out)
                            throws Exception {
                        out.collect("数据已到达，时间戳为：" + ctx.timestamp());
                        // 立即获取当的watermark
                        out.collect(" 数据已到达，水位线为： " + ctx.timerService().currentWatermark() + "\n -------分割线-------");
                        // 注册一个 10 秒后的定时器
                        ctx.timerService().registerEventTimeTimer(ctx.timestamp() + 10 * 1000L);
                    }
                })
                .print();
        //执行程序
        env.execute();
    }

    /**
     * 自定义测试数数据源
     */
    public static class KeyedPocessCustomSource implements SourceFunction<Event> {

        @Override
        public void run(SourceContext<Event> sourceContext) throws Exception {
            // 向flink的source发送一条测试数据
            sourceContext.collect(new Event("Mary", "./home1", 1000L));
            Thread.sleep(10000L);
            // 发出10秒后的数据
            sourceContext.collect(new Event("Mary", "./home1", 11000L));
            Thread.sleep(1000L);
            //发出10秒+1ms后的数据
            sourceContext.collect(new Event("Alice", "./cart1", 11001L));
            Thread.sleep(5000L);
        }

        @Override
        public void cancel() {

        }
    }
}
/*
* ======================= 输出数据说明 ========================================
* ------------------------------------------------- 输出数据 start
* 数据已到达，时间戳为：1000
* 数据已到达，水位线为： -9223372036854775808
* -------分割线-------
* 数据已到达，时间戳为：11000
* 数据已到达，水位线为： 999
* -------分割线-------
* 数据已到达，时间戳为：11001
* 数据已到达，水位线为： 10999
* -------分割线-------
* 定时器被触发了，触发时间：11000
* 定时器被触发了，触发时间：21000
* 定时器被触发了，触发时间：21001
* ------------------------------------------------- 输出数据end
* 当第一条数据到来，时间戳为 1000，可水位线的生成是周期性的（默认 200ms 一次），不会立即发生改变，所以我们输出当前的水位线依然是最小值 Long.MIN_VALUE （看 forMonotonousTimestamps 源码）；
* 随后只要到了水位线生成的时间点（200ms 到了），就会依据当前的最大时间戳 1000 来生成水位线了。这里我们没有设置水位线延迟，默认需要减去 1 毫秒（看源码），所以水位线推进到了 999。
* 睡眠5秒后当第二条时间戳为 11000 的数据到来之后，水位线同样没有立即改变，仍然是 999，就好像总是“滞后”数据一样。
* 随后只要到了水位线生成的时间点（200ms 到了），生成新的水位线：10999；
* 睡眠5秒后当第三条时间戳为 11001 的数据到来之后，水位线同样没有立即改变，仍然是10999。
* 随后只要到了水位线生成的时间点（200ms 到了），生成新的水位线：11000；
* 紧接着触发第一个定时器（设定的定时器时间为 1000 + 10 * 1000 = 11000）；
* 睡眠5秒程序结束，此时 Flink 会自动将水位线推进到长整型的最大值（Long.MAX_VALUE）。于是所有尚未触发的定时器这时就统一触发了，我们就在控制台看到了后两个定时器的触发信息
*
*/
