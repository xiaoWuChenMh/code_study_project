## 目录
##### 1、总结
##### 2、引导客户端
##### 3、引导服务端
##### 4、从Channel引导客户端
##### 5、Netty的ChannelOption 和属性
##### 6、引导DatagramChannel


### 1、总结

   ***“引导”的含义对应用程序进行配置，并使它能够运行起来，在Netty通过一个抽象类AbstractBootstrap来定义引导的功能。***
   ***而对于通信的两端来说，分别继承AbstractBootstrap，延生出两个实现类，分别用于引导客户端和服务端。***
   
   ***服务端ServerBootstrap 使用一个父channel来接受来自客户端的连接，并创建子Channel用来他们之间的通信。***
   
   ***客户端Bootstrap只有一个channel用来处理所有的网络交互。***
   
   ***引导支持复制（浅拷贝）。***
   
   ***注意Channel和EventLoopGroup的兼容性，Nio系列的只能和Nio系列的匹配，oio和oio的匹配；***

### 2、引导客户端

**引导客户端Bootstrap，被用于客户端或者使用了无连接协议的应用程序中；**
**在bind方法调用后会创建一个新的Channel,在之后将会调用connnect方法建立连接，而connnect方法调用后会在新建立一个Channel；**

##### 2.1 常用api
    
    group(EventLoopGroup): 继承自抽象父类，设置用来处理Channel所有事件的EventLoopGroup;
    
    channel(Class<? extends C> ) : 继承自抽象父类，设置channel的实现类；
   
    channelFactory(ChannelFactory<? extends C> ):继承自抽象父类，如该Channel没有提供默认的构造函数，可以该方法来指定一个工厂类，它会被bind方法调用；
   
    localAddress : 继承自抽象父类，指定Channel应绑定到的本地地址，未指定操作系统会创建一个随机地址，或者也可以通过bind和connect方法来指定；
   
    option(ChannelOption<T> option, T value): 继承自抽象父类，设置ChannelOption，会被应用到每个新建的Channel的ChannelConfig上;该方法在Channel被创建后在调用无效； 
             这些选项会通过bind和connect方法设置到channel上。
   
    attr(AttributeKey<T> key, T value): 继承自抽象父类，为新建的Channle设置属性值，这些属性会通过bind和connect方法设置到channel上；该方法在Channel被创建后在调用无效。
    
    handler(ChannelHandler) :继承自抽象父类，添加接收事件通知的ChannelHandler到Pipeline中；
    
    clone(): 覆盖实现，创建一个当前Boootstrap的克隆，具有和原始的Bootstrap相同的设置信息；
    
    remoteAddress:该类自有的方法，设置远程地址，也可以通过conect方法来指定远程地址
    
    bind: 继承自抽象父类，绑定Channel并返回一个ChannelFuture,其将会在绑定操作完成后接收到通知，在哪之后必须调用connect方法来建立连接；
   
    connect:该类自有的方法，连接到远程节点并返回一个ChannelFuture,其将会在连接操作完成后接收到通知。
   
##### 2.2 客户端引导程序

    public static void main(String[] args) {
            EventLoopGroup group  = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();       //建立客户端引导程序实例
            bootstrap.group(group)                       //设置EventLoopGroup
                    .channel(NioSocketChannel.class)     //指定要使用的Channel实现
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() { //设置用于处理Channel事件和数据的Inboun（ChannelInboundHandler）
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                            System.out.println("消费传入的数据");
                        }
                    });                                    
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost",9090)); //连接到远程主机
            future.addListener(new ChannelFutureListener() {   //添加监控
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        System.out.println("连接已建立");
                    }else{
                        System.out.println("尝试连接建立失败");
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        }
        
        --- 设置步骤
          1、设置EventLoopGroup；
          2、指定要使用的Channel实现
          3、设置handler 、option 、remoteAddress（可以在connect是设置）没有先后顺序要求
            3.1 入站handler，添加入站逻辑和异常处理
            3.2 出战handler,添加出站逻辑和异常处理
          4、连接到远程主机
          5、添加监控

### 3、服务端引导程序

**服务端引导程序ServerBootstrap，在执行bind方法后，会先建立一个ServerChannel用来接受来自客户端的连接,且它会管理多个子Channel,通过这些子channel和客户端进行通信**
**ServerBootstrap接受两个EventLoopGrou，boss和worker,boss这个EventLoopGroup作为一个acceptor负责接收来自客户端的请求，然后分发给worker这个EventLoopGroup来处理所有的事件event和channel的IO。**

##### 3.1 常用api

    group : 设置ServerBootstrap要用的EventLoopGroup。用于ServerChannel和被接受的子Channel的I/O处理；
    
    channel : 设置将要被实例化的ServerChannel类；
    
    channelFactory :同客户端引导程序（没有connect方法）;
    
    localAddress : 同客户端引导程序（没有connect方法），只不过是ServerChannel而不是Channel；
    
    option :同客户端引导程序（没有connect方法）;
    
    childOption : 指定当子Channelb被接受时，应用到子Channel的ChannelConfig的ChannelOption
    
    attr :同客户端引导程序（没有connect方法）;
    
    childAttr :为子Channel设置属性，其他同上;
    
    handler :为ServerChannel的pipeLine添加ChannelHandler;
    
    childHandler : 为子Channel的pipeLine添加ChannelHandler;
    
    clone : 克隆操作，新的ServerBootstrap和原始的具有相同的设置信息；
    
    bind:绑定ServerChannel并返回一个ChannelFuture,其将会在绑定操作完成后收到通知（带着成功或者失败的结果）
    
##### 3.2 服务端引导程序 
   
      public static void main(String[] args) {
    
            //boss线程，用来处理所监听端口的Socket请求，并为该请求新建一个子Channel（一个打开的socket对应一个channel）,然后转给worker线程；
            EventLoopGroup bossGroup  = new NioEventLoopGroup();
            //worker线程，用来处理客户端的请求信息
            EventLoopGroup workerGroup = bossGroup;
            ServerBootstrap bootstrap = new ServerBootstrap();  //建立服务端引导程序实例
            bootstrap.group(bossGroup,workerGroup)              //设置EventLoopGroup
                    .channel(NioServerSocketChannel.class)      //指定要使用的Channel实现
                    .childHandler(new SimpleChannelInboundHandler<ByteBuf>() { //设置用于处理子Channel事件和数据的Inboun
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                            System.out.println("处理入站信息");
                        }
                    });
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(9090));//设置要监听的端点
            future.addListener(new ChannelFutureListener() {  //添加监控
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("服务端建立成功");
                    } else {
                        System.out.println("尝试建立连接失败");
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        }

### 4、从Channel引导客户端

***当服务端是一个代理服务器，接收到请求后需要转发给另一个服务端时，这时需要代理服务器充当客户端，对该场景Netty提供的解决方案是：通过channel在引导出一个客户端；***


### 5、Netty的ChannelOption 和属性
   
    文件option和childoption.md

### 6、引导DatagramChannel

### 7、在引导过程中添加多个ChannelHandler
    
    Netty提供了一个特殊的ChannelInboundHandlerAdapter的子类————ChannelInitializer；
    
    当Channle被注册到了它的EventLoop之后，就会调用你的实现的initChannel方法，在该方法返回后，ChannelInitializer的实例会从ChannelPipeline中移除自己；
    
    

