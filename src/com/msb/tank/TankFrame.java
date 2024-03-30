package com.msb.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {
  private Tank myTank,enemy;
  private static final int GAME_WIDTH = 800;
  private static final int GAME_HEIGHT = 600;
  private Image offScreenImage = null;  //利用双缓冲解决图片闪烁问题
  private List<Bullet> bullets;
  public static final TankFrame INSTANCE = new TankFrame();

    private TankFrame(){
        this.setTitle("Tank War v0.2");
        this.setLocation(400,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener());//添加监听器
        myTank = new Tank(100,100,Dir.U,Group.GOOD);
        enemy = new Tank(200,200,Dir.D,Group.BAD);
        bullets = new ArrayList<>();
      //  bullet = new Bullet(100,100,Dir.D,Group.BAD);

    }


    public void add(Bullet bullet){
        bullets.add(bullet);
    }


    /**
     * awt 自动调用该方法，重新绘制时会被调用
     * g 是awt的参数
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        //
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullets:"+bullets.size(),10,50);
        g.setColor(c);
        myTank.paint(g);
       // System.out.println("paint");
        enemy.paint(g);
        //一个个画出子弹
        for (int i = 0; i < bullets.size() ; i++) {
            bullets.get(i).paint(g);
        }

    }


    /**
     * 解决双闪烁问题
     * 先在内存里面画出来，再给到显卡中
      * @param g
     */

   @Override
   public void update(Graphics g){
       if(offScreenImage == null){
           offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
       }
       Graphics graphics = offScreenImage.getGraphics();
       Color c = graphics.getColor();
       graphics.setColor(Color.BLACK);
       graphics.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
       graphics.setColor(c);
       paint(graphics);
       g.drawImage(offScreenImage,0,0,null);


   }







    /**
     * 内部类，处理键盘监听方法
     */
    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
           // enemy.keyPressed(e);

        }

        @Override
        public void keyReleased(KeyEvent e) {
             myTank.keyReleased(e);
           //  enemy.keyReleased(e);
        }
    }



}
