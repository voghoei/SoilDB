package Insert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Sahar
 */
public class InsertPackages {

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
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-VT6LQFU\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("F:\\2016 winter data\\G2F Packets.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Name = countCurrentLine.split(",")[0];
                String OrgAccession = countCurrentLine.split(",")[0].replaceAll(" ", "");
                //String Note = countCurrentLine.split(",")[7];
                String  No= countCurrentLine.split(",")[2];
                //String Amount = countCurrentLine.split(",")[2];
                String Query = "";

                rs = statement.executeQuery("select ID from Pedigree where replace(Name,' ','') = replace('" + Name + "',' ','')");
                if (rs.next()) {
                    String Pedigree_ID = rs.getString(1);
//                    if (Amount.length() > 0) {
////                       Query = "INSERT INTO [dbo].[Package1] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Amount],[Note],[Original_Name]) VALUES(" + Pedigree_ID + ",6,'" + OrgAccession + "','2016-03-14',Null,'"+ Amount +"','"+ Note+"','" + Name +"')";
//                       Query = "INSERT INTO [dbo].[Package] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Amount],[Note],[Original_Name]) VALUES(" + Pedigree_ID + ", 6,'" + OrgAccession + "','2016-03-14',"+No+",'"+Amount+"',Null,'" + Name +"')";
//                   
//                  } else {
                        Query = "INSERT INTO [dbo].[Package] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Amount],[Note],[Original_Name]) VALUES(" + Pedigree_ID + ",12,'" + OrgAccession + "','2016-04-01','"+No+"',Null,Null,'" + Name +"')";
//                    }

                    
                    counter++;
                    statement.executeUpdate(Query);
                }
                else
                    System.out.println(Name + " Does not exist");
            }
            System.out.println(counter + " Rows Data Inserted");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }

}
