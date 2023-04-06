

## 总结

  ***Eventloop将由一个永远都不会改变的Thread驱动，在内部按照先进先出（FIFO）的顺序执行事件和任务已保证字节内容总是按正确的顺序被处理;***
  
  ***Eventloop将负责一个Channel的整个生命周期内的素有事件；***

## EventLoop的线程模型：

### 线程管理：
    当调用线程正式支撑EventLoop的那个线程时，所提交的代码将会被直接执行，否则将会放入EventLoop的内部队列（独有）中，等待EventLoop下次处理它的
    事件时，它会执行队列中的那些任务/事件；
    ps:因此不要把一个长时间运行的任务放入执行队列，这会阻塞该线程上的其他任务；
    
### 线程分配：
   ***EventLoop被包含在EventLoopGroup中，在不同的传输实现中，EvnetLoop的建立和分配方式也不同。***

####   异步传输：
     EventLoopGroup管理被创建的EventLoop,EventLoop可以同时服务于多个Channel,当新建一个Channel时在当前的Nety4版本中使用EventLoopGroup
     使用顺序循环的方式确定Channel应该分配到哪一个Channel上；
     一单一个Channel分配给一个EventLoop，它将在整个生命周期中都使用这一个EventLoop；
####   阻塞传输：
    EventLoopGroup会为每一个Channel分配一个EventLoop,而Channel将独享这个EventLoop；