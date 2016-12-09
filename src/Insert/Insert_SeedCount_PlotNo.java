package Insert;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author voghoei
 */
public class Insert_SeedCount_PlotNo {
     public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br ;
        ResultSet rs ;
        int counter = 0;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("E:\\2016 winter data\\Phones\\Harvest\\2016 NUR Seed Count.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String PlotNo = countCurrentLine.split(",")[0];
                String SeedCount = countCurrentLine.split(",")[2];                
                String q = "select [FieldMap_ID] from [View_FieldMap] where [Destination] like '%NUR%' and [Destination] like '%" + PlotNo + "%' ";
                rs = statement.executeQuery(q);
                if (rs.next()) {
                    String FieldMap_ID = rs.getString(1);                    
                
                String Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date) values (" + FieldMap_ID + ",9, '" + SeedCount + "','2016-11-15 10:00:00.000')";
                statement.executeUpdate(Query); 
                counter++;
               } 
            }
            System.out.println(counter + " Rows Data Inserted");

        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.err.println("Problem Connecting!  " + e);
        }

    }
    
}
