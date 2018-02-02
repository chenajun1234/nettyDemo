package cn.cj.stickpackage;
import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;  
  
public class BaseServer {  
  
    private int port;  
      
    public BaseServer(int port) {  
        this.port = port;  
    }  
      
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))  
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                          
                        protected void initChannel(SocketChannel ch) throws Exception {  
                            //专门用于以换行符为分割的消息的解码，能够处理\n和\r\n的换行符。 100 规定一行数据最大的字节数
//                            ch.pipeline().addLast(new LineBasedFrameDecoder(100));
                            //定长解码器 获取的帧数据有117个字节就切分一次
//                            ch.pipeline().addLast(new FixedLengthFrameDecoder(117));  
                            //分割符解码器
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(100,Unpooled.copiedBuffer(";".getBytes()))); 
                            ch.pipeline().addLast(new StringDecoder());  
                            ch.pipeline().addLast(new BaseServerHandler());  
                        };  
                          
                    }).option(ChannelOption.SO_BACKLOG, 128)     
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
        new BaseServer(port).start();  
    }  
}  