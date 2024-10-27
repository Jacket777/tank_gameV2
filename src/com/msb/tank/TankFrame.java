package com.msb.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class TankFrame extends Frame {
  private Player myTank;
  private Tank enemy;
  public static final int GAME_WIDTH = 800;
  public static final int GAME_HEIGHT = 600;
  private Image offScreenImage = null;  //利用双缓冲解决图片闪烁问题
  private List<Bullet> bullets;
  private List<Tank>tanks;

  private List<Explode> explodes;

  public static final TankFrame INSTANCE = new TankFrame();

    private TankFrame(){
        this.setTitle("Tank War V0.2");
        this.setLocation(400,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener());//添加监听器

        initGameObjects();

    }

    private void initGameObjects() {
        myTank = new Player(100,100,Dir.U,Group.GOOD);
        enemy = new Tank(200,200,Dir.D,Group.BAD);
        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        explodes = new ArrayList<>();
        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));

        for (int i = 0; i < tankCount ; i++) {
            tanks.add(new Tank(100+50*i, 200,Dir.D,Group.BAD));
        }
    }


    public void add(Bullet bullet){
        bullets.add(bullet);
    }


    public void add(AbstractGameObject go){

    }


    /**
     * awt 自动调用该方法，重新绘制时会被调用
     * g 是awt的参数
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullets:"+bullets.size(),10,50);
        g.drawString("enemies:"+tanks.size(),10,70);
        g.drawString("explodes:"+explodes.size(),10,90);
        g.setColor(c);
        myTank.paint(g);
        for(int i = 0; i < tanks.size();i++){
            if(!tanks.get(i).isLive()){
                tanks.remove(i);
            }else{
                tanks.get(i).paint(g);
            }
        }
       // enemy.paint(g);
        //一个个画出子弹
        for (int i = 0; i < bullets.size() ; i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWithTank(tanks.get(j));
            }
          //  bullets.get(i).collideWithTank(enemy);
            if(!bullets.get(i).isLive()){
                bullets.remove(i);
            }else{
                bullets.get(i).paint(g);
            }
        }


        for(int i = 0; i < explodes.size();i++){
            if(!explodes.get(i).isLive()){
                explodes.remove(i);
            }else{
                explodes.get(i).paint(g);
            }
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

    public void addExplode(Explode explode) {
       this.explodes.add(explode);
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
