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
        int counter = 1;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= CAGT-SAHAR-D\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

           br = new BufferedReader(new FileReader("D:\\Soil Files\\2018\\Report\\18GH_HMC_NewPackages.csv"));
           br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String  Pedigree_ID = countCurrentLine.split(",")[12];//"294";
                String  Origin_Accession = countCurrentLine.split(",")[5];//"17IH-NUR-0-0322";
                String  Original_Name= countCurrentLine.split(",")[19]; // "NC334";
                String  No= countCurrentLine.split(",")[9]; // "113";
                String  ExperimentPlot_ID= countCurrentLine.split(",")[15]; //"5394";
                String Note = countCurrentLine.split(",")[20];     
                String  Query = "";
                
                if (Note.length()>0)
                    Query = "INSERT INTO [dbo].[Package] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Original_Name],[ExperimentPlot_ID],[Discarded], [Note]) VALUES(" + Pedigree_ID + ",9,'" + Origin_Accession + "','2018-02-21','"+No+"','" + Original_Name +"',"+ExperimentPlot_ID+" , 0 ,'"+Note+"')";
                else                            
                    Query = "INSERT INTO [dbo].[Package] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Original_Name],[ExperimentPlot_ID],[Discarded]) VALUES(" + Pedigree_ID + ",9,'" + Origin_Accession + "','2019-02-21','"+No+"','" + Original_Name +"',"+ExperimentPlot_ID+" , 0)";
                
                statement.executeUpdate(Query);   
                System.out.println(counter + " Rows Data Inserted "+ExperimentPlot_ID);
                counter++;
            }
            System.out.println(counter + " Rows Data Inserted");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }
}
