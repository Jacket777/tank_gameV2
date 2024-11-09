package com.msb.tank.net;

import com.msb.tank.Dir;
import com.msb.tank.Group;
import com.msb.tank.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TankStartMovingMsgTest {
    @Test
    void decode(){
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());
        UUID id = UUID.randomUUID();
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStartMoving.ordinal());
        buf.writeInt(28);
        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Dir.D.ordinal());
        ch.writeInbound(buf);
        TankStartMovingMsg tm = ch.readInbound();
        assertEquals(5,tm.getX());
        assertEquals(8,tm.getY());
    }


    @Test
    public void encode(){
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgEncoder());
        TankStartMovingMsg msg = new TankStartMovingMsg(UUID.randomUUID(),50, 100,Dir.D);
        ch.writeOutbound(msg);
        ByteBuf buf = ch.readOutbound();
        //1.先读消息类型
        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();
        UUID id = new UUID(buf.readLong(),buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        assertEquals(MsgType.TankStartMoving,msgType);


    }
}