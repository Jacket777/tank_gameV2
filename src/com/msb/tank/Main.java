package com.msb.tank;



import java.awt.*;
import java.util.concurrent.TimeUnit;
import com.msb.tank.net.Client;

/**
 * 程序的入口
 * -----------> x
 *
 * */
public class Main {
    public static void main(String[] args) throws Exception {

        TankFrame.INSTANCE.setVisible(true);
        new Thread(()->{
            while(true){
                try {
                    // TimeUnit.MICROSECONDS.sleep(25);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //repaint---->update---->paint
                TankFrame.INSTANCE.repaint();
            }
        }).start();

       Client.INSTANCE.connect();
    }
}
