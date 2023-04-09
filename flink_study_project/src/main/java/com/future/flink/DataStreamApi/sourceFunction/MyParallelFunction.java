package com.future.flink.DataStreamApi.sourceFunction;

import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

/**
 *  创建自定义多并行度为的source 如果使用该自定义Source，如果代码中没有设置并行度，会根据机器性能自动设置并行度。
 *  继承CheckpointedFunction是为了实现Checkpointed功能
 */
public class MyParallelFunction  implements ParallelSourceFunction<Long>, CheckpointedFunction {

    Long count = 0L;
    Boolean isRunning = true;

    @Override
    public void run(SourceContext<Long> ctx)  {
        while ( isRunning && count<100) {
            synchronized (ctx.getCheckpointLock()){
                ctx.collect(count);
                count++;
            }
        }

    }

    @Override
    public void cancel() {
        isRunning=false;
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) {
        // 待补充
    }

    @Override
    public void initializeState(FunctionInitializationContext functionInitializationContext) {
        // 待补充
    }
}
