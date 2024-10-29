package com.msb.tank.chainofrespon;

import com.msb.tank.AbstractGameObject;
import com.msb.tank.Bullet;
import com.msb.tank.Tank;

public class BulletTankCollider implements Collider {
    /**
     * 返回true时，此时应该继续下一个
     * @param go1
     * @param go2
     * @return
     */
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if (go1 instanceof Bullet && go2 instanceof Tank) {
            Bullet b = (Bullet) go1;
            Tank t = (Tank) go2;
            if (!b.isLive() || !t.isLive()) {
                return false;
            }
            if (b.getGroup() == t.getGroup()) {
                return true;
            }
            if (b.getRect().intersects(t.getRect())) {
                b.die();
                t.die();
                return false;
            }


        } else if (go1 instanceof Tank && go2 instanceof Bullet) {
            collide(go2, go1);
        }
        return true;
    }
}


