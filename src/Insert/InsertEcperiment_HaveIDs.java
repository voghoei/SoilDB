/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Insert;

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
public class InsertEcperiment_HaveIDs {
     public static void main(String[] args) throws SQLException {
        String countCurrentLine;

        BufferedReader br = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();


            br = new BufferedReader(new FileReader("E:\\2016 winter data\\Transactions\\Greenhouse Experiment 12 seeds only 2016.csv"));
            int counter = 0;
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                
                String Plot = countCurrentLine.split(",")[0]; 
                String Package_ID = countCurrentLine.split(",")[10];     
                int SeedNo = 6;                           
                int Experiment_ID = 0;
                
                statement.executeUpdate("INSERT INTO [dbo].[Experiment] ([ExperimentType_ID],[location_ID],[Plot],[Year]) VALUES(5,13,'" + Plot + "',16)");
                counter++;
                rs = statement.executeQuery("select IDENT_CURRENT('Experiment')");
                if (rs.next()) {
                    Experiment_ID = rs.getInt(1);
                    statement.executeUpdate("INSERT INTO [dbo].[PackageWithdrawal] ([Package_ID],[Experiment_ID],[Person_ID],[Date],[SeedRemove]) VALUES(" + Package_ID + "," + Experiment_ID + ",1,CONVERT (date, GETDATE())," + SeedNo + ")");

                }               
            }
            System.out.println(counter + " Rows Data Inserted");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }
     }
}
