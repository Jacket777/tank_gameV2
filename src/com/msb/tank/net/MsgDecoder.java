package com.msb.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if(buf.readableBytes() < 8){
            return;
        }
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        if(buf.readableBytes()<length){
            buf.resetReaderIndex();//没读
            return;
        }
        byte[]bytes = new byte[length];
        buf.readBytes(bytes);
        Msg msg = null;
        msg = (Msg)Class.forName("com.msb.tank.net."+msgType.toString()+"Msg")
                .getDeclaredConstructor()
                .newInstance();
//        if(msgType.equals(MsgType.TankJoinMsg)){
//
//        }
//        TankJoinMsg tjm = new TankJoinMsg();
        //tjm.parse(bytes);
        msg.parse(bytes);
        list.add(msg);
    }
}
