package com.future.flink.DataStreamApi.State.broadcaststate;

import com.future.flink.DataStreamApi.State.broadcaststate.model.Action;
import com.future.flink.DataStreamApi.State.broadcaststate.model.Pattern;
import com.future.flink.common.kafka.KafkaExampleUtil;
import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.util.Collector;

public class BroadcastStateTest {

    public static void main(String[] args) throws Exception {

        final ParameterTool parameterTool = ParameterTool.fromArgs(args);
        StreamExecutionEnvironment env = KafkaExampleUtil.prepareExecutionEnv(parameterTool);

        // 用户行为事件流
        DataStream<Action> actions = env
                .addSource(
                        new FlinkKafkaConsumer010<>(
                                parameterTool.getRequired("action-topic"),
                                new ActionEventSchema(),
                                parameterTool.getProperties()));

        // 1、定义一个用于广播的事件流
        DataStream<Pattern> patterns = env
                .addSource(
                        new FlinkKafkaConsumer010<>(
                                parameterTool.getRequired("pattern-topic"),
                                new PatternEventSchema(),
                                parameterTool.getProperties()));

        KeyedStream<Action, Long> actionsByUser = actions
                .keyBy((KeySelector<Action, Long>) action -> action.userId);

        // 2、定义状态的描述信息，这里是一个mapState的描述符；没有用到key所以给VOID
        MapStateDescriptor<Void, Pattern> bcStateDescriptor =
                new MapStateDescriptor<>("patterns", Types.VOID, Types.POJO(Pattern.class));

        // 3、将一个事件流转化为Broadcast State
        BroadcastStream<Pattern> bcedPatterns = patterns.broadcast(bcStateDescriptor);

        DataStream<Tuple2<Long, Pattern>> matches = actionsByUser
                .connect(bcedPatterns) // 4、使用广播数据，将广播数据和用户行为流connect到一起
                .process(new PatternEvaluator()); // 5、处理逻辑

        matches.print();

        env.execute("CartDetectPatternEvaluatorExample");

    }

    public static class PatternEvaluator
            extends KeyedBroadcastProcessFunction<Long, Action, Pattern, Tuple2<Long, Pattern>> {

        // handle for keyed state (per user)，用于存储当前用户行为的前一个事件行为
        ValueState<String> prevActionState;
        // broadcast state descriptor ，定义广播流的事件描述，用于获取广播事件
        MapStateDescriptor<Void, Pattern> patternDesc;

        // 1、初始化state
        @Override
        public void open(Configuration conf) {
            // initialize keyed state
            prevActionState = getRuntimeContext().getState(
                    new ValueStateDescriptor<>("lastAction", Types.STRING));
            patternDesc =
                    new MapStateDescriptor<>("patterns", Types.VOID, Types.POJO(Pattern.class));
        }

        /**
         * 3、定义用户行为流水的处理逻辑
         * Called for each user action.
         * Evaluates the current pattern against the previous and
         * current action of the user.
         */
        @Override
        public void processElement(
                Action action,
                ReadOnlyContext ctx,
                Collector<Tuple2<Long, Pattern>> out) throws Exception {
            // get current pattern from broadcast state，从广播事件中获取数据
            Pattern pattern = ctx
                    .getBroadcastState(this.patternDesc)
                    // access MapState with null as VOID default value
                    .get(null);
            // 处理逻辑，用户自己决定怎么处理。
            // get previous action of current user from keyed state
            String prevAction = prevActionState.value();
            if (pattern != null && prevAction != null) {
                // user had an action before, check if pattern matches
                if (pattern.firstAction.equals(prevAction) &&
                        pattern.secondAction.equals(action.action)) {
                    // MATCH
                    out.collect(new Tuple2<>(ctx.getCurrentKey(), pattern));
                }
            }
            // update keyed state and remember action for next pattern evaluation
            prevActionState.update(action.action);
        }

        /**
         * 2、定义更新广播事件流的逻辑
         * Called for each new pattern.
         * Overwrites the current pattern with the new pattern.
         */
        @Override
        public void processBroadcastElement(
                Pattern pattern,
                Context ctx,
                Collector<Tuple2<Long, Pattern>> out) throws Exception {
            // store the new pattern by updating the broadcast state
            BroadcastState<Void, Pattern> bcState = ctx.getBroadcastState(patternDesc);
            // storing in MapState with null as VOID default value
            bcState.put(null, pattern);
        }
    }
}
