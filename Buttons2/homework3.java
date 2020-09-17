/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dayeonshin
 */

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class homework3 extends Thread{
    static JButton button;
    static int col = 2;
    static int row = 4;     
    static int numBtn = 9;   
    static ArrayList<JButton> buttonList = new ArrayList<JButton>();   
    static Thread thread;
    static ArrayList<Thread> threadList = new ArrayList<Thread>();        
    static volatile boolean flag = true;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Button color changer");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(row, col));
        buttonGenerator(panel);
        frame.add(panel);  
        frame.setVisible(true);
    }
    
    static class ButtonPress implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            int i = buttonList.indexOf(ae.getSource());     
            JButton chosenJb = buttonList.get(i);
            Thread chosenTh = threadList.get(i);
            JButton action = (JButton)ae.getSource();
            if (action == chosenJb){
                chosenTh.interrupt();
            }
        }  
    }
            
    static Color randColorGenerator(){
        Random randInt = new Random();
        int r = randInt.nextInt(256);
        int g = randInt.nextInt(256);
        int b = randInt.nextInt(256);
        Color color = new Color(r, g, b);
        return color;
    }
    
    static void backgroundColorSetter(JButton button){
        button.setBackground(randColorGenerator()); 
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
    }
    
    static void buttonGenerator(JPanel panel){
        for (int i = 1; i < numBtn; i++){
            button = new JButton("Button #"+i);
            backgroundColorSetter(button);
            buttonList.add(button);
            panel.add(button);
            button.addActionListener(new ButtonPress());
            threadGenerator(button);
            }
    } 

    static void threadGenerator(JButton button){
            thread = new Thread(){
                public void run(){
                    for (int i = 1; i < numBtn; i++){
                        while (flag){
                            try{
                                sleep(1000);
                                backgroundColorSetter(button);
                            }
                            catch (InterruptedException ex){
                                return;
                            }
                        }
                    }
                }
            };
            thread.start();
            threadList.add(thread);
    }
}