## 目录
###### 1、Channel
###### 2、数据处理组件 ChannelHandler 
###### 3、ChannelInboundHandler
###### 4、ChannelOutboundHandler
###### 5、ChannelPipeline
###### 6、ChannelHandlerContext
###### 7、异常处理

## 说明
     
     ChannelPipeline内的实例是handler，入站handler是inBound,出站handler是outBound
     
     channel作为连接客户端和通信端的通信管道，byteBuf是管道内的流动的水流的最小单位，管道内的水流可以流入也可以流出；
     
     当channel管道建立成功后，还要为其分配一个pipeline，这样才是一个完整的通信管道
     
     通过pipeline把数据处理组件ChannelHandler链接在一起
     
     为期绑定一个EvenLoop(做什么用的)
     每个管道绑定一个pipeline作为控制系统，控制管道的流入和流出
     管道又包含一个输入和输出管道，输入管道上
     
     EvenLoop(I/O线程)来处理事件
     
## 1、Channel

总结：

    可以把Channel看做是连接客户端和服务端的一条数据管道，里面传输的数据是ByteBuf,当Channel创建成功后会尝试注册到EnvenLoop上，同时还会为其分配一个ChannelPipeline(分配后不可变)。

类型：

    接口

生命周期（当状态发送改变后，会生成对应的事件，会被转发给ChannelPipeline中的ChannelHandler）：

    ChannelUnregistered : 已被创建，但未注册到EventLoop;
    
    ChannelRegistered   : 已被注册到EventLoop;
    
    ChannelActive       : 处于活动状态（已连接到它的远程节点），代表现在可以接收和发送数据；
    
    ChannelInattive     : 没有连接到远程节点
    
Channel的实现类：
   
    Socket请求： NioSocketChannel（客户端） 和 NioServerSocketChannel（服务端）


### 2、数据处理组件 ChannelHandler 

总结：

    ChannelHandler的接口定义，ChannelInboundHandler（入站）和ChannelOutboundHandler（出站）是其下的重要子接口

类型：

    接口

方法：
   
    handlerAdded    : 当把ChannelHandler添加到ChannelPipeline中时被调用；
                      调用链：ChannelPipeline的handler添加方法 --》ChannelHandlerContext.callHandlerAdded -->ChannelHandler.handlerAdded
    
    handlerRemoved  : 当从channelPipeline中移除ChannelHandler时被调用；
             调用链  : ChannelPipeline.remove --> 
                      ChannelHandlerContext.callHandlerRemoved --> 
                      ChannelHandler.handlerRemoved  -- end
     
    exceptionCaugth : 当处理过程在ChannelPipeLine中有错误产生时被调用
    
其他：

    在Handler的实现类上标记@Sharable,可以让多个pipeline共享该Handler，但要注意线程安全的处理
   
### 3、ChannelInboundHandler

总结：

    是ChannelHandler的子接口，用于数据的接收处理，也是一系列的接收处理Handler的顶级接口定义。同时通过类的父类是否为ChannelInboundHandler，来确定其是否为接收处理Handler.

类型：

    接口

方法(对应的Channel状态发生改变 和 数据被接收时 被调用，大多数情况下被动触发)：
    
    channelRegistered : 当Channel已注册到它的EventLoop并且能够处理I/O时被调用，对应Channel生命周期中的ChannelRegistered；
    
    channelUnregistered : 当Channel从它的EventLoop注销并且无法处理任何I/O时被调用，对应Channel生命周期中的ChannelUnregistered；
    
    channelActive : 当Channel处于活动状态时被调用；Channel已经连接/绑定并且已经就绪；对应Channel生命周期中的ChannelActive；
    
    channelInactive : 当Channel离开活动状态且不再连接它的远程节点时被调用；对应Channel生命周期中的ChannelInattive；
    
    channelRead : 当从Channel读取数据时被调用，每次从Channle读取调用一次，直到Channel中的可读字节都被读取完后调用channelReadComplete()
                  我们的逻辑代码都会写在这里，因为只有从这里我们才可以得到Channel里的数据
    
    channelReadComplete : 当Channle上一个读操作完成时被调用,及所有可读字节都已经从Channel中读取之后，将会调用，因此在该方法调用之前会多次调用channelRead（）
                  调用链 : ChannelPipeline.channelReadComplete --> 
                          ChannelHandlerContext.fireChannelReadComplete -->
                          ChannelHandlerContext.invokeChannelReadComplete  -->
                          ChannelHandlerContext.invokeChannelReadComplete  -->
                          ChannelInboundHandler.channelReadComplete  --end
    
    channelWritabilityChanged : channel写状态状态发生改变时被调用，可以在Channel变为再次可写时恢复写入或者也可以做些操作保障写入操作不会完成的太快（避免OutofMemoryError）
    
    exceptionCaught : 用于处理异常，任何异常发生后都会在流经整个pipeline在所有后续的handler中传播，而该方法就是入站handler拦截处理异常的地方，
    
    userEventTriggered : 用户事件，当pipeline.fireUserEventTriggered()方法被调用后触发，因为一个POJO被传进了pipeline

