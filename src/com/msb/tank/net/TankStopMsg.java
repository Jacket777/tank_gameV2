package com.msb.tank.net;

import com.msb.tank.*;

import java.io.*;
import java.util.UUID;

/**
 * 坦克停止的消息
 */
public class TankStopMsg extends Msg{
    private UUID id;
    private int x, y;
    private boolean moving;
    private Group group;

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




    public TankStopMsg() {
    }


    public TankStopMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }


    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try{
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
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


    @Override
    public byte[]toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[]bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
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
        return "TankStopMsg{" +
                "x=" + x +
                ", y=" + y +
                ", moving=" + moving +
                ", id=" + id +
                '}';
    }


    @Override
    public void handle() {
        //1.判断
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())){
            return;
        }
        //2. 查找tank是否已经在游戏画面中
        Tank t = TankFrame.INSTANCE.getGm().findTankByUUID(this.id);
        if(t!=null){
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStop;
    }
}
