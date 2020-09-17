/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dayeonshin
 */

package SERVER;
import java.io.*;
import java.net.*;
import java.util.*;

public class SERVER {
    static int PORT = 5190;
    static ArrayList<CONNECT> users = new ArrayList<CONNECT>();

    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(PORT);
            while (true) {
                Socket client = socket.accept(); 
                CONNECT CT = new CONNECT(client);
                users.add(CT);
                CT.start();
            }
        } catch(IOException ex) {
            System.out.println("NOT FOUND");
        }
    }
}

class CONNECT extends Thread {
    Socket user;
    String username;
    Scanner INPUT;
    PrintStream OUTPUT;

    public CONNECT(Socket CS) {
        user = CS;
        try {
            INPUT = new Scanner(CS.getInputStream());
            OUTPUT = new PrintStream(CS.getOutputStream());
            username = INPUT.nextLine();
            
        } catch (IOException ex) {            

        }
    }

    public void Message(String message, String username) {
        for (CONNECT con : SERVER.users) {
            con.OUTPUT.println(username + ": " + message);
        }
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                String message = INPUT.nextLine();
                if (message.equals("EXIT")) 
                    break;
                Message(message, username);
            }
            user.close();
        } catch (IOException ex) {

        }
    }
}     
