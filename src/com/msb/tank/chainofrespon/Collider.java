package com.msb.tank.chainofrespon;

import com.msb.tank.AbstractGameObject;

/**
 * 碰撞规则
 */
public interface Collider {
    public void collide(AbstractGameObject go1, AbstractGameObject go2);
}
