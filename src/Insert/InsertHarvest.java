package Insert;

/*
 * You can filter your date to have insetion for yout year
 */


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
public class InsertHarvest {

    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br ;
        ResultSet rs ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


        int counter = 0;
        int dublication = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("E:\\2016 winter data\\Phones\\Sweet_corn_data_Blairsville\\Full\\Total Weight Hi2Lo2 corrected.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String FieldMap_ID = countCurrentLine.split(",")[0];
                String Total_Wet_Weight = countCurrentLine.split(",")[2];
                String Sample_Wet_Weight = countCurrentLine.split(",")[3];
                String Sample_Dry_Weight = countCurrentLine.split(",")[4];
                String ExcelDate = countCurrentLine.split(",")[5];
                
                Date DateTime = formatter.parse(ExcelDate);
                java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(DateTime.getTime());

                String Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date) values (" + FieldMap_ID + ",13, '" + Total_Wet_Weight + "','" + sqlDateTime + "')";
                statement.executeUpdate(Query);
                
                Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date) values (" + FieldMap_ID + ",14, '" + Sample_Wet_Weight + "','" + sqlDateTime + "')";
                statement.executeUpdate(Query);
                
                Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date) values (" + FieldMap_ID + ",15, '" + Sample_Dry_Weight + "','" + sqlDateTime + "')";
                statement.executeUpdate(Query);
                
                counter++;
                
            }
            System.out.println(counter + " Rows Data Inserted");
            System.out.println(dublication + " Rows Data Dublicated");

        } catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
            System.err.println("Problem Connecting!  " + e);
        }

    }

}
