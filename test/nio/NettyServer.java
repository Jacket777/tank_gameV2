package nio;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class NettyServer {
    public static void main(String[] args) throws Exception{
        //负责处理事件
         EventLoopGroup bossGroup = new NioEventLoopGroup(2);
         EventLoopGroup workGroup = new NioEventLoopGroup(4);
         ServerBootstrap b = new ServerBootstrap();
         b.group(bossGroup,workGroup);//第一个参数的位置是boss的位置
         //异步全双工
         b.channel(NioServerSocketChannel.class);
         // 帮我们内部处理了accept的过程
//         b.childHandler(new ChannelInitializer() {
//             @Override
//             protected void initChannel(Channel channel) throws Exception {
//
//             }
//         });

        b.childHandler(new MyChildInitializer());
       ChannelFuture future =   b.bind(8888).sync();
       future.channel().closeFuture().sync();
       bossGroup.shutdownGracefully();
       workGroup.shutdownGracefully();
    }

}

class MyChildInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println(" a client connected");
        //处理连接好了连接
        socketChannel.pipeline().addLast(new MyChildHandler());
    }
}

class MyChildHandler extends ChannelInboundHandlerAdapter {
    /**
     * 接收数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        System.out.println(buf.toString());
        System.out.println(buf.toString(CharsetUtil.UTF_8));
      // buf.release();
        ctx.writeAndFlush(msg);
       // super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
