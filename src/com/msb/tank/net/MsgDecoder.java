package com.msb.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        int length = buf.readInt();
        byte[]bytes = new byte[length];
        buf.readBytes(bytes);
        TankJoinMsg tjm = new TankJoinMsg();
        tjm.parse(bytes);
        list.add(tjm);
    }
}
