package com.future.flink.Windwos.watermark;

import org.apache.flink.api.common.eventtime.Watermark;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkOutput;
import org.apache.flink.api.java.tuple.Tuple3;

import java.sql.Timestamp;

/**
 * @Description
 * @Author hma
 * @Date 2023/8/11 19:20
 */
public class MysqlPeriodicGenerator implements WatermarkGenerator<Tuple3<String,Integer,Long>> {
    private Long delayTime = 1000L; // 延迟时间
    private Long maxTs = Long.MIN_VALUE + delayTime + 1L; // 观察到的最大时间戳

    // 每个事件（数据）到来都会调用的方法,获取最大时间戳
    @Override
    public void onEvent(Tuple3<String,Integer,Long> event, long eventTimestamp, WatermarkOutput output) {
        // 每来一条数据就调用一次
        maxTs = Math.max(event.f2, maxTs); // 更新最大时间戳
        System.out.println("name:"+event.f0+";num:"+event.f1+";maxts:"+new Timestamp(event.f2));

    }

    // 周期性调用的方法，可以由用户控制什么时候需要生成一个新的watemark并发送出去，默认为200ms。
    @Override
    public void onPeriodicEmit(WatermarkOutput output) {
        // 发射水位线，默认200ms调用一次
        output.emitWatermark(new Watermark(maxTs - delayTime - 1L));
    }
}