package com.future.flink.DataStreamApi.sourceFunction;


import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 使用自定义的SourceFunction
 */
public class UseMySourceFunction {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Long> items = env.addSource(new MyNoParallelFunction());

        // 第五步：输出流里的数据
        items.print();

        //打印结果
        String jobName = "user defined streaming source";
        env.execute(jobName);
    }
}
