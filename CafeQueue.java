/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final3;
import java.util.LinkedList;

/**
 *
 * @author dayeonshin
 */
public class CafeQueue {
    
    private LinkedList<String> list = null;
    
    CafeQueue(){
        this.list = new LinkedList<String>();
    }
    
    public synchronized void enterQueue(String str){
        this.list.offer(str);
    }
    public synchronized String serveCustomer(){
        return this.list.poll();
    }
}
