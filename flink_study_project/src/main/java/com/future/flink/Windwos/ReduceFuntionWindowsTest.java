package com.future.flink.Windwos;

import com.future.flink.DataSource.CustomEventSource;
import com.future.flink.DataSource.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.time.Duration;

/**
 * @Description 窗口计算函数——ReduceFuntion
 * @Date 2023/8/9 16:27
 */
public class ReduceFuntionWindowsTest {


    public static void main(String[] args) throws Exception {
        // 设置运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 可选：强制设置并行度
        env.setParallelism(1);

        // 接入source数据
        SingleOutputStreamOperator<Event> eventStream = env.addSource(new CustomEventSource());

        // 注册watermark：内置的乱序流生成器，延迟时间设置为5s
        eventStream = eventStream.assignTimestampsAndWatermarks(
                        WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                                .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                                    @Override
                                    public long extractTimestamp(Event element, long recordTimestamp) {
                                        return element.timestamp;
                                    }
                                })
                );

        // 对数据流进行分组
        KeyedStream<Event,String> keySteam = eventStream.keyBy(data -> data.url);

        // 设置窗口——滑动式（窗口大小8分钟，滑动间隔为4分钟）
        WindowedStream<Event, String, TimeWindow> windowStream = keySteam.window(SlidingEventTimeWindows.of(Time.minutes(8),Time.minutes(4)));

        // 执行函数计算
        SingleOutputStreamOperator<Event> outputStream =  windowStream.reduce(new ReduceFunction<Event>() {
             @Override
             public Event reduce(Event event1, Event event2) throws Exception {
                 // 在这里定义如何将两个事件进行合并
                 // 例如，可以根据需要对字段进行聚合或比较
                 // 返回合并后的事件
                 return new Event(event1.user, event1.url, Math.max(event1.timestamp, event2.timestamp));
             }
        });

        //sink操作：将结果输出到控制台
        outputStream.print();

        env.execute();
    }

}
