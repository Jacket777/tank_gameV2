package com.msb.tank;

import java.awt.*;
import java.io.Serializable;

public abstract class AbstractGameObject implements Serializable {
    public abstract  void paint(Graphics g);

    public abstract boolean islive() ;
}
