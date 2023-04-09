package com.future.flink.DataStreamApi;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.util.ArrayList;
import java.util.List;

/**
 * flink的拆分流（旁路输出）SideOutPut 的demo代码
 * 注意：
 *   1、SideOutPut方式拆分流是可以多次进行拆分的，无需担心会爆出异常。
 */
public class SideOutPutDemo {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //第一步：获取数据源
        DataStreamSource<Tuple3<Integer,Integer,Integer>> items = getDataSource(env);

        // 第二步：定义 OutputTag，用于作为数据的标记符号,同时也声明了新流的数据类型
        OutputTag<Tuple3<Integer,Integer,Integer>> zeroStream = new OutputTag<Tuple3<Integer,Integer,Integer>>("zeroStream") {};
        OutputTag<Tuple3<Integer,Integer,Integer>> oneStream = new OutputTag<Tuple3<Integer,Integer,Integer>>("oneStream") {};

        // 第三步： 通过process函数来标记流里的数据 ，即new一个ProcessFunction的匿名子类对流里的数据进行标记
        SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> processStream = items.process(new ProcessFunction<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>() {
            @Override
            public void processElement(Tuple3<Integer, Integer, Integer> value, Context ctx, Collector<Tuple3<Integer, Integer, Integer>> out) throws Exception {
                // 本例是将流拆分成了两道，通过调用getSideOutput可以获取新的流。
                // 通过调用out.collect(value); 也可以将流数据下发，可以使得不调用getSideOutput能直接获取所有的流数
                if (value.f0 == 0) {
                    //不仅可以标记，也可以调整流中得数据，如：转换类型、逻辑运算等
                    ctx.output(zeroStream, value);
                } else if (value.f0 == 1) {
                    ctx.output(oneStream, value);
                }
            }
        });

        // 第四步：拆分流：通过对流调用getSideOutput来获取标记对象
        DataStream<Tuple3<Integer, Integer, Integer>> zeroSideOutput = processStream.getSideOutput(zeroStream);
        DataStream<Tuple3<Integer, Integer, Integer>> oneSideOutput = processStream.getSideOutput(oneStream);

        // 第五步：输出流里的数据
        zeroSideOutput.print();
        oneSideOutput.printToErr();
        // 补充：只有紫processElement方法里调用了out.collect(value)，该流中才有数据。
        processStream.print();

        //打印结果
        String jobName = "user defined streaming source";
        env.execute(jobName);
    }

    /**
     * 获取数据源
     * @param env
     */
    public static DataStreamSource<Tuple3<Integer,Integer,Integer>>  getDataSource(StreamExecutionEnvironment env){
        List data = new ArrayList<Tuple3<Integer,Integer,Integer>>();
        data.add(new Tuple3<>(0,1,0));
        data.add(new Tuple3<>(0,1,1));
        data.add(new Tuple3<>(0,2,2));
        data.add(new Tuple3<>(0,1,3));
        data.add(new Tuple3<>(1,2,5));
        data.add(new Tuple3<>(1,2,9));
        data.add(new Tuple3<>(1,2,11));
        data.add(new Tuple3<>(1,2,13));
        return env.fromCollection(data);
    }
}
