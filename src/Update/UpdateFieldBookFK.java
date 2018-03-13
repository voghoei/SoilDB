/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Update;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author voghoei
 */
public class UpdateFieldBookFK {
    
    public static void main(String[] args) {
        BufferedReader br = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        int counter = 1;
        String Query2="";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= CAGT-SAHAR-D\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();
            rs = statement.executeQuery("select ID from FieldBook order by ID");
            
            while (rs.next()) {
                int ID = rs.getInt(1);
                //System.out.println(ID);
                Statement statement2 = conn.createStatement();
                Query2 = "select dbo.FieldMap.ExperimentPlot_ID FROM dbo.FieldMap INNER JOIN  dbo.FieldBook ON dbo.FieldMap.ID = dbo.FieldBook.FieldMap_ID and  FieldBook.ID =" + ID;
                
                rs2 = statement2.executeQuery(Query2);    
                if(rs2.next()){
                    int ExperimentPlot_ID = rs2.getInt(1);
                    Statement statement3 = conn.createStatement();
                    statement3.executeUpdate("update FieldBook set FieldMap_ID= " + ExperimentPlot_ID +"where ID = "+ID );
                    //System.out.println(counter++ );
                    counter++;
                }                                    
            }            
            System.out.println(counter + " Rows Data Updated");

        } catch (Exception e) {
            System.out.println(Query2);
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }
    
    
}
