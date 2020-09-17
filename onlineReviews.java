/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final2;

import java.sql.*;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dayeonshin
 */
public class final2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection conn = null;
        try{
            //Class.forName("org.mariadb.jdbc.Driver").newInstance();
            String url = "localhost";
            String dbuser="CS3913";
            String password = "GettingAnA+";
            
            conn = DriverManager.getConnection(url, dbuser, password);
            Statement s = conn.createStatement();   
            
            String query = "Select * from Products";
            String ratingQuery = "Select Rating from Reviews where PID=?;";
            ResultSet rs = s.executeQuery(query);  
            
            List<Map> productList = new LinkedList<Map>();
            while (rs.next()){
                int PID = rs.getInt("PID");
                String ProductName = rs.getString("ProductName");
                Map<String, Object> product = new HashMap<String, Object>();
                product.put("PID", PID);
                product.put("ProductName", ProductName);
                PreparedStatement pstmt = conn.prepareStatement(ratingQuery);
                pstmt.setInt(1, PID);
                ResultSet rs2 = pstmt.executeQuery();
                int totalRating = 0;
                int totalCnt = 0;

                while (rs2.next()){                
                    int Rating = rs2.getInt("Rating");                
                    totalRating += Rating;
                    totalCnt ++;
                }
                double avg = (double)totalRating / (double)totalCnt;
                product.put("AVG", avg);
                productList.add(product);
                rs2.close();
            }

            Map<String, Object> tmp = null;
            for(int i= productList.size() - 1 ; i > 0 ; i--){
                for(int j = 0; j < i; j++){
                    if((double)productList.get(j).get("AVG") > (double)productList.get(i).get("AVG")){
                        tmp = productList.get(j);
                        productList.set(j, productList.get(i));
                        productList.set(j+1, tmp);
                    }
                }  
            }
            Collections.reverse(productList);
            for(Map<String, Object> product : productList){
                System.out.println(product.get("ProductName"));
            }
            
            rs.close();
            s.close();
            conn.close();
        }
        catch (Exception e){System.out.println("Error: "+e.toString());}
    }
}
