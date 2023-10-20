package com.future.java.java8.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * @Description
 *  3、异步回调方法获取结果(不阻塞主线程）：thenApply、thenRun、thenAccept ||  whenComplete、handle
 *
 * @Author hma
 * @Date 2023/10/10 17:50
 */
public class AsynGetReturn {

    public static void main(String[] args) throws Exception{
        thenApplyTest();
        thenAcceptTest();
        thenRunTest();
        whenCompleteTest();
        handleTest();
    }

    /*
     * @Description:  异步回调方法，会将该任务的执行结果即方法返回值作为入参传递到回调方法中，【带有返回值】。
     *    thenApply类型的操作有两个方法：thenApply和thenApplyAsync，区别就在于线程池的选用：
     *       thenApply方法的子任务与父任务使用的是同一个线程；
     *       thenApplyAsync在子任务中是另起一个线程执行任务，但实测中不传入线程池时默认与父任务使用的是同一个线程；
     */
    public static void thenApplyTest() throws InterruptedException {
        // 异步程序
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " cf1....");
            return "supplyAsync";
        });

        // 异步回调 thenApply
        CompletableFuture<String> cf2 =  cf1.thenApply((String result)->{
            System.out.println(Thread.currentThread() + " cf2 ....");
            return "thenApply";
        });

        // 异步回调 thenApplyAsync
        CompletableFuture<String> cf3 =  cf1.thenApplyAsync((String result)->{
            System.out.println(Thread.currentThread() + " cf3 ....");
            return "thenApply";
        },Executors.newSingleThreadExecutor());
        System.out.println("主线程等待一下，等异步操作的子线程执行完 ");
        Thread.sleep(6000);
        System.out.println(Thread.currentThread() + " 主线程 ....");
    }

    /*
     * @Description:  异步回调方法，会将该任务的执行结果即方法返回值作为入参传递到回调方法中，【无返回值】。
     *    thenAccept类型的操作有两个方法：thenAccept和thenAcceptAsync，区别就在于线程池的选用：
     *       thenAccept方法的子任务与父任务使用的是同一个线程；
     *       thenAcceptAsync在子任务中是另起一个线程执行任务，但实测中不传入线程池时默认与父任务使用的是同一个线程；
     */
    public static void thenAcceptTest() throws InterruptedException {
        // 异步程序
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " cf1....");
            return "supplyAsync";
        });

        // 异步回调 thenAccept
        cf1.thenAccept((String result)->{
            System.out.println(Thread.currentThread() + " cf2 ....");
        });

        // 异步回调 thenAcceptAsync
        cf1.thenAcceptAsync((String result)->{
            System.out.println(Thread.currentThread() + " cf3 ....");
        },Executors.newSingleThreadExecutor());
        System.out.println("主线程等待一下，等异步操作的子线程执行完，等待后需要exit帮忙idex才能退出");
        Thread.sleep(6000);
        System.out.println(Thread.currentThread() + " 主线程 ....");
        System.exit(1);
    }

    /*
     * @Description:  异步回调方法【无入参，无返回值】。
     *    thenRun类型的操作有两个方法：thenRun和thenRunAsync，区别就在于线程池的选用：同上
     */
    public static void thenRunTest() throws InterruptedException {
        // 异步程序
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " cf1....");
            return "supplyAsync";
        });

        // 异步回调 thenAccept
        cf1.thenRun(()->{
            System.out.println(Thread.currentThread() + " cf2 ....");
        });

        // 异步回调 thenRun
        cf1.thenRunAsync(()->{
            System.out.println(Thread.currentThread() + " cf3 ....");
        });

        System.out.println("主线程等待一下，等异步操作的子线程执行完，等待后需要exit帮忙idex才能退出");
        Thread.sleep(6000);
        System.out.println(Thread.currentThread() + " 主线程 ....");
        System.exit(1);
    }

    /*
     * @Description:  异步回调方法,会将【执行结果】或者【执行期间抛出的异常】传递给回调方法【无返回值】。
     *    whenComplete类型的操作有两个方法：whenComplete和whenCompleteAsync，区别就在于线程池的选用：同上
     */
    public static void whenCompleteTest() throws InterruptedException {
        // 异步程序
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " cf1....");
            return "supplyAsync";
        });

        // 异步回调 whenComplete
        cf1.whenComplete((result,e)->{
            System.out.println(Thread.currentThread() + " cf2 ....");
        });

        // 异步回调 whenCompleteAsync
        cf1.whenCompleteAsync((result,e)->{
            System.out.println(Thread.currentThread() + " cf3 ....");
        },Executors.newSingleThreadExecutor());

        System.out.println("主线程等待一下，等异步操作的子线程执行完，等待后需要exit帮忙idex才能退出");
        Thread.sleep(6000);
        System.out.println(Thread.currentThread() + " 主线程 ....");
        System.exit(1);
    }

    /*
     * @Description:   跟whenComplete基本一致，区别在于handle的回调方法有返回值。
     *    handle类型的操作有两个方法：handle和handleAsync，区别就在于线程池的选用：同上
     */
    public static void handleTest() throws InterruptedException {
        // 异步程序
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " cf1....");
            return "supplyAsync";
        });

        // 异步回调 handle
        CompletableFuture<String> cf2 = cf1.handle((result,e)->{
            System.out.println(Thread.currentThread() + " cf2 ....");
            return "handle";
        });

        // 异步回调 handleAsync
        CompletableFuture<String> cf3 = cf1.handleAsync((result,e)->{
            System.out.println(Thread.currentThread() + " cf3 ....");
            return "handleAsync";
        },Executors.newSingleThreadExecutor());

        System.out.println("主线程等待一下，等异步操作的子线程执行完，等待后需要exit帮忙idex才能退出");
        Thread.sleep(6000);
        System.out.println(Thread.currentThread() + " 主线程 ....");
        System.exit(1);
    }


}


