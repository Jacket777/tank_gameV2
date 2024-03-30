package com.msb.tank;

import java.awt.*;

/**
 *  子弹类
 */
public class Bullet {
    private int x;
    private int y;
    private Dir dir;
    private Group group;
    public static final int SPEED = 10;

    public Bullet(int x, int y, Dir dir,Group group){
        this.x = x;
        this.y =y;
        this.dir = dir;
        this.group = group;
    }



    public void paint(Graphics g){
            switch(dir){
                case L:
                    g.drawImage(ResourceMgr.bulletL,x,y,null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.bulletU,x,y,null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.bulletR,x,y,null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.bulletD,x,y,null);
                    break;
            }

       move();
    }

    public void move(){
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

}
