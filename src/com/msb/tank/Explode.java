package com.msb.tank;

import java.awt.*;

/**
 *  爆炸类
 */
public class Explode extends AbstractGameObject{
    private int x, y;
    private int width,height;
    private int step;//画了第几步
    private boolean live = true; //爆炸是否结束
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean islive) {
        this.live = islive;
    }


    

    public Explode(int x, int y){
        this.x = x;
        this.y =y;
        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
    }



    public void paint(Graphics g){
        if(!live){
            return;
        }
        g.drawImage(ResourceMgr.explodes[step],x,y,null);
        step++;
        if(step >= ResourceMgr.explodes.length){
            this.die();
        }

    }

    private void die() {
        this.live = false;
    }


}
