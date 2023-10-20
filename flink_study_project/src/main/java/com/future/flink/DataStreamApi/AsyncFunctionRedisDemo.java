package com.future.flink.DataStreamApi;


import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.util.concurrent.TimeUnit;

/**
 *  基于Redis的异步IO操作实例(
 *     orderedWait（有序）：消息的发送顺序与接收到的顺序相同（包括 watermark ），也就是先进先出。
 *     unorderWait（无序）：
 *        在ProcessingTime中完全无序，即哪个请求先返回结果就先发送(最低延迟和最低消耗)
 *        在EventTime中，以watermark为边界，介于两个watermark之间的消息可以乱序，但是watermark和消息之间不能乱序，这样既认为在无序中又引入了有序，这样就有了与有序一样的开销。
 *     参考资料：
 *       1、https://blog.csdn.net/weixin_51329630/article/details/118657221
 *       2、https://zhuanlan.zhihu.com/p/26889859
 *     ps： 不支持异步IO的redis
 */
public class AsyncFunctionRedisDemo {

    public static void main(String[] args) throws Exception {
        // 1. 初始化流计算运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 2. 从文件读取数据
        DataStreamSource<String> inputStream = getDataSource(env);
        // 4.应用异步 I/O 转换操作，不启用重试，有orderedWait（有序） 和 unorderedWait两个方法（无序）。
        SingleOutputStreamOperator<String> resultDataStream = AsyncDataStream.orderedWait(
                inputStream, // DataStream
                new AsyncReadRedis(), // AsyncFunctionsh实现类
                90000,  // 超时时间
                TimeUnit.MICROSECONDS, //时间单位（微秒）
                1   // 用于定义同时最多会有多少个异步请求在同时处理，该参数可以限制并发请求数量
        );
        resultDataStream.print().setParallelism(2) ;

        //执行
        String jobName = "user defined streaming source";
        env.execute(jobName);
    }

    /**
     * 获取数据源
     * @param env
     */
    public static DataStreamSource<String> getDataSource(StreamExecutionEnvironment env){
        List data = new ArrayList<String>();
        data.add("11111111,1001");
        data.add("22222222,1001");
        data.add("33333333,1002");
        data.add("44444444,1001");
        data.add("55555555,1002");
        data.add("11111111,1001");
        return env.fromCollection(data);
    }

    /**
     * 3、创建一个异步连接redis的 AsyncFunction
     */
    public static class AsyncReadRedis extends RichAsyncFunction<String, String> {
        // Jedis 连接池对象
        private JedisPool jedisPool = null ;
        // Jedis 对象
        private Jedis jedis = null ;

        /**
         * 3.1 利用open方法初始化支持异步操作的数据库客户端 或者 连接池
         * @param parameters
         * @throws Exception
         */
        @Override
        public void open(Configuration parameters) throws Exception {
            jedisPool = new JedisPool(
                    new JedisPoolConfig(), //
                    "node1.itcast.cn", //
                    6379, //
                    10000 //
            );
            jedis = jedisPool.getResource() ;
        }

        /**
         * 3.2 实现异步操作——通过重写 asyncInvoke 方法
         * @param input
         * @param resultFuture
         * @throws Exception
         */
        @Override
        public void asyncInvoke(String input, ResultFuture<String> resultFuture) throws Exception {
            System.out.println("Input: " + input);
              // 使用CompletableFuture发起异步请求，返回结果Future
            CompletableFuture<String> future =CompletableFuture.supplyAsync(new Supplier<String>() {
                @Override
                public String get() {
                    // 数据样本：1,beijing
                    String[] split = input.split(",");
                    // 从Redis获取值
                    String reply = jedis.hget("AsyncReadRedis", split[1]);
                    System.out.println("Output: " + reply);
                    return reply;
                }
            });
            // 注册回调函数，在请求完成后处理结果
            future.thenAccept((String dbResult) -> {
               // 发送结果给下游算子
                resultFuture.complete(Collections.singleton(dbResult));
            });
        }

        // 3.3 超时异常处理——通过重写 timeout 方法,没有重新代表使用默认

        /**
         * 3.4 重写close方法来关闭客户端链接
         * @throws Exception
         */
        @Override
        public void close() throws Exception {
             // 关闭Jedis连接
            if(null != jedis) jedis.close();
        }
    }
}



