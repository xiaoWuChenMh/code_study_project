package com.future.flink.DataStreamApi.sourceFunction;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * 自定义Source，实现一个支持并行度的富类source
 *   继承CheckpointedFunction是为了实现Checkpointed功能
 */
public class MyRichParallelSourceFunction extends RichParallelSourceFunction<Long> implements CheckpointedFunction {

    Long count = 0L;
    Boolean isRunning = true;

    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
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
    public void open(Configuration parameters) throws Exception {
        // 如果需要获取其他链接资源，那么可以在open方法中获取资源链接
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        // 在close中关闭资源链接
        super.close();
    }

    @Override
    public void snapshotState(FunctionSnapshotContext functionSnapshotContext) throws Exception {

    }

    @Override
    public void initializeState(FunctionInitializationContext functionInitializationContext) throws Exception {

    }
}
