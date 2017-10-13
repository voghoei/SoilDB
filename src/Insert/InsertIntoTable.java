/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Insert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author voghoei
 */
public class InsertIntoTable {
    
     public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br ;
        ResultSet rs ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


        int counter = 0;
        int dublication = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("C:\\Users\\voghoei\\Desktop\\tables Fix\\EXPDetailFix.csv"));
            //br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String f0 = countCurrentLine.split(",")[0];
                String f1 = countCurrentLine.split(",")[1];
                String f2 = countCurrentLine.split(",")[2];
                String f3 = countCurrentLine.split(",")[3];
                String f4 = countCurrentLine.split(",")[4];
                String f5 = countCurrentLine.split(",")[5];
//                String f6 = countCurrentLine.split(",")[6];
//                String f7 = countCurrentLine.split(",")[7];
//                String f8 = countCurrentLine.split(",")[8];
//                String f9 = countCurrentLine.split(",")[9];
//                String f10 = countCurrentLine.split(",")[10];
//                String f11 = countCurrentLine.split(",")[11];
//                String f12 = countCurrentLine.split(",")[12];             
                

                
                String Query = "SET IDENTITY_INSERT Soil.dbo.ExperimentDetails ON; insert into ExperimentDetails( ID,ExperimentType_ID,location_ID,Rep_ID,Plot_ID,Year,Note,Treatment_ID) values (" + f0 + "," + f1 + "," + f2 + ","+ f3 + ","+ f4 + ","+ f5 + ",null,null)";
                statement.executeUpdate(Query);
                    counter++;
                
            }
            System.out.println(counter + " Rows Data Inserted");
            System.out.println(dublication + " Rows Data Dublicated");

        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.err.println("Problem Connecting!  " + e);
        }

    }
    
}
