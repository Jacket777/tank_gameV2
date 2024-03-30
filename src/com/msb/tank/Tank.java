package com.msb.tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.msb.tank.Dir.L;

public class Tank {
    private int x,y;//坦克的位置
    private Dir dir; //坦克的方向
    public static final int SPEED = 5;
    private boolean bL,bU,bR,bD;
    private boolean moving = false;
    private Group group;


    public Tank(int x, int y,Dir dir,Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    public void paint(Graphics g) {
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
        System.out.println("键盘被按下------");
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
        System.out.println("键盘被弹起------");
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
        //setMainDir();
    }

    private void fire() {
        new Bullet(x,y,dir,group);
    }
}
