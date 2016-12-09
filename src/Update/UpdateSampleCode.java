package Update;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
public class UpdateSampleCode {

     public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br ;
        ResultSet rs ; 
        int counter = 0;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("E:\\2016 winter data\\Phones\\Sampling\\Sample 2\\All chenges.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String code1 = countCurrentLine.split(",")[0];
                String code2 = countCurrentLine.split(",")[1];                
                String Query = "";
               
                Query = "update FieldBook set Value='" + code2 + "' where Value ='"+code1+"'";
                               
                if (statement.executeUpdate(Query) == 1) {
                    counter++;
                }
            }
            System.out.println(counter + " Rows Data Updated");
            conn.close();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.err.println("Problem Connecting!  " + e);
        }

    }
    
}
