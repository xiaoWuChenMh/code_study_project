package com.future.java.java8.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @Description
 *  1、创建异步任务 suppleyAsync和runAsync
 * @Author hma
 * @Date 2023/10/10 16:03
 */
public class CreateFuture {

    public static void main(String[] args) throws Exception{
        // 带返回值的异步请求
        suppleyAsyncDefaultThPool();
        suppleyAsyncCustomThPool();
        // 不带返回值的异步请求
        runAsyncDefaultThPool();
        runAsyncCustomThPool();

    }

    /*
     * @Description: 带返回值的异步请求，使用默认线程池
     *   使用的方法：public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
     */
    public static void suppleyAsyncDefaultThPool() throws ExecutionException, InterruptedException {
        // 常规写法
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("CompletableFuture.supplyAsync 》》 默认线程池——常规写法");
                return "supplyAsync";
            }
        });

        //Lambda表达式的写法
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("CompletableFuture.supplyAsync 》》 默认线程池——Lambda表达式的写法");
            return "supplyAsync";
        });

        System.out.println("cf1执行结果："+cf1.get());
        System.out.println("cf2执行结果："+cf2.join());
    }

    /*
     * @Description: 带返回值的异步请求，自定义线程池
     *    使用的方法：public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
     */
    public static void suppleyAsyncCustomThPool() throws ExecutionException, InterruptedException {
        //自定义线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //常规写法
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("CompletableFuture.supplyAsync  》》自定义线程池——常规写法");
                return "supplyAsync";
            }
        },executorService);

        // Lambda表达式写法
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("CompletableFuture.supplyAsync 》》 自定义线程池——Lambda表达式的写法");
            return "supplyAsync";
        },executorService);

        System.out.println("cf1执行结果："+cf1.get());
        System.out.println("cf2执行结果："+cf2.get());

    }

    /*
     * @Description: 不带返回值的异步请求，使用默认线程池
     *    使用的方法：public static CompletableFuture<Void> runAsync(Runnable runnable)
     */
    public static void runAsyncDefaultThPool() throws ExecutionException, InterruptedException {
        //常规写法
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("CompletableFuture.runAsync  》》自定义线程池——常规写法");
            }
        });

        // Lambda表达式写法
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
            System.out.println("CompletableFuture.runAsync 》》 自定义线程池——Lambda表达式的写法");
        });

        System.out.println("cf1执行结果："+cf1.get());  // cf1执行结果：null
        System.out.println("cf2执行结果："+cf2.get());  // cf2执行结果：null
    }

    /*
     * @Description: 不带返回值的异步请求，自定义线程池
     *    使用的方法：public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
     */
    public static void runAsyncCustomThPool() throws ExecutionException, InterruptedException {
        //自定义线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //常规写法
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println("CompletableFuture.runAsync  》》自定义线程池——常规写法");
            }
        }, executorService);

        // Lambda表达式写法
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
            System.out.println("CompletableFuture.runAsync 》》 自定义线程池——Lambda表达式的写法");
        },executorService);

        System.out.println("cf1执行结果："+cf1.get());  // cf1执行结果：null
        System.out.println("cf2执行结果："+cf2.get());  // cf2执行结果：null
    }



}
