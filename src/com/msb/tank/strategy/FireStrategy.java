package com.msb.tank.strategy;

import com.msb.tank.Player;
import com.msb.tank.Tank;

import java.io.Serializable;

public interface FireStrategy extends Serializable {
    public void fire(Player p);
}
