package com.future.flink.DataStreamApi.State.keyedstate;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;

public class MapFunctionTest extends RichMapFunction<Tuple2<String, String>, Long> {

    private ValueState<Long> totalLengthByKey;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        // 第一步：定义定义状态描述符
        ValueStateDescriptor<Long> stateDescriptor =
                new ValueStateDescriptor<Long>("sum of length", LongSerializer.INSTANCE);
        // 第二步：调用getRuntimeContext().getState去 注册状态
        totalLengthByKey = getRuntimeContext().getState(stateDescriptor);
    }

    /**
     * 第三步：读写状态
     * @param value
     * @return
     * @throws Exception
     */
    @Override
    public Long map(Tuple2<String, String> value) throws Exception {
        // 3.1 获取当前状态
        Long value_his = totalLengthByKey.value();
        if (null == value_his){
            value_his = 0l ;
        }
        Long newTotalLength = value_his + value.f1.length();
        // 3.2 更新当前状态
        totalLengthByKey.update(newTotalLength);
        // 3.3 将结果返回给下游
        return newTotalLength;
    }

}
