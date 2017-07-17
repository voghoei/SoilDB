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
public class InsertWithdrawalWithPackage {
     public static void main(String[] args) throws SQLException {
        String countCurrentLine;

        BufferedReader br = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();


            br = new BufferedReader(new FileReader("E:\\Soil\\2017\\Package info\\Final 2017 Summer field PAT.csv"));
            int counter = 0;
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Destination = countCurrentLine.split(",")[8];  
                String Package_ID = countCurrentLine.split(",")[13]; 
                String Note = countCurrentLine.split(",")[12];     
                String SeedNo = countCurrentLine.split(",")[10];                            
                int Experiment_ID = 0;
                
                counter++;
                rs = statement.executeQuery("select Experiment_ID from View_Experiment where Destination ='"+Destination+"'");
                if (rs.next()) {
                    Experiment_ID = rs.getInt(1);
                    statement.executeUpdate("INSERT INTO [dbo].[PackageWithdrawal] ([Package_ID],[Experiment_ID],[Person_ID],[Date],[SeedRemove],Note) VALUES(" + Package_ID + "," + Experiment_ID + ",1,'2017-05-02'," + SeedNo + ",'"+Note+"')");

                }               
            }
            System.out.println(counter + " Rows Data Inserted");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }
     }
}
