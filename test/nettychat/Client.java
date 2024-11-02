package nettychat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;


public class Client {
    private Channel channel = null;


    public  void connect() throws Exception{
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline().addLast(new MyClientChatHandler());
                }
            });

            ChannelFuture future = bootstrap.connect("localhost",8888).sync();
            System.out.println(" connected to server");
            /**
             * 等待关闭，如果没有，则等待中国
             */
            future.channel().closeFuture().sync();
        }catch(Exception e){
           e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(String text) {
        System.out.println("send: "+text);
        channel.writeAndFlush(Unpooled.copiedBuffer(text.getBytes()));
    }

    public void closeConnection() {
        send("__bye__");
        channel.close();
    }
}

class MyClientChatHandler extends ChannelInboundHandlerAdapter {
    /**
     * 刚链接上需要做的事情
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello netty server".getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = null;
        try{
            buf = (ByteBuf) msg;
            byte[]bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(),bytes);
            String str = new String(bytes);
            ClientFrame.INSTANCE.updateText(str);
            //System.out.println(str);
            //System.out.println(buf.refCnt());//拿到buf被多少个对象引用
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(buf!=null){
                ReferenceCountUtil.release(buf);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