实现类：

    SimpleChannelInboundHandler：可以自动释放资源，spark中的RBackendHandler就是继承的该类，作为最后一个Handler,在类RBackend中被使用

    ChannelInboundHandlerAdapter：适配器，提供了Inbound的基本实现
    
    .....Netty提供了非常多的各类功能的InBound
    
资源管理：

    主动释放：通过调用ReferenceCountUtil.release(msg)方法来释放资源msg;
            使用场景：重写ChannelInboundHandler的channelRead方法时，注意最后一个Handler需要手动释放资源
            
    主动释放：SimpleChannelInboundHandler自动释放资源

### 4、ChannelOutboundHandler

总结：

    是ChannelHandler的子接口，用于数据的出站处理，也是一系列的出站处理Handler的顶级接口定义。同时通过类的父类是否为ChannelOutboundHandler，来确定其是否为出站处理的Handler.

类型：

    接口

方法：
    
    bind : 当请求将Channel绑定到“本地地址”时被调用；
    
    connect : 当请求将Channel连接到远程节点时被调用；
    
    disconnect:当请求将Channel从远程节点断开时被调用；
    
    close : 当请求关闭Channel时被调用；
    
    deregister : 当请求将Channel从它的EnventLoop注销时被调用；
    
    read : 当请求从Channel读取更多的数据时被调用；
    
    write : 当请求通过Channel将数据写到远程节点时被调用；
    
    flush : 当请求通过Channle将入队数据冲刷到远程节点时被调用； 
    

实现类:

    ChannelOutboundHandlerAdapter：适配器，提供了Outbound的基本实现
    
    ......Netty提供了非常多的各类功能的OutBound

通知：
    
    ChannelOutboundHandler的很多方法有一个参数是ChannelPromise，它是ChannelFuture的扩展接口，定义了一些操作完成后的通知
    
资源管理：
    
    资源释放：通过调用ReferenceCountUtil.release(msg)方法来释放资源；
            场景：当一个消息被消费或者丢弃了，并没有传递给pipeline中的下一个Outbound，那么用户有责任释放资源；
    不需要释放：消息达到了实际的传输层，那么当它被写入时或者Channel关闭时，都将自动释放资源。
    
    通知ChannelFuture：调用ChannelFuture.setSuccess()方法
            场景：防止ChannelFutureListener收不到某个消息已经被处理的通知，所以需要手动通知

### 5、ChannelPipeline

总结：
    
    pipeline保存了与Channel相关联的Handler，是一个拦截流经Channel的入站和出站事件的ChannelHandler实例链，实例指的是inBound和outBound这两类Handler；
    
    Netty会为每一个新建的Channel分配一个pipeline(这个关系是永久的),而后当有事件时，事件会被inBound或outBound处理，随后调用ChannelHandlerContext实现，将事件转发给同一超类型下的一个Handler.
    
    inbound事件都会从pipeline中通过fire开始触发，随后调用AbstractChannelHandlerContext对应的invoke方法，head的invoke方法，该方法会调用该context的handler的对应处理方法，然后调用该context的fire方法向后面的inbound context传播。
    
    outbound事件都会从pipeline的tail开始传播，从tail向head遍历寻找outbound类型的context，执行该context的handler的对应处理方法。

类型：
    
     接口

实现类：

    DefaultChannelPipeline
    
方法
  
    修改ChannelPipeline(实时的修改pipeline的布局)
    
        addFirst    : 将一个Handler添加到pipeline中的第一位；
        addLast     : 将一个Handler添加到pipeline中的最后位；
        addBefore   : 将一个Handler添加到当前handler的前面;
        addAfter    : 将一个Handler添加到当前handler的后面;
        remove      : 将一个Handler从当前的pipeline中移除,可以通过名称和对象引用来确定移除哪一个handler;
        replace     : 将pipeline中的一个handler替换为另一个handler;
        
    通过pipeline访问Handler的操作
        get         : 通过类型和名称放回Handler
        context     : 通过handler的名称或引用对象返回 handler绑定的ChannelHandlerContext;
        names       : 没有参数，直接返回pipeline中所有的ChannelHandler的名称
        
    触发事件（用于调用入站和出站操作的方法）
    
        ——入站操作
    
        fireChannelRegistered           : 调用pipeline中下一个inBound的HandlerContext的invoke**方法，然后在调用inBound的***(去掉fire前缀)方法   
        fireChannelUnregistered         : 过程同上
        fireChannelActive               : 过程同上
        fireChannelInactive             : 过程同上  
        fireExceptionCaught             : 过程同上
        fireUserEventTriggered          : 过程同上    
        fireChannelRead                 : 过程同上  
        fireChannelReadComplete         : 过程同上 
        fireChannelWritabilityChanged   : 过程同上
        
        
        -- 入站操作
        bind           : 将channel绑定到一个本地地址，会调用pipeline中下一个outBound的bind方法
        connect        : 将channel连接到一个远程地址，会调用用pipeline中下一个outBound的同名方法
        disconnect     : 将channel断开连接，会调用用pipeline中下一个outBound的同名方法
        close          : 将channel关闭，会调用用pipeline中下一个outBound的同名方法
        deregister     : 将channel从先前所分配的EventExecutor（即EventLoop）中注销，会调用用pipeline中下一个outBound的同名方法
        flush          : 冲刷channel所有挂起的写入，会调用用pipeline中下一个outBound的同名方法
        write          : 将消息写入channel，会调用用pipeline中下一个outBound的同名方法,事件会在整个pipeline上传播
        writeAndFlush  : 先调用write方法在调用flush方法
        read           : 请求从channel中读取更多数据，会调用用pipeline中下一个outBound的同名方法 
        



    
