package com.msb.tank.net;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    private TextArea ta = new TextArea();
    private TextField tf = new TextField();
    private Client c = null;
    public static final ClientFrame INSTANCE = new ClientFrame();

    private ClientFrame(){
        this.setSize(300,400);
        this.setLocation(400,20);
        this.add(ta,BorderLayout.CENTER);
        this.add(tf,BorderLayout.SOUTH);
        this.setTitle(" client chat");
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               c.send(tf.getText());
               ta.setText(ta.getText()+"\r\n"+tf.getText());
                tf.setText("");
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                c.closeConnection();
                System.exit(0);
                super.windowClosing(e);
            }
        });
    }

    public void connectToServer() throws Exception {
       // c = new Client();
        c = Client.INSTANCE;
        c.connect();
    }

    public static void main(String[] args) throws Exception {
        ClientFrame cf = ClientFrame.INSTANCE;
        cf.setVisible(true);
        cf.connectToServer();
    }

    public void updateText(String str) {
        ta.setText(ta.getText()+str+"\r\n");
    }
}
