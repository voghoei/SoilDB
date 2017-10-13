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
        int counter = 1;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= CAGT-SAHAR-D\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("D:\\Soil Files\\2016\\Sampling\\Sample 2\\checked 67 .csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String plot = countCurrentLine.split(",")[0];
                String code = countCurrentLine.split(",")[1];  
                String note = countCurrentLine.split(",")[2];
                String Query = "";
               
                if (note.length()>1){
                    Query = "update FieldBook set Value='" + code + "' ,Note = '"+note+"'  where [ID] = (SELECT ID FROM [Soil].[dbo].[View_FieldBook_Data] where Phenotype_ID in (6) and Destination='"+plot+"')";
                
                }
                else{
                    Query = "update FieldBook set Value='" + code + "' where [ID] = (SELECT ID FROM [Soil].[dbo].[View_FieldBook_Data] where Phenotype_ID in (6) and Destination='"+plot+"')";
                
                }
                //System.out.println( " plot  = "+plot);        
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
