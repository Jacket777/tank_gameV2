package nettychat;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    public  ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public  void serverStart() {
        //负责处理事件
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workGroup = new NioEventLoopGroup(4);
        try{
            ServerBootstrap b = new ServerBootstrap();
            //异步全双工
//            b.group(bossGroup, workGroup);//第一个参数的位置是boss的位置
//            b.channel(NioServerSocketChannel.class);
//            b.childHandler(new MyChildServerInitializer());
            // ChannelFuture future = b.bind(8888).sync();
            ChannelFuture future = b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyChildServerInitializer())
                    .bind(8888)
                    .sync();
            ServerFrame.INSTANCE.updateServerMsg("server started");

            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    class MyChildServerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            System.out.println(" a client connected");
            //处理连接好了连接
            socketChannel.pipeline().addLast(new MyChildNettyHandler());
        }
    }

    class MyChildNettyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            clients.add(ctx.channel());//存管道
        }

        /**
         * 接收数据
         *
         * @param ctx
         * @param msg
         * @throws Exception
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf2 = (ByteBuf)msg;
            System.out.println(buf2.toString());
//        System.out.println(buf.toString(CharsetUtil.UTF_8));
//      // buf.release();
//        ctx.writeAndFlush(msg);
            ByteBuf buf = null;
            try {
                buf = (ByteBuf) msg;
                byte[] bytes = new byte[buf.readableBytes()];
                buf.getBytes(buf.readerIndex(), bytes);
                String str = new String(bytes);
                System.out.println("client send " + str);
                if (str.equals("__bye__")) {
                    System.out.println("client ready to quit");
                    clients.remove(ctx.channel());
                    ctx.close();
                    System.out.println(clients.size());
                } else {
                    clients.writeAndFlush(msg);//会自动对buf释放
                }
                //System.out.println(str);
                //System.out.println(buf.refCnt());//拿到buf被多少个对象引用
            } catch (Exception e) {
                e.printStackTrace();
            }
//        finally {
////            if (buf != null) {
////                ReferenceCountUtil.release(buf);
////            }
//        }
            //Server.clients.writeAndFlush(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            clients.remove(ctx.channel());
            ctx.close();
        }
    }

}

