package com.future.java.java8.completableFuture;

/**
 * @Description
 *  2、获取任务执行的结果(阻塞主线程）：get、join、getNow、complete、completeExceptionally
 *
 * @Author hma
 * @Date 2023/10/10 17:49
 */
public class BlockGetReturn {

    /*
     *     // 会阻塞当前线程,如果完成则返回结果，否则就抛出具体的异常
     *     public T get() throws InterruptedException, ExecutionException
     *
     *     // 指定最大时间等待返回结果，否则就抛出具体异常
     *     public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
     *
     *     // 它会阻塞当前线程，完成时返回结果值，否则抛出unchecked异常。为了更好地符合通用函数形式的使用，
     *     //如果完成此 CompletableFuture所涉及的计算引发异常，则此方法将引发unchecked异常并将底层异常作为其原因
     *     public T join()
     *
     *     // 如果完成则返回结果值（或抛出任何遇到的异常），否则返回给定的 valueIfAbsent。
     *     public T getNow(T valueIfAbsent)
     *
     *     // 如果任务没有完成，返回的值设置为给定值
     *     public boolean complete(T value)
     *
     *     // 如果任务没有完成，就抛出给定异常
     *     public boolean completeExceptionally(Throwable ex)
     */

}