pipeline中handler的执行顺序

    入站handler(inBound)按照添加到pipeline的顺序——正序执行，入站口在pipeline头部；
   
    出站handler(outBound)按照添加到pipeline的顺序——倒序执行，出站口在pipeline尾端；
   
    在执行过程中，pipeline会测试事件运动方向的（入站事件的运动方向是从头到尾）下一个handler类型是否和与当前handler类型同属于一个超类，不同则跳过并前进到下一个，再次进行匹配验证，验证成功执行handler内的逻辑；
    
    
### 6、ChannelHandlerContext

总结：
    
    代表了Handler和pipeline之间的关联，每当向pipelien添加一个handler时，都会为该handler创建一个ChannelHandlerContext，因此用Context来管理handler和与其在同一pipeline上的其他handler交互；
    
    调用Channel和pipeline上的同名方法，他们将沿着pipeline链进行传播，而Context上相同的方法，将从当前所关联的handler开始并只会传给下一个能够处理该事件的Handler;
    
    所有的事件调用(无论是Channle还是pipeline)传播，最后都需要通过ChannelHandlerContext将事件转发到下一个Handler
类型：
    
    接口

实现类：
    
    AbstractChannelHandlerContext ：抽象类，handlerState--标记handler的状态（1：待定；2：新增；3：移除）
    
    DelegatingChannelHandlerContext ： 内部类
    
方法
    
    read          : 将数据从Channel读取到第一个入站缓冲区；如果读取成功会触发一个ChannelRead事件，并在最后一个消息被读取完触发ChannelReadComplete方法；
    pipeline      : 返回这个实例所关联的pipeline;
    alloc         : 返回这个实例所关联的Channel所配置的ByteBufAllocator;
    bind          : 绑定到给定的SocketAddress,并返回ChannelFuture;
    channel       : 返回绑定到这个实例的channel;
    close         : 关闭Channel,并返回ChannelFuture
    connect       : 连接给定的SocketAddress,并返回ChannelFuture;
    deregister    : 从之前分配的EventExecutor注销，并返回ChannelFuture;
    disconnect    : 从远程端点断开，并返回ChannelFuture;
    executor      : 返回调度时间的EventExecutor；
    name          : 返回这个实例的唯一名称；
    write         : 通过这个实例写入消息并经过ChannelPipeline;
    writeAndFlush : 通过这个实例写入并冲刷消息并经过ChannelPipeline;
    fire***       : 以fire开头的一些列方法会触发对 下一个inBound上 同名/去掉fire前缀 方法的调用；
    
    
### 7、异常处理

#### 7.1 入站异常处理

    重新Handlerdler的exceptionCaught方法（参数1是context，参数2是抛出的异常），在该方法中处理该异常，异常也属于pipeline里的事件，因此也会按照入站的方向流动到每一个handler里的
    Handlerdler的exceptionCaught方法中。
    
    注意：在最后一个Handler中一定要有异常处理，也就是说有异常处理的handler通常位于pipeline的最后，当日前面的handler你也可以做异常处理；
    
    如何处理异常：很大程度上取决于你的应用程序，可以通过context来关闭Channle，也可以尝试进行恢复，也可以记录某些信息等等；

#### 7.2 出站异常处理

    出站操作中想要获取到正常完成和异常信息，都是通过Future通知机制来完成的。
    
    每个出站操作（write..方法）都会返回一个ChannelFuture。而outBound上的每个方法都会传入一个ChannelFuture的之类实现类ChannelPromise实例。可以通过给ChannelFuture
    添加监听器，在监听器内部实现异常处理。
    
    注意：出站的一般的异常处理使用方法里的参数ChannelPromise实例，出站的细致异常处理使用出站操作的返回的ChannelFuture实例,outBound本身发生异常，Netty会通知任何已经注册到对应ChannelPromise的监听器；
    
    添加监听器
       future.addListener(new ChannelFutureListener() {
           @Override
           public void operationComplete(ChannelFuture channelFuture) throws Exception {
               if(!channelFuture.isSuccess()){
                   channelFuture.cause().printStackTrace();//打印异常堆栈信息
                   channelFuture.channel().close();//关闭channel
               }
           }
       });



