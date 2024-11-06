package com.msb.tank.net;

import com.msb.tank.Dir;
import com.msb.tank.Group;
import com.msb.tank.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 坦克加入游戏的消息
 */
public class TankJoinMsg {
    private int x, y;
    private Dir dir;
    private boolean moving;
    private Group group;
    private UUID id; //自己的id


    public TankJoinMsg() {
    }


    public TankJoinMsg(Player p) {
        this.x = p.getX();
        this.y = p.getY();
        this.dir = p.getDir();
        this.moving = p.isMoving();
        this.group = p.getGroup();
        this.id = p.getId();
    }

    public byte[]toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[]bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(baos!=null){
                    baos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                if(dos!=null){
                    dos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
    }
}
