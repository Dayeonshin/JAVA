/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 *
 * @author dayeonshin
 */

public class final1 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DrawPointUI ui = new DrawPointUI("My Window");
		ui.run();
	}    
}

class DrawPointUI extends JFrame{
    public DrawPointUI(String title) {
	super(title);
	initUI();
    }
    private void initUI() {
	this.setSize(400, 400);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	GridPanel panel = new GridPanel();
	this.add(panel);
    }
    public void run() {
	this.setVisible(true);
    }
}

class GridPanel extends JPanel{
    private int screen_w = 0;
    private int screen_h = 0;
    private int circleSize = 5;
    private Graphics graphic = null;
    private List<MouseEvent> pointers = null;
    public GridPanel() {
        this.pointers = new LinkedList<MouseEvent>();
        this.addMouseListener(new MouseAdapterEx(this));					
    }
	
    class MouseAdapterEx extends MouseAdapter{
        private GridPanel parent;
        public MouseAdapterEx(GridPanel parent) {
            this.parent = parent;
        }
        @Override
        public void mousePressed(MouseEvent e) {				
            if (e.getButton() == MouseEvent.BUTTON1) {				
                this.parent.pointers.add(e);
                this.parent.repaint();	
            } else {
                this.parent.pointers.clear();
                this.parent.repaint();				
            }
        }
    }
	
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	this.graphic = g;
	this.screen_w = this.getSize().width;
	this.screen_h = this.getSize().height;
		
	drawHLine();
	drawVLine();
	drawCirle();
	drawLine();
    }
	
    public void drawVLine() {
	int width = this.screen_w / 10;	
	graphic.setColor(Color.GRAY);
	for (int i = width; i < this.screen_w; i += width)
            graphic.drawLine(i, 0, i, this.screen_h);
    }
	
    public void drawHLine() {
	int height = this.screen_h / 10;	
	graphic.setColor(Color.GRAY);
	for (int i = height; i < this.screen_h; i += height)
            graphic.drawLine(0, i, this.screen_w, i);
    }
	
    public void drawCirle() {
	graphic.setColor(Color.RED);		
	for (MouseEvent e : this.pointers) {
            graphic.fillOval(e.getX() - 5, e.getY() - 5, circleSize, circleSize);
            String s = "(" + e.getX() + "," + e.getY() + ")";
            graphic.drawString(s, e.getX()+5, e.getY()+2);
	}
    }
	
    public void drawLine() {
	NumberFormat formatter = new DecimalFormat("#0.00");
	for (int i = 1; i < this.pointers.size(); i++) {
            MouseEvent preEvent = this.pointers.get(i - 1);
            MouseEvent event = this.pointers.get(i);
            double x = Math.pow(event.getX() - preEvent.getX(), 2);
            double y = Math.pow(event.getY() - preEvent.getY(), 2);
            double dist = Math.sqrt(x+y);
            graphic.drawLine(preEvent.getX(), preEvent.getY(), event.getX(), event.getY());
            graphic.drawString(formatter.format(dist), (preEvent.getX() + event.getX()) / 2, (preEvent.getY() + event.getY()) / 2);
	}
    }
}