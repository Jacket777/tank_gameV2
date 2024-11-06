package com.msb.tank.net;

import com.msb.tank.Dir;
import com.msb.tank.Group;
import com.msb.tank.Player;
import com.msb.tank.net.MsgEncoder;
import com.msb.tank.net.TankJoinMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MsgEncoderTest {

    @Test
    void encode() {
        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgEncoder());
        Player p = new Player(50,100, Dir.R, Group.BAD);
        TankJoinMsg tjm = new TankJoinMsg(p);
        ch.writeOutbound(tjm);

        //读出
        ByteBuf buf = ch.readOutbound();
        int length = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        Dir dir = Dir.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        UUID id = new UUID(buf.readLong(),buf.readLong());
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(Dir.R,dir);
        assertFalse(moving);
    }
}