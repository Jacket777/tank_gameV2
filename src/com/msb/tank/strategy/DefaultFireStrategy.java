package com.msb.tank.strategy;

import com.msb.tank.*;

public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Player t) {
        int bx = t.getX()+ ResourceMgr.goodtankD.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int by = t.getY()+ ResourceMgr.goodtankD.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bx,by,t.getDir(),t.getGroup()));

    }
}
