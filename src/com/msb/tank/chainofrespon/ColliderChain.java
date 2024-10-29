package com.msb.tank.chainofrespon;
import com.msb.tank.AbstractGameObject;
import com.msb.tank.PropertyMgr;

import java.util.ArrayList;
import java.util.List;
public class ColliderChain implements Collider{
    private List<Collider> colliders;

    public ColliderChain(){
        colliders = new ArrayList<>();
        initColliders();
    }

    private void initColliders() {
        String[]collidersName = PropertyMgr.get("colliders").split(",");
        for(String name: collidersName){
            try {
                Class clazz = Class.forName("com.msb.tank.chainofrespon."+name);
                Collider collider = (Collider)(clazz.getConstructor().newInstance());
                colliders.add(collider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean collide(AbstractGameObject go1, AbstractGameObject go2){
        for(Collider c: colliders){
            if(!c.collide(go1,go2)){
                return false;
            }
        }
        return true;
    }
}
