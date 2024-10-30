package com.msb.tank.chainofrespon;

import com.msb.tank.AbstractGameObject;

import java.io.Serializable;

/**
 * 碰撞规则
 */
public interface Collider extends Serializable {
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
