package com.msb.tank.net;

import com.msb.tank.Dir;
import com.msb.tank.Group;
import com.msb.tank.Tank;
import com.msb.tank.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStartMovingMsg extends Msg{
    private UUID id;
    private int x,y;
    private Dir dir;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public TankStartMovingMsg() {
    }

    public TankStartMovingMsg(UUID id, int x, int y, Dir dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
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
            dos.writeInt(dir.ordinal());
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
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try{
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
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
    public void handle() {
        //1.判断
        if(this.id.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())){
            return;
        }
        //2. 查找tank是否已经在游戏画面中
        Tank t = TankFrame.INSTANCE.getGm().findTankByUUID(this.id);
        if(t!=null){
            t.setMoving(true);
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(this.dir);
        }

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }

    @Override
    public String toString() {
        return "TankStartMovingMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                '}';
    }
}
