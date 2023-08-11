package com.future.flink.Windwos;

import com.future.flink.Windwos.watermark.MyWatermarkTest;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.sql.Timestamp;
import java.time.Duration;

/**
 * @Description 窗口计算函数——ReduceFuntion ，基于数字
 * @Date 2023/8/9 17:19
 */
public class ReduceFuntionWindowsOfNumberTest {

    public static void main(String[] args) throws Exception {
        // 设置运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 可选：强制设置并行度
        env.setParallelism(1);

        // 接入source数据
        SingleOutputStreamOperator<Tuple3<String,Integer,Long>> eventStream = env.addSource(new reduceFunctionNumberSource());

        // 注册watermark：内置的乱序流生成器，延迟时间设置为5s
        eventStream = eventStream.assignTimestampsAndWatermarks(new MyWatermarkTest());


        // 对数据流进行分组
        KeyedStream<Tuple3<String,Integer,Long>,String> keySteam = eventStream.keyBy(x -> x.f0);

        // 设置窗口——滑动式（窗口大小5秒，滑动间隔为5秒）
        WindowedStream<Tuple3<String,Integer,Long>, String, TimeWindow> windowStream = keySteam.window(SlidingEventTimeWindows.of(Time.seconds(5),Time.seconds(5)));

        // 执行函数计算
        SingleOutputStreamOperator<Tuple3<String,Integer,Long>> outputStream =  windowStream.reduce(new ReduceFunction<Tuple3<String,Integer,Long>>() {
            @Override
            public Tuple3<String,Integer,Long> reduce(Tuple3<String,Integer,Long> t0,Tuple3<String,Integer,Long> t1) throws Exception {
                return new Tuple3<>(t0.f0,t0.f1+t1.f1,Math.max(t0.f2, t1.f2));
            }
        });

        //sink操作：将结果输出到控制台
            outputStream.print();

        env.execute();
    }

    public static class reduceFunctionNumberSource implements SourceFunction<Tuple3<String,Integer,Long>> {
        @Override
        public void run(SourceContext<Tuple3<String,Integer,Long>> sourceContext) throws Exception {
            Long currTime = System.currentTimeMillis();
            System.out.println(new Timestamp(currTime));
            sourceContext.collect(new Tuple3("c",1,currTime));

            Thread.sleep(4000L);
            // 模拟：4秒后发生的事件
            Long time1 = currTime+4000L;
            System.out.println(new Timestamp(time1));
            sourceContext.collect(new Tuple3("b",1,time1));
            sourceContext.collect(new Tuple3("a",1,time1));

            Thread.sleep(1000L);
            // 模拟：1秒后发生的事件
            Long time2 = time1+1000L;
            System.out.println(new Timestamp(time2));
            sourceContext.collect(new Tuple3("a",1,time1));

            Thread.sleep(5000L);
            // 模拟：6秒后发生的事件,等5秒的时候有时会把c4累计到c1上，为什么，我的窗口是5秒且延时1秒？？？
            Long time3 = time2+5000L;
            System.out.println(new Timestamp(time3));
            sourceContext.collect(new Tuple3("b",1,time3));
            sourceContext.collect(new Tuple3("a",1,time3));
            sourceContext.collect(new Tuple3("b",1,time3));
            sourceContext.collect(new Tuple3("a",1,time3));
            sourceContext.collect(new Tuple3("c",4,currTime));
            sourceContext.collect(new Tuple3("c",2,time3));
            // 測試还是不行，不能将数据按窗口完美的分割开，需要自定义一个WaterMark试一试
        }

        @Override
        public void cancel() {

        }
    }

}
