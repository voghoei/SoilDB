/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author voghoei
 */
public class Insert {
    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= CAGT-SAHAR-D\\SQLEXPRESS;databaseName=UGA_Sample;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("D:\\Soil Files\\2017\\Sampling\\Campus\\2-19-2018.csv"));
            
            while ((countCurrentLine = br.readLine()) != null) {
                String Name = countCurrentLine.split(",")[2].replaceAll(" ", "");
                String Barcode = countCurrentLine.split(",")[1].replaceAll(" ", "");
                String Note = "";//countCurrentLine.split(",")[2];
                int Sample_ID = 0;
                String Query = "";

                rs = statement.executeQuery("select ID from Sample where replace(Name,' ','') = replace('" + Name + "',' ','')");
                if (rs.next()) {
                    Sample_ID = rs.getInt(1);                    
                }
                else{
                    Query = "INSERT INTO [dbo].[Sample] (Name) VALUES('" + Name + "')";                    
                    statement.executeUpdate(Query);
                    
                    rs = statement.executeQuery("select ID from Sample where replace(Name,' ','') = replace('" + Name + "',' ','')");
                    if (rs.next()) {
                        Sample_ID = rs.getInt(1);
                    }
                    
                }
                if( Note.length()>1)
                    Query = "INSERT INTO [dbo].[Barcode] (Sample_ID, Year, SampleDate, Barcode, Note) VALUES(" + Sample_ID + ",2018,'2018-02-19','"+Barcode+"','"+Note+"')";
                else
                    Query = "INSERT INTO [dbo].[Barcode] (Sample_ID, Year, SampleDate, Barcode) VALUES(" + Sample_ID + ",2018,'2018-02-19','"+Barcode+"')";
                
                    statement.executeUpdate(Query);
                    counter++;                    
            }
            System.out.println(counter + " Rows Data Inserted");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }
    
}
