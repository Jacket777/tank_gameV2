package com.msb.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TankFrame extends Frame {
   Tank myTank;

    public TankFrame(){
        this.setTitle("Tank War v0.2");
        this.setLocation(400,100);
        this.setSize(800,600);
        this.addKeyListener(new TankKeyListener());
        myTank = new Tank(100,100,Dir.U);

    }

    /**
     * awt 自动调用该方法，重新绘制时会被调用
     * g 是awt的参数
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        myTank.paint(g);
        System.out.println("paint");

        super.paint(g);
    }

    /**
     * 内部类，处理键盘监听方法
     */
    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);

        }

        @Override
        public void keyReleased(KeyEvent e) {
             myTank.keyReleased(e);
        }
    }



}
