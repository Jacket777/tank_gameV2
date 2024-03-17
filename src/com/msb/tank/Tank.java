package com.msb.tank;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    private int x,y;//坦克的位置
    private Dir dir; //坦克的方向
    public static final int SPEED = 5;
    private boolean bL,bU,bR,bD;

    public Tank(int x, int y,Dir dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void paint(Graphics g) {
        g.fillRect(x,y, 50,50);
        move();
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("键盘被按下------");
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_LEFT:
                bL = true;
                dir = Dir.L;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                dir = Dir.U;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                dir = Dir.R;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                dir = Dir.D;
                break;
        }
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

    public void keyReleased(KeyEvent e) {
        System.out.println("键盘被弹起------");
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_LEFT:
                dir = Dir.L;
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
    }
}
