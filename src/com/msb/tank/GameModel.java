package com.msb.tank;

import com.msb.tank.chainofrespon.ColliderChain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * model - view
 */
public class GameModel implements Serializable {
    private Player myTank;
   // private Tank enemy;
    private ColliderChain chain = new ColliderChain();
    private List<AbstractGameObject> objects;

    public GameModel(){
        initGameObjects();
    }

    private void initGameObjects() {
        myTank = new Player(100,100,Dir.U,Group.GOOD);
       // enemy = new Tank(200,200,Dir.D,Group.BAD);
//        bullets = new ArrayList<>();
//        tanks = new ArrayList<>();
//        explodes = new ArrayList<>();
        int tankCount = Integer.parseInt(PropertyMgr.get("initTankCount"));
        objects = new ArrayList<>();

        for (int i = 0; i < tankCount ; i++) {
            //tanks.add(new Tank(100+50*i, 200,Dir.D,Group.BAD));
            this.add(new Tank(100+50*i, 200,Dir.D,Group.BAD));
        }
       // this.add(new Wall(300,200,400,20));
    }


    public void add(AbstractGameObject go){
        objects.add(go);
    }


    public void paint(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("OBJ: "+ objects.size(),10,50);
        g.setColor(c);
        myTank.paint(g);


        for (int i = 0; i < objects.size(); i++) {
            if(!objects.get(i).islive()){
                objects.remove(i);
                break;
            }
            AbstractGameObject go1 = objects.get(i);
            for(int j = 0; j < objects.size();j++){
                AbstractGameObject go2 = objects.get(j);
                chain.collide(go1,go2);
//                collider.collide(go1,go2);
//                wallCollider.collide(go1,go2);
            }

            if(objects.get(i).islive()){
                objects.get(i).paint(g);
            }
        }
    }


    public Player getMyTank(){
        return myTank;
    }


}
