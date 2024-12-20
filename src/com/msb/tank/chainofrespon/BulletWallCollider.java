package com.msb.tank.chainofrespon;

import com.msb.tank.AbstractGameObject;
import com.msb.tank.Bullet;
import com.msb.tank.Tank;
import com.msb.tank.Wall;

public class BulletWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Bullet && go2 instanceof Wall){
            Bullet b = (Bullet) go1;
            Wall w = (Wall) go2;
            if(b.isLive()){
                if(b.getRect().intersects(w.getRect())){
                    b.die();
                    return false;
                }
            }
        }else if(go1 instanceof Wall && go2 instanceof Bullet){
           return collide(go2,go1);
        }

        return true;
    }
}
