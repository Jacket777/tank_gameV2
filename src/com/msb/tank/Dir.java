package com.msb.tank;

import java.util.Random;

/**
 * 坦克运动的方向
 */
public enum Dir {
    L,U,R,D;

    private static Random r = new Random();

    public static Dir randomDir(){
        return values()[r.nextInt(Dir.values().length)];
    }
}
