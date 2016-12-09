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

/**
 *
 * @author Sahar
 */
public class InsertPlot {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        String Query="";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-VT6LQFU\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            for (int i=1; i<230; i++){
                Query = "INSERT INTO [dbo].[Plot] ([No]) VALUES("+i+")";
                statement.executeUpdate(Query);
            }

                    
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }
    }
    
}
