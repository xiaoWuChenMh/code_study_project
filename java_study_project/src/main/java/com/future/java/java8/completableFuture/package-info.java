package com.future.java.java8.completableFuture;
/*
* @Description: Java 8 - Lambda CompletableFuture 异步处理操作
*  介绍： CompletableFuture是Java 8中引入的一个类，用于处理异步编程和并发操作。它提供了一种简洁而强大的方式来处理异步任务的结果。
*  特点：异步执行、链式操作、异常处理、多任务组合
*  学习资料：
*       https://blog.csdn.net/zsx_xiaoxin/article/details/123898171  （在异步回调处理的地方有点问题？）
*       https://zhuanlan.zhihu.com/p/643454663
*  学习内容：
*       1、[CreateFuture]      创建异步任务 suppleyAsync和runAsync
*       2、[BlockGetReturn]    获取任务执行的结果(阻塞主线程）：get、join、getNow、complete、completeExceptionally
*       3、[AsynGetReturn]     异步回调方法获取结果(不阻塞主线程）：thenApply、thenRun、thenAccept ||  whenComplete、handle
*       4、[CombinationFuture] 多任务组合处理：
*           4.1 thenCombine、thenAcceptBoth 和runAfterBoth
*               将两个CompletableFuture组合起来处理，只有两个任务都正常完成时，才进行下阶段任务
*           4.2 applyToEither、acceptEither和runAfterEither
*           4.1 allOf / anyOf
*/