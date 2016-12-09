package Update;

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

/**
 *
 * @author Sahar
 */
public class UpdateMapInfo {

     public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-VT6LQFU\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("F:\\2016 winter data\\Map\\Map_GPS_Hi2_Lo2.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
//                String PackageWithdrawal_ID = countCurrentLine.split(",")[1].replaceAll(" ", "");
                String Code = countCurrentLine.split(",")[0].replaceAll(" ", "");
//                String Row = countCurrentLine.split(",")[3].replaceAll(" ", "");
//                String Range = countCurrentLine.split(",")[2].replaceAll(" ", "");
                String Lat = countCurrentLine.split(",")[1].replaceAll(" ", "");
                String Long = countCurrentLine.split(",")[2].replaceAll(" ", "");
                rs = statement.executeQuery("select [PackageWithdrawal_ID] from View_PackageWithdrawal_ForMap where replace([Code],' ','')='16B-ABM-" + Code + "' ");
                if (rs.next()) {
                    String PackageWithdrawal_ID = rs.getString(1);
//                    String Query = "insert into FieldMap1(PackageWithdrawal_ID,Row,Range) values ("+PackageWithdrawal_ID+", "+Row+","+Range+")";
//                     String Query = "insert into FieldMap1(PackageWithdrawal_ID,Row,Range,GPS_Lat,GPS_Long) values ("+PackageWithdrawal_ID+", "+Row+","+Range+","+Lat+","+Long+")";
                       String Query = "update FieldMap set GPS_Lat = "+Lat+", GPS_Long = "+Long+" where PackageWithdrawal_ID = "+PackageWithdrawal_ID;
                    statement.executeUpdate(Query);
                    counter++;
                } else {
                    System.out.println(" Package Does not exist Code: " + Code);
                }
            }
            System.out.println(counter + " Rows Data Updated");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }
    
}
