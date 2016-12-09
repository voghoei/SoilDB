package General;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class Counter {

   public static void main(String[] args)throws ClassNotFoundException, SQLException  {
         BufferedReader br = null;
        
        BufferedWriter writer = null;

        try {

            String CurrentLine;
            writer = new BufferedWriter(new FileWriter("F:\\Result.csv"));

            for (int i = 255; i>=1; i--){
                
                writer.write("*"+i+"*\n");

            }
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
