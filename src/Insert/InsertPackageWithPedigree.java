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
public class InsertPackageWithPedigree {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("D:\\Soil Files\\2018\\Package info\\USDA-GRIN.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Name = countCurrentLine.split(",")[1];
                String OrgAccession = countCurrentLine.split(",")[3].replaceAll(" ", "");
                String  No= countCurrentLine.split(",")[4];    
                String Amount = countCurrentLine.split(",")[5];
                //String Note = countCurrentLine.split(",")[6];      
                String Pedigree_ID = countCurrentLine.split(",")[7];
                        
                String Query = "";

                Query = "INSERT INTO [dbo].[Package] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Amount],[Original_Name],Discarded) VALUES(" + Pedigree_ID + ",4,'"+OrgAccession+"','2018-09-12',"+No+","+Amount+",'" + Name +"',0)";
                System.out.println(Name + " inserted");
                counter++;
                statement.executeUpdate(Query);                
            }
            System.out.println(counter + " Rows Data Inserted");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }

}
