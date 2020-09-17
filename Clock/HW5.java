/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

/**
 *
 * @author dayeonshin
 */

public class HW5 extends JFrame {
    Dial clockDial;
    static int hour;
    static int minute;
    static int second;
    
    public static void getTime() {

        try (Socket socket = new Socket("time-a-g.nist.gov", 13)) {
            int c;
            InputStream in = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            StringBuilder data = new StringBuilder();
 
            while ((c = reader.read()) != -1) {
                data.append((char)c);
            }

            hour = Integer.parseInt(Character.toString(data.charAt(16)) + Character.toString(data.charAt(17)));
            minute = Integer.parseInt(Character.toString(data.charAt(19)) + Character.toString(data.charAt(20)));
            second = Integer.parseInt(Character.toString(data.charAt(22)) + Character.toString(data.charAt(23)));
            
            System.out.println(data);  
            System.out.println("Hour: " + hour);
            System.out.println("Minute: " + minute);
            System.out.println("Second: " + second);          
        }
        catch (IOException ex) {
            System.out.println("ERROR");
        }  
    }

    public HW5() {
        setSize(500,510);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        clockDial = new Dial(this);
        getContentPane().add(clockDial);
        Clock.setPriority(Clock.getPriority()+3);
        Clock.start();
    }

    public static void main(String args[]) {
        new HW5().setVisible(true);
        getTime();
    }

    Thread Clock = new Thread() {
        int newMinute, newSecond;

        public void run() {
            while(true) {
                newMinute = (minute+(second+1)/60)%60;
                newSecond = (second+1)%60;
                hour = (hour+(minute+(second+1)/60)/60)%12;
                minute = newMinute;
                second = newSecond;

                try {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException ex) {
                }

                clockDial.repaint();
            }
        }
    };
}

class Dial extends JPanel {
    HW5 parent;
    
    public Dial(HW5 pt){
        setSize(500,510);
        parent = pt;
    }
   
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.fillOval(5, 5, 480, 480);
        g.setColor(Color.BLACK);
        g.fillOval(10, 10, 470, 470);
        g.setColor(Color.WHITE);
        g.fillOval(237, 237, 15, 15);
        g.setFont(g.getFont().deriveFont(Font.BOLD,35));

        for(int i=1; i<=12; i++)
            g.drawString(Integer.toString(i),240-(i/12)*11+(int)(210*Math.sin(i*Math.PI/6)),253-(int)(210*Math.cos(i*Math.PI/6)));

        int x, y;
        int xp[] = new int[3];
        int yp[] = new int[3];
        double hr = (double)Math.PI/6;
        double minsec = (double)Math.PI/30;
 
        x = 245 + (int)(210 * Math.sin(parent.second*minsec));
        y = 245 - (int)(210 * Math.cos(parent.second*minsec));
        g.drawLine(245, 245, x, y);

        x = 245 + (int)(190 * Math.sin(parent.minute*minsec));
        y = 245 - (int)(190 * Math.cos(parent.minute*minsec));
        xp[0] = 245;
        xp[1] = x+3;
        xp[2] = x-3;
        yp[0] = 245;
        yp[1] = y+3;
        yp[2] = y-3;
        g.fillPolygon(xp, yp, 3);

        x = 245 + (int)(160 * Math.sin(parent.hour*hr+parent.minute*Math.PI/360));
        y = 245 - (int)(160 * Math.cos(parent.hour*hr+parent.minute*Math.PI/360));
        xp[1] = x+10;
        xp[2] = x-10;
        yp[1] = y-10;
        yp[2] = y+10;
        g.fillPolygon(xp, yp, 3);
    }
}