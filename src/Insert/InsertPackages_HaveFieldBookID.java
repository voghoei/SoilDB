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
import java.sql.Statement;

/**
 *
 * @author voghoei
 */
public class InsertPackages_HaveFieldBookID {
     public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= CAGT-SAHAR-D\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("D:\\Soil Files\\2017\\Reports\\NUR_New_Pakhages 2017 (Harvest).csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String  Pedigree_ID = countCurrentLine.split(",")[12];
                String  Origin_Accession = countCurrentLine.split(",")[5];
                String  Original_Name= countCurrentLine.split(",")[0];
                String  No= countCurrentLine.split(",")[9];
                String  ExperimentPlot_ID= countCurrentLine.split(",")[15];
                
                String  Query = "INSERT INTO [dbo].[Package] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Original_Name],[ExperimentPlot_ID],[Discarded]) VALUES(" + Pedigree_ID + ",9,'" + Origin_Accession + "','2017-12-12','"+No+"','" + Original_Name +"',"+ExperimentPlot_ID+" , 0)";
                
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
