package com.future.flink.Windwos.watermark;

import com.future.flink.DataSource.Event;
import com.future.flink.watermark.CustomWatermarkTest;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * @Description
 * @Author v_hhuima
 * @Date 2023/8/11 19:17
 */
public class MyWatermarkTest implements WatermarkStrategy<Tuple3<String,Integer,Long>>{

    // 时间戳分配器:负责从事件流中提取时间戳
    @Override
    public TimestampAssigner<Tuple3<String,Integer,Long>> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
        return new SerializableTimestampAssigner<Tuple3<String,Integer,Long>>() {
            @Override
            public long extractTimestamp(Tuple3<String,Integer,Long> element, long recordTimestamp) {
                return element.f2; // 告诉程序数据源里的时间戳是哪一个字段
            }
        };
    }

    // watermark生成器:负责按照定义去生成watermark
    @Override
    public WatermarkGenerator<Tuple3<String,Integer,Long>> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
        return new MysqlPeriodicGenerator();
    }

}
