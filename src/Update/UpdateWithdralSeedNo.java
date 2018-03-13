/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Update;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author voghoei
 */
public class UpdateWithdralSeedNo { 
     public static void main(String[] args) throws SQLException {
        String countCurrentLine;

        BufferedReader br = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();


            br = new BufferedReader(new FileReader("E:\\Soil\\2017\\Package info\\Final 2017 Summer field TRT.csv"));
            int counter = 0;
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Destination = countCurrentLine.split(",")[10]; 
                String Note="";
                Note = countCurrentLine.split(",")[13];     
                String SeedNo = countCurrentLine.split(",")[11];                            
                int Withdrawal_ID = 0;
                
                counter++;
                rs = statement.executeQuery("select Withdrawal_ID from [View_PackageWithdrawal_Full] where Destination ='"+Destination+"'");
                if (rs.next()) {
                    Withdrawal_ID = rs.getInt(1);
                    //System.out.println("update PackageWithdrawal set SeedRemove= " + SeedNo + ", Note=  '" + Note + "' where ID = "+Withdrawal_ID);
                    statement.executeUpdate("update PackageWithdrawal set SeedRemove= " + SeedNo + ", Note=  '" + Note + "' where ID = "+Withdrawal_ID);

                }               
            }
            System.out.println(counter + " Rows Data Updated");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }
     }
}

