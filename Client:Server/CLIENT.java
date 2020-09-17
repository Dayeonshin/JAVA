/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dayeonshin
 */

package CLIENT;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CLIENT extends JPanel implements ActionListener {
    
    protected JPanel inner;
    protected JPanel outer;
    protected JPanel info;
    protected JTextField textbox;
    protected JButton sendbt;
    protected JLabel instructions;
    protected static JTextArea chatbox;
    static PrintStream output;
    final static Color black = new Color(0, 0, 0);
    final static Color white = new Color(255, 255, 255);
    final static Color grey = new Color(204, 204, 204);
    
    public CLIENT() {
        inner = new JPanel();
        inner.setLayout(new BorderLayout());

        textbox = new JTextField(34);
        textbox.setToolTipText("TYPE");
        textbox.setForeground(black);
        textbox.addActionListener(this);
        inner.add(textbox, BorderLayout.CENTER);
        
        sendbt = new JButton("SEND");
        sendbt.setForeground(black);
        sendbt.setOpaque(true);
        sendbt.addActionListener(e -> actionSend(e));
        inner.add(sendbt, BorderLayout.EAST);
        
        instructions = new JLabel();
        instructions.setText("TYPE USER NAME THEN PRESS 'SEND' BUTTON TO START THE CHAT");
        instructions.setForeground(Color.black); 
        inner.add(instructions, BorderLayout.NORTH);

        info = new JPanel();
        info.setLayout(new BorderLayout());
        inner.add(info, BorderLayout.SOUTH);

        chatbox = new JTextArea(26, 34);
        chatbox.setForeground(black);

        outer = new JPanel();
        outer.setLayout(new BorderLayout());
        outer.setBackground(white);
        outer.add(inner, BorderLayout.SOUTH);
        outer.add(chatbox, BorderLayout.NORTH);
        
        add(outer);
    }

     public static void GUI() {
        JFrame jf = new JFrame("CHAT");
        jf.setSize(500, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(new CLIENT());
        jf.setVisible(true);
    }
     
    public void actionSend(ActionEvent e) {
        String message = textbox.getText();
        output.print(message + "\r\n");
        textbox.setText("");
    }
    
    public void actionPerformed(ActionEvent e) {
        String message = textbox.getText();
        output.print(message + "\r\n");
        textbox.setText("");
    }  

    public static void main(String[] args) {
        GUI();

        try {
            String str = "";
            Socket socket = new Socket("LOCALHOST", 5190);
            Scanner input = new Scanner(socket.getInputStream());
            output = new PrintStream(socket.getOutputStream());

            while (!str.equals("EXIT")){
                str = input.nextLine();
                chatbox.setText(chatbox.getText() + str + "\n");
            }
        }
        catch (IOException ex) {
        
        }
    }
}