package com.future.flink.watermark;

import com.future.flink.DataSource.CustomEventSource;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Description 自定义 周期性水位线生成器（Periodic Generator）
 * @Author v_hhuima
 * @Date 2023/8/9 15:53
 */
public class CustomWatermarkTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.addSource(new CustomEventSource())
                .assignTimestampsAndWatermarks(new CustomWatermarkStrategy())
                .print();

        env.execute();
    }

    /*
    * @Description: 1、自定义waterMark生成策略：时间戳分配器 and  watermark生成器
    * @Param:
    * @return:
    */
    public static class CustomWatermarkStrategy implements WatermarkStrategy<Event> {

        // 时间戳分配器:负责从事件流中提取时间戳
        @Override
        public TimestampAssigner<Event> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
            return new SerializableTimestampAssigner<Event>() {
                @Override
                public long extractTimestamp(Event element, long recordTimestamp) {
                    return element.timestamp; // 告诉程序数据源里的时间戳是哪一个字段
                }
            };
        }

        // watermark生成器:负责按照定义去生成watermark
        @Override
        public WatermarkGenerator<Event> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
            return new CustomPeriodicGenerator();
        }
    }

    /*
    * @Description: 自定义的watermark生成器
    * @Param:
    * @return:
    */
    public static class CustomPeriodicGenerator implements WatermarkGenerator<Event> {
        private Long delayTime = 5000L; // 延迟时间
        private Long maxTs = Long.MIN_VALUE + delayTime + 1L; // 观察到的最大时间戳

        // 每个事件（数据）到来都会调用的方法,获取最大时间戳
        @Override
        public void onEvent(Event event, long eventTimestamp, WatermarkOutput output) {
            // 每来一条数据就调用一次
            maxTs = Math.max(event.timestamp, maxTs); // 更新最大时间戳
        }

        // 周期性调用的方法，可以由用户控制什么时候需要生成一个新的watemark并发送出去，默认为200ms。
        @Override
        public void onPeriodicEmit(WatermarkOutput output) {
            // 发射水位线，默认200ms调用一次
            output.emitWatermark(new Watermark(maxTs - delayTime - 1L));
        }
    }


}
