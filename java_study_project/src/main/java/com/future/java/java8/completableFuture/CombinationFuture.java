package com.future.java.java8.completableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 *  4、多任务组合处理：
 *      4.1 thenCombine、thenAcceptBoth 和runAfterBoth
 *          将两个CompletableFuture组合起来处理，只有两个任务都正常完成时，才进行下阶段任务
 *      4.2 applyToEither、acceptEither和 runAfterEither
 *      4.1 allOf / anyOf
 *
 * @Author hma
 * @Date 2023/10/10 17:46
 */
public class CombinationFuture {

    public static void main(String[] args) throws Exception{
        // thenCombine、thenAcceptBoth 和runAfterBoth
        tenCombineTest();
        thenAcceptBothTest();
        thenAcceptBothTest();
        // thenCombine、thenAcceptBoth 和runAfterBoth
    }

    // ---------------- 4.1 组合两个CompletableFuture:thenCombine、thenAcceptBoth 和runAfterBoth  --------------

    private static List<CompletableFuture<Integer>> createFuture(){
        List<CompletableFuture<Integer>> resultList = new ArrayList<>();
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " cf1 do something....");
            return 1;
        });

        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " cf2 do something....");
            return 2;
        });
        resultList.add(cf1);
        resultList.add(cf2);
        return resultList;
    }

    /*
     * @Description: thenCombine会将两个任务的执行结果作为所提供函数的参数，【有返回值】
     *   注意两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。
     */
    public static void tenCombineTest() throws ExecutionException, InterruptedException {

        List<CompletableFuture<Integer>> resultList = createFuture();

        CompletableFuture<Integer> cf3 = resultList.get(0).thenCombine(resultList.get(1), (a, b) -> {
            System.out.println(Thread.currentThread() + " cf3 do something....");
            return a + b;
        });

        System.out.println("cf3结果->" + cf3.get());
    }

    /*
     * @Description: thenAcceptBoth同样将两个任务的执行结果作为方法入参，【无返回值】
     *  注意两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。
     */
    public static void thenAcceptBothTest() throws ExecutionException, InterruptedException {

        List<CompletableFuture<Integer>> resultList = createFuture();

        CompletableFuture<Void> cf3 = resultList.get(0).thenAcceptBoth(resultList.get(1), (a, b) -> {
            System.out.println(Thread.currentThread() + " cf3 do something....");
            System.out.println(a + b);
        });

        System.out.println("cf3结果->" + cf3.get());
    }

    /*
     * @Description: runAfterBoth 【没有入参，也没有返回值】
     *  注意两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。
     */
    public static void runAfterBothTest() throws ExecutionException, InterruptedException {

        List<CompletableFuture<Integer>> resultList = createFuture();

        CompletableFuture<Void> cf3 = resultList.get(0).runAfterBoth(resultList.get(1), () -> {
            System.out.println(Thread.currentThread() + " cf3 do something....");
        });

        System.out.println("cf3结果->" + cf3.get());
    }


    // -------------- 4.2 组合两个CompletableFuture:applyToEither、acceptEither和 runAfterEither  ----------------

    /*
     * @Description: applyToEither会将已经完成任务的执行结果作为所提供函数的参数，且该方法有返回值
     */
    public static void applyToEitherTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "cf2 任务完成";
        });

        CompletableFuture<String> cf3 = cf1.applyToEither(cf2, (result) -> {
            System.out.println("接收到" + result);
            System.out.println(Thread.currentThread() + " cf3 do something....");
            return "cf3 任务完成";
        });

        System.out.println("cf3结果->" + cf3.get());
    }


    /*
     * @Description: acceptEither同样将已经完成任务的执行结果作为方法入参，但是无返回值
     */
    public static void acceptEitherTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "cf2 任务完成";
        });

        CompletableFuture<Void> cf3 = cf1.acceptEither(cf2, (result) -> {
            System.out.println("接收到" + result);
            System.out.println(Thread.currentThread() + " cf3 do something....");
        });

        System.out.println("cf3结果->" + cf3.get());
    }

    /*
     * @Description: runAfterEither没有入参，也没有返回值。
     */
    public static void runAfterEitherTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 任务完成");
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 任务完成");
            return "cf2 任务完成";
        });

        CompletableFuture<Void> cf3 = cf1.runAfterEither(cf2, () -> {
            System.out.println(Thread.currentThread() + " cf3 do something....");
            System.out.println("cf3 任务完成");
        });

        System.out.println("cf3结果->" + cf3.get());
    }

    // -------------- 4.4 组合多个CompletableFuture:allOf / anyO  ----------------

    /*
     * @Description: allOf：CompletableFuture是多个任务都执行完成后才会执行，只有有一个任务执行异常，则返回的CompletableFuture执行get方法时会抛出异常，如果都是正常执行，则get返回null。
     */
    public static void allOfTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 任务完成");
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                int a = 1/0;
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 任务完成");
            return "cf2 任务完成";
        });

        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf3 任务完成");
            return "cf3 任务完成";
        });

        CompletableFuture<Void> cfAll = CompletableFuture.allOf(cf1, cf2, cf3);
        System.out.println("cfAll结果->" + cfAll.get());
    }


    /*
     * @Description: anyOf ：CompletableFuture是多个任务只要有一个任务执行完成，则返回的CompletableFuture执行get方法时会抛出异常，如果都是正常执行，则get返回执行完成任务的结果。
     */
    public static void anyOfTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf1 do something....");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 任务完成");
            return "cf1 任务完成";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 任务完成");
            return "cf2 任务完成";
        });

        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread() + " cf2 do something....");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf3 任务完成");
            return "cf3 任务完成";
        });

        CompletableFuture<Object> cfAll = CompletableFuture.anyOf(cf1, cf2, cf3);
        System.out.println("cfAll结果->" + cfAll.get());
    }
}
