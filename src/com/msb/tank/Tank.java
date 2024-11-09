package com.msb.tank;

import com.msb.tank.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank extends AbstractGameObject {
    private Group group;
    private int x, y;//坦克的位置

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    private Dir dir; //坦克的方向
    public static final int SPEED = 5;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private boolean moving = true;
    private boolean live = true;
    private int oldX, oldY; //坦克原有位置
    private int width, height; //坦克的宽度和高度

    private Rectangle rect;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private UUID id;


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


    public Tank(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.moving = msg.isMoving();
        this.group = msg.getGroup();
        this.id = msg.getId();
        this.oldX = x;
        this.oldY = y;
        this.width = ResourceMgr.goodtankU.getWidth();
        this.height = ResourceMgr.goodtankU.getHeight();
        this.rect = new Rectangle(x, y, width, height);
    }

    // private Random random = new Random();


    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        //this.group = Group.BAD;
        this.group = group;
        this.oldX = x;
        this.oldY = y;
        this.width = ResourceMgr.goodtankU.getWidth();
        this.height = ResourceMgr.goodtankU.getHeight();
        this.rect = new Rectangle(x, y, width, height);
    }

    public void paint(Graphics g) {
        if (!this.isLive()) {
            return;
        }
        move();
        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badtankL : ResourceMgr.goodtankL, x, y, null);
                //g.drawImage(ResourceMgr.badtankL,x,y,null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badtankU : ResourceMgr.goodtankU, x, y, null);
                //g.drawImage(ResourceMgr.badtankU,x,y,null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badtankR : ResourceMgr.goodtankR, x, y, null);
                //g.drawImage(ResourceMgr.badtankR,x,y,null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD) ? ResourceMgr.badtankD : ResourceMgr.goodtankD, x, y, null);
                //g.drawImage(ResourceMgr.badtankD,x,y,null);
                break;
        }

    }

    @Override
    public boolean islive() {
        return this.live;
    }


    public void move() {
        if (!moving) {
            return;
        }

        oldX = x;
        oldY = y;

        switch (dir) {
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

        boundsCheck();

        //敌人坦克方向随机
      //  randomDir();

        rect.x = x;
        rect.y = y;
//        if (r.nextInt(100) > 90) {
//            fire();
//        }
    }

    private void boundsCheck() {
        if (x < 5 || y < 50 || x + width > TankFrame.GAME_WIDTH || y + height > TankFrame.GAME_HEIGHT) {
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
        if (r.nextInt(100) > 90) {
            this.dir = Dir.randomDir();
        }
    }


    private void fire() {

        int bx = x + ResourceMgr.goodtankD.getWidth() / 2 - ResourceMgr.bulletU.getWidth() / 2;
        int by = y + ResourceMgr.goodtankD.getHeight() / 2 - ResourceMgr.bulletU.getHeight() / 2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bx, by, dir, group));
    }

    public void die() {
        this.setLive(false);
        // TankFrame.INSTANCE.addExplode(new Explode(x,y));
        TankFrame.INSTANCE.getGm().add(new Explode(x, y));
    }


}
