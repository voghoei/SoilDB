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
public class insertPackageExact {
    public static void main(String[] args) {
        String countCurrentLine;

        BufferedReader br = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("D:\\Soil Files\\2017\\Package info\\PackageCorrection.csv"));
            int counter = 0;
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                int id = Integer.parseInt(countCurrentLine.split(",")[0]);
                int Pedigree_ID = Integer.parseInt(countCurrentLine.split(",")[1]);
                String Origin_Accession = countCurrentLine.split(",")[4];                
                int Number = Integer.parseInt(countCurrentLine.split(",")[7]);
                String Original_Name = countCurrentLine.split(",")[10];               
                int ExperimentPlot_ID =Integer.parseInt(countCurrentLine.split(",")[12]);
                String Query = "INSERT INTO [dbo].[Package]  ([ID],[Pedigree_ID],[Box_ID],[Origin_ID],[Origin_Accession],[Date_In],[Date_Out],[Number],[Amount],[Note],[Original_Name],[Discarded],[ExperimentPlot_ID]) VALUES"
                        + "(" + id + "," + Pedigree_ID + ",null,9,'" + Origin_Accession + "','2016-12-12',null," + Number + ",null,null,'" + Original_Name + "',0," + ExperimentPlot_ID + ")";               
                System.out.println(Query);
                //statement.executeUpdate(Query);
                counter++;
                //System.out.println(id + " Inserted");
                
            }
            System.out.println(counter + " Rows Data Inserted");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }
    }

}
