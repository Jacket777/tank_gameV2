package com.msb.tank.net;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    public  ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public  void serverStart() {
        //负责处理事件
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workGroup = new NioEventLoopGroup(4);
        try{
            ServerBootstrap b = new ServerBootstrap();
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
            socketChannel.pipeline()
                           // .addLast(new TankMsgDecoder())
                    .addLast(new MyChildNettyHandler());
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
          // TankMsg tm = (TankMsg) msg;
            System.out.println(" msg "+msg);
           ServerFrame.INSTANCE.updateClientMsg(msg.toString());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            clients.remove(ctx.channel());
            ctx.close();
        }
    }
}

