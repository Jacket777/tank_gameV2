package com.msb.tank.strategy;

import com.msb.tank.*;

public class FourDirFireStrategy implements FireStrategy{
    @Override
    public void fire(Player p) {
        int bx = p.getX()+ ResourceMgr.goodtankD.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int by = p.getY()+ ResourceMgr.goodtankD.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;

        Dir[]dirs = Dir.values();
        for(Dir d:dirs){
            TankFrame.INSTANCE.getGm().add(new Bullet(bx,by,d,p.getGroup()));
        }
    }
}
