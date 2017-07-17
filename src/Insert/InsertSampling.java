/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Insert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author voghoei
 */
public class InsertSampling {

    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br;
        int counter = 0;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("E:\\Soil\\2017\\Sampling\\Full Sampling 1 Data.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String FieldMap_ID = countCurrentLine.split(",")[0];
                String barcode = countCurrentLine.split(",")[4];
                String Note = countCurrentLine.split(",")[3];
                int phenotype_id = 3;

                String Query = "";

                if (Note.length() > 0) {
                    Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + "," + phenotype_id + ", '" + barcode + "','2017-06-27 09:00:00.000','" + Note + "')";
                } else {
                    Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date) values (" + FieldMap_ID + "," + phenotype_id + ", '" + barcode + "','2017-06-27 09:00:00.000')";

                }                
                statement.executeUpdate(Query);
                counter++;

            }
            System.out.println(counter + " Rows Data Inserted");          

        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.err.println("Problem Connecting!  " + e);
        }

    }

}
