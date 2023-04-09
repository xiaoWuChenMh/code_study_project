package com.future.flink.DataStreamApi.State.operatorstate;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import java.util.ArrayList;
import java.util.List;

public class BufferingSinkFunction implements SinkFunction<Tuple2<String, Integer>>, CheckpointedFunction {

    private final int threshold;

    // 声明一个OperatorState对象
    private transient ListState<Tuple2<String, Integer>> checkpointedState;

    // 声明一个缓存对象
    private List<Tuple2<String, Integer>> bufferedElements;

    public BufferingSinkFunction(int threshold) {
        this.threshold = threshold;
        this.bufferedElements = new ArrayList<>();
    }


    // sink的invoke方法，用于将数据写入外部存储介质
    @Override
    public void invoke(Tuple2<String, Integer> value, Context contex) throws Exception {
        bufferedElements.add(value);
        if (bufferedElements.size() == threshold) {
            for (Tuple2<String, Integer> element : bufferedElements) {
                // send it to the sink
            }
            bufferedElements.clear();
        }
    }

    // 将state进行持久化，也就是将数据写入到state中；该方法在执行checkpoints的时候被调用，根据持久化方案将数据写入不同的介质中
    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        checkpointedState.clear();
        for (Tuple2<String, Integer> element : bufferedElements) {
            checkpointedState.add(element);
        }
    }

    //初始化和注册satate
    //
    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        // 定义一个状态描述符，描述状态的名称和类型
        ListStateDescriptor<Tuple2<String, Integer>> descriptor =
                new ListStateDescriptor<>(
                        "buffered-elements",
                        TypeInformation.of(new TypeHint<Tuple2<String, Integer>>() {
                        }));
        // 注册一个state
        checkpointedState = context.getOperatorStateStore().getListState(descriptor);

        //判断是否需要恢复state数据
        if (context.isRestored()) {
            //数据恢复逻辑，从state中读取数据，然后放入缓存中
            for (Tuple2<String, Integer> element : checkpointedState.get()) {
                bufferedElements.add(element);
            }
        }
    }
}