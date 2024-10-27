package com.msb.tank;

import java.awt.*;
import java.awt.event.KeyEvent;

import static com.msb.tank.Dir.L;

public class TankV2 {
    private int x,y;//坦克的位置
    private Dir dir; //坦克的方向
    public static final int SPEED = 5;
    private boolean bL,bU,bR,bD;
    private boolean moving = false;
    private Group group;
    private boolean live = true;

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




    public TankV2(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    public void paint(Graphics g) {
        if(!this.isLive()) {
         return;
        }
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

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                dir = Dir.U;
                break;
            case KeyEvent.VK_RIGHT:
                dir = Dir.R;
                break;
            case KeyEvent.VK_DOWN:

                dir = Dir.D;
                break;
        }
        setMainDir();
    }

    /**
     * 设置坦克的方向
     */
    private void setMainDir() {
        if(!bL&&!bU&&!bR&&!bD){
            moving = false; //all key is up so tank is stop
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



    public void keyReleased(KeyEvent e) {
        moving = false;
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_LEFT:
                dir = L;
                break;
            case KeyEvent.VK_UP:
                dir = Dir.U;
                break;
            case KeyEvent.VK_RIGHT:
                dir = Dir.R;
                break;
            case KeyEvent.VK_DOWN:
                dir = Dir.D;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }

    }

    private void fire() {
        int bx = x+ ResourceMgr.goodtankD.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int by = y+ ResourceMgr.goodtankD.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.add(new Bullet(bx,by,dir,group));
    }

    public void die() {
        this.setLive(false);
    }
}
