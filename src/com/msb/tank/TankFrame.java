package com.msb.tank;

import com.msb.tank.chainofrespon.BulletTankCollider;
import com.msb.tank.chainofrespon.BulletWallCollider;
import com.msb.tank.chainofrespon.Collider;
import com.msb.tank.chainofrespon.ColliderChain;
import com.sun.deploy.net.MessageHeader;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.List;

import static com.msb.tank.Dir.L;

public class TankFrame extends Frame implements Serializable{
//  private Player myTank;
//  private Tank enemy;
  public static final int GAME_WIDTH = 800;
  public static final int GAME_HEIGHT = 600;
  private Image offScreenImage = null;  //利用双缓冲解决图片闪烁问题
//  private List<Bullet> bullets;
//  private List<Tank>tanks;
//
//  private List<Explode> explodes;
//
//  private List<AbstractGameObject>objects;
//  private List<Collider>colliders = new ArrayList<>();
    private GameModel gm = new GameModel();

  public static final TankFrame INSTANCE = new TankFrame();

    private TankFrame(){
        this.setTitle("Tank War V0.2");
        this.setLocation(400,100);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener());//添加监听器

       // initGameObjects();
    }



//    private void initGameObjects() {
//        myTank = new Player(100,100,Dir.U,Group.GOOD);
//        enemy = new Tank(200,200,Dir.D,Group.BAD);
////        bullets = new ArrayList<>();
////        tanks = new ArrayList<>();
////        explodes = new ArrayList<>();
//        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));
//        objects = new ArrayList<>();
//
//        for (int i = 0; i < tankCount ; i++) {
//            //tanks.add(new Tank(100+50*i, 200,Dir.D,Group.BAD));
//            this.add(new Tank(100+50*i, 200,Dir.D,Group.BAD));
//        }
//        this.add(new Wall(300,200,400,20));
//    }


//    public void add(Bullet bullet){
//        bullets.add(bullet);
//    }


//    public void add(AbstractGameObject go){
//
//        objects.add(go);
//    }

   private Collider collider = new BulletTankCollider();

    private Collider wallCollider = new BulletWallCollider();

   //    private ColliderChain chain = new ColliderChain();
    /**
     * awt 自动调用该方法，重新绘制时会被调用
     * g 是awt的参数
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        gm.paint(g);
//        Color c = g.getColor();
//        g.setColor(Color.WHITE);
//        g.drawString("OBJ: "+ objects.size(),10,50);
//        g.drawString("bullets:"+bullets.size(),10,50);
//        g.drawString("enemies:"+tanks.size(),10,70);
//        g.drawString("explodes:"+explodes.size(),10,90);
       // g.setColor(c);
       // myTank.paint(g);

//        for (int i = 0; i < objects.size(); i++) {
//            if(!objects.get(i).islive()){
//                objects.remove(i);
//                break;
//            }
//            AbstractGameObject go1 = objects.get(i);
//            for(int j = 0; j < objects.size();j++){
//                AbstractGameObject go2 = objects.get(j);
//               chain.collide(go1,go2);
////                collider.collide(go1,go2);
////                wallCollider.collide(go1,go2);
//            }
//
//            if(objects.get(i).islive()){
//                objects.get(i).paint(g);
//            }
//        }
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



    public GameModel getGm(){
      return this.gm;
    }

    /**
     * 内部类，处理键盘监听方法
     */
    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_S){
                save();
            }else if(key == KeyEvent.VK_L){
                load();
            }else{
                gm.getMyTank().keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyReleased(e);
        }
    }


    /**
     * 保存游戏当前元素
     */
    public void save(){
        ObjectOutputStream oos = null;
        try {
            File f = new File("E:/tank.dat");
            FileOutputStream fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gm);
            oos.flush();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null!=oos){
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**
     * 恢复上一次保存的游戏元素
     */
    public void load(){
        ObjectInputStream ois = null;
        try{
            File f = new File("E:/tank.dat");
            FileInputStream fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            this.gm = (GameModel) (ois.readObject());
        }catch (Exception e){
           e.printStackTrace();
        } finally{
            if(ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
