package com.msb.tank.chainofrespon;

import com.msb.tank.AbstractGameObject;
import com.msb.tank.Bullet;
import com.msb.tank.Tank;
import com.msb.tank.Wall;

public class TankWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Tank && go2 instanceof Wall){
            Tank t = (Tank) go1;
            Wall w = (Wall) go2;
            if(t.isLive()){
                if(t.getRect().intersects(w.getRect())){
                   t.back();
                }
            }

        }else if(go1 instanceof Wall && go2 instanceof Bullet){
            collide(go2,go1);
        }

        return true;
    }
}
