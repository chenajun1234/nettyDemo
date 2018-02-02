package cn.cj.stickpackage;
import io.netty.buffer.ByteBuf;  
import io.netty.buffer.Unpooled;  
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.ChannelInboundHandlerAdapter;  
  
public class BaseClientHandler extends ChannelInboundHandlerAdapter{  
      
    private byte[] req;  
      
    private int counter;  
      
    public BaseClientHandler() {  
//        req = ("BazingaLyncc is learner" + System.getProperty("line.separator"))  
//            .getBytes();  
        req = ("123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"
            + "123456789abcdefghijklmnopqrstuvwxyz;123456789abcdefghijklmnopqrstuvwxyz;"+System.getProperty("line.separator")).getBytes();  
    }  
      
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        ByteBuf message = null;  
//        for (int i = 0; i < 100; i++) {  
//            message = Unpooled.buffer(req.length);  
//            message.writeBytes(req);  
//            ctx.writeAndFlush(message);  
//        }  
        message = Unpooled.buffer(req.length);  
        message.writeBytes(req);  
        ctx.writeAndFlush(message);  
        message = Unpooled.buffer(req.length);  
        message.writeBytes(req);  
        ctx.writeAndFlush(message);  
    }  
      
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg)  
        throws Exception {  
        String buf = (String) msg;  
        System.out.println("Now is : " + buf + " ; the counter is : "+ ++counter);  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {  
        ctx.close();  
    }  
      
      
  
}  