package Insert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sahar
 */
public class InsertMapInfo {

    public static void main(String[] args) throws SQLException {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();
            String Query = "";

            br = new BufferedReader(new FileReader("D:\\Soil Files\\2019\\Maps\\MMH.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String ExperimentPlot_ID = countCurrentLine.split(",")[0].replaceAll(" ", "");
                String Row = countCurrentLine.split(",")[3].replaceAll(" ", "");
                String Range = countCurrentLine.split(",")[4].replaceAll(" ", "");
                String Block = countCurrentLine.split(",")[2].replaceAll(" ", "");
                String subBlock = countCurrentLine.split(",")[5].replaceAll(" ", "");
//                String Lat = countCurrentLine.split(",")[1].replaceAll(" ", "");
//                String Long = countCurrentLine.split(",")[2].replaceAll(" ", "");
//                String Note = countCurrentLine.split(",")[3].replaceAll(" ", "");
                  rs = statement.executeQuery("select max(ID)+1 from FieldMap");
                  if (rs.next()) {
                        int ID = rs.getInt(1);
                        Query = "insert into FieldMap(ID,ExperimentPlot_ID,Row,Range, Block, SubBlock) values ("+ID+"," + ExperimentPlot_ID + ", " + Row + "," + Range + ",'" + Block + "','" + subBlock + "')";
                        statement.executeUpdate(Query);
                        counter++;
                  }
                // } else {
                //System.out.println(" Package Does not exist Code: " + Code);
                //  }
            }
            System.out.println(counter + " Rows Data Updated");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }

}
