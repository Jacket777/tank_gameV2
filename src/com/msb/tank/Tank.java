package com.msb.tank;

import java.awt.*;
import java.util.Random;

public class Tank extends AbstractGameObject{
    private  Group group;
    private int x,y;//坦克的位置
    private Dir dir; //坦克的方向
    public static final int SPEED = 5;
    private boolean moving = true;
    private boolean live = true;
    private int oldX,oldY; //坦克原有位置
    private int width, height; //坦克的宽度和高度

    private Rectangle rect;

   // private Random random = new Random();

    public Rectangle getRect() {
        return rect;
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



    public Tank(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = Group.BAD;
        this.oldX = x;
        this.oldY = y;
        this.width = ResourceMgr.goodtankU.getWidth();
        this.height = ResourceMgr.goodtankU.getHeight();
        this.rect = new Rectangle(x,y,width,height);
    }

    public void paint(Graphics g) {
        if(!this.isLive()) {
         return;
        }
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
        move();
        if(r.nextInt(100)>90){
            fire();
        }

    }

    @Override
    public boolean islive() {
        return this.live;
    }


    public void move(){
        if(!moving) {
            return;
        }

        oldX = x;
        oldY = y;

        boundsCheck();

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

        //敌人坦克方向随机
        randomDir();

        rect.x = x;
        rect.y = y;
    }

    private void boundsCheck() {
        if(x < 5 || y< 50 || x+width > TankFrame.GAME_WIDTH || y +height> TankFrame.GAME_HEIGHT){
            this.back(); //回到上一步
        }
    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }

    private Random r = new Random();

    private void randomDir() {
      //  this.dir = Dir.values()[random.nextInt(Dir.values().length)];
        if(r.nextInt(100) > 90){
            this.dir = Dir.randomDir();
        }
    }


    private void fire() {

        int bx = x+ ResourceMgr.goodtankD.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int by = y+ ResourceMgr.goodtankD.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bx,by,dir,group));
    }

    public void die() {
        this.setLive(false);
       // TankFrame.INSTANCE.addExplode(new Explode(x,y));
        TankFrame.INSTANCE.getGm().add(new Explode(x,y));

    }
}
