package cn.cj.helloNetty;
import io.netty.bootstrap.ServerBootstrap;  
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelInitializer;  
import io.netty.channel.ChannelOption;  
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.SocketChannel;  
import io.netty.channel.socket.nio.NioServerSocketChannel;  
import io.netty.handler.codec.string.StringDecoder;  
import io.netty.handler.codec.string.StringEncoder;  
  
import java.net.InetSocketAddress;  
  
public class HelloWorldServer {  
  
    private int port;  
      
    public HelloWorldServer(int port) {  
        this.port = port;  
    }  
      
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))  
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                          
                        protected void initChannel(SocketChannel ch) throws Exception {  
//                            ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));  
                            ch.pipeline().addLast("decoder", new StringDecoder());  
                            ch.pipeline().addLast("encoder", new StringEncoder());  
                            ch.pipeline().addLast(new HelloWorldServerHandler());  
                        };  
                          
                    }).option(ChannelOption.SO_BACKLOG, 128)  //用来初始化服务端可连接队列，
                    /* SO_KEEPALIVE 当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的
                　　　　连接。当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。*/
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
             // 绑定端口，开始接收进来的连接  
             ChannelFuture future = sbs.bind(port).sync();    
               
             System.out.println("Server start listen at " + port );  
             future.channel().closeFuture().sync();  
        } catch (Exception e) {  
            bossGroup.shutdownGracefully();  
            workerGroup.shutdownGracefully();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        int port;  
        if (args.length > 0) {  
            port = Integer.parseInt(args[0]);  
        } else {  
            port = 8080;  
        }  
        new HelloWorldServer(port).start();  
    }  
} 