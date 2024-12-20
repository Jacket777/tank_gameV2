package com.msb.tank;

import com.msb.tank.net.Client;
import com.msb.tank.net.TankStartMovingMsg;
import com.msb.tank.net.TankStopMsg;
import com.msb.tank.strategy.DefaultFireStrategy;
import com.msb.tank.strategy.FireStrategy;
import com.msb.tank.strategy.FourDirFireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.UUID;

import static com.msb.tank.Dir.L;

public class Player extends AbstractGameObject implements Serializable {
    private int x,y;//坦克的位置
    private Dir dir; //坦克的方向
    public static final int SPEED = 5;
    private boolean bL,bU,bR,bD;
    private boolean moving = false;
    private Group group;
    private boolean live = true;

    private FireStrategy strategy = null; //坦克的射击方式

    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }



    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }



    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
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


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Player(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.initFireStrategy(); //初始化射击方式
    }

    public void paint(Graphics g) {
        if(!this.isLive()) {
         return;
        }
        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.drawString(id.toString(),x,y-10);
        g.setColor(c);
      //  g.fillRect(x,y, 50,50);
        if(this.group == Group.GOOD){
            switch(dir){
                case L:
                    g.drawImage(ResourceMgr.goodtankL,x,y,null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.goodtankU,x,y,null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.goodtankR,x,y,null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.goodtankD,x,y,null);
                    break;
            }

        }

        if(this.group == Group.BAD){
            switch(dir){
                case L:
                    g.drawImage(ResourceMgr.badtankL,x,y,null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.badtankU,x,y,null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.badtankR,x,y,null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.badtankD,x,y,null);
                    break;
            }
        }
        move();
    }

    @Override
    public boolean islive() {
        return this.live;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                //dir = Dir.U;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                //dir = Dir.R;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                //dir = Dir.D;
                break;
            default:
                break;
        }
        setMainDir();
    }

    public void keyReleased(KeyEvent e) {
        moving = false;
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        setMainDir();

    }

    /**
     * 设置坦克的方向
     */
    private void setMainDir() {
        boolean oldMoving = moving;
        System.out.println("bL: "+bL);
        System.out.println("bU: "+bU);
        System.out.println("bR: "+bR);
        System.out.println("bD: "+bD);
        System.out.println("===============================");
        if(!bL && !bU && !bR&& !bD){
           // System.out.println("stop tank moving ...");
            moving = false; //all key is up so tank is stop
            Client.INSTANCE.send(new TankStopMsg(this.id,this.x,this.y));
           // System.out.println("have send the message ...");
        }else{
            moving = true;
            if(!bL&&bU&&!bR&&!bD){
                dir = Dir.U;
            }
            if(!bL&&!bU&&bR&&!bD){
                dir = Dir.R;
            }
            if(!bL&&!bU&&!bR&&bD){
                dir = Dir.D;
            }
            if(bL&&!bU&&!bR&&!bD){
                dir = L;
            }

            //原有状态不是移动状态，则坦克立刻开始移动
            if(!oldMoving){
                Client.INSTANCE.send(new TankStartMovingMsg(this.id,this.x,this.y,this.dir));
            }
        }
    }


    public void move(){
        if(!moving) return;
        switch(dir){
            case L:
                x -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
    }





    private void initFireStrategy(){
        String className = PropertyMgr.get("tankFireStrategy");
        try{
            Class clazz = Class.forName("com.msb.tank.strategy."+className);
            strategy = (FireStrategy)(clazz.getDeclaredConstructor().newInstance());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void fire() {
        strategy.fire(this);
    }

    public void die() {
        this.setLive(false);
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }
}
