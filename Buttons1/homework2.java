/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homework2;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/**
 *
 * @author dayeonshin
 */
public class homework2 {

    /**
     * @param args the command line arguments
     */
    static JButton button;
    static int col = 2;
    static int row = 4;     
    static int numBtn = 9;   
    static ArrayList<JButton> buttonList = new ArrayList<JButton>();
 
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
            
            for (int j = 0; j < numBtn - 1; j++){
                if (j != i)
                    backgroundColorSetter(buttonList.get(j));   
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
        }
    } 
}    