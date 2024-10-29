package com.msb.tank;

import java.awt.*;

/**
 *  子弹类
 */
public class Wall extends AbstractGameObject{
    private int x,y;
    private int width, height;
    private Rectangle rect;



    public Wall(int x, int y, int width, int height){
        this.x = x;
        this.y =y;
        this.width = width;
        this.height = height;
        this.rect = new Rectangle(x,y,width,height);
    }



    public void paint(Graphics g){
         Color c = g.getColor();
         g.setColor(Color.DARK_GRAY);
         g.fillRect(x,y,width,height);
         g.setColor(c);
//         Color c1 = g.getColor();
//         g.setColor(Color.YELLOW);
//         g.drawRect(rect.x, rect.y, rect.width, rect.height);
//         g.setColor(c1);
    }

    @Override
    public boolean islive() {
        return true;
    }


    public Rectangle getRect() {
        return rect;
    }
}
