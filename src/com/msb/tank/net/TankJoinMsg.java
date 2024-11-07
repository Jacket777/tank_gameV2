package com.msb.tank.net;

import com.msb.tank.*;

import java.io.*;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }




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

    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try{
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(),dis.readLong());
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                dis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handle() {
        //判断id是否相同
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())){
            return;
        }
        if(TankFrame.INSTANCE.getGm().findTankByUUID(this.id)!=null){
            return;
        }
        Tank tank = new Tank(this);
        TankFrame.INSTANCE.getGm().add(tank);
        //把自己的发进来
        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGm().getMyTank()));
    }
}
