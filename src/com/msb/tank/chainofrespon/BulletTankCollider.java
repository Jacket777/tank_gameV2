package com.msb.tank.chainofrespon;

import com.msb.tank.AbstractGameObject;
import com.msb.tank.Bullet;
import com.msb.tank.Tank;

public class BulletTankCollider implements Collider{
    @Override
    public void collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Bullet && go2 instanceof Tank){
            Bullet b = (Bullet) go1;
            Tank t = (Tank) go2;
            b.collideWithTank(t);
        }else if(go1 instanceof Tank && go2 instanceof Bullet){
            collide(go2,go1);
        }
    }
}
