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
public class InsertFieldBook {

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

            br = new BufferedReader(new FileReader("E:\\2016 winter data\\Phones\\G2F final Count.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String FieldMap_ID = countCurrentLine.split(",")[0];
                String trait = countCurrentLine.split(",")[2];
                String Value = countCurrentLine.split(",")[3];
                
               // String Note = countCurrentLine.split(",")[3];
               // String ExcelDate = countCurrentLine.split(",")[3];
                String ExcelDate = "2016-10-14 8:58:00";
                Date DateTime = formatter.parse(ExcelDate);
                java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(DateTime.getTime());

                String Query = "";
                
                rs = statement.executeQuery("select ID,Type from Phenotype where Title = '" + trait+"'");
                if (rs.next()) {
                    String phenotype_id = rs.getString(1);
                    String phenotype_type = rs.getString(2);
                    rs = statement.executeQuery("select Value, Date from FieldBook where Phenotype_ID = " + phenotype_id + " and FieldMap_ID = " + FieldMap_ID);
                    if (rs.next()) {

                        dublication++;
                        System.out.println(" Rows dublicated:" + FieldMap_ID + " trait: " + trait + " Date: " + sqlDateTime + " LastDate: " + rs.getDate(2) + " Value: " + Value );

                        if (phenotype_type.equals("Date")) {
                            String d = rs.getString(1);
                            Date Dated = formatter.parse(d);
                            java.sql.Timestamp DBDate = new java.sql.Timestamp(Dated.getTime());
                            if (DBDate.after(sqlDateTime)) {
                               //if (Note.length() > 0) {
                                //    Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + "," + phenotype_id + ", '" + Value + "','" + sqlDateTime + "','" + Note + "')";
                               // } else {
                                    Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date) values (" + FieldMap_ID + "," + phenotype_id + ", '" + Value + "','" + sqlDateTime + "')";
                                //}
                            }
                        }                             
                 
                   // } else if (Note.length() > 0) {
                    //    Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + "," + phenotype_id + ", '" + Value + "','" + sqlDateTime + "','" + Note + "')";
                    } else {
                        Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date) values (" + FieldMap_ID + "," + phenotype_id + ", '" + Value + "','" + sqlDateTime + "')";
                    }

                } else {
                    System.out.println(" Phenotype didn't find" + trait );
                }
                if (Query.length() > 1) {
                    statement.executeUpdate(Query);
                    counter++;
                }
            }
            System.out.println(counter + " Rows Data Inserted");
            System.out.println(dublication + " Rows Data Dublicated");

        } catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
            System.err.println("Problem Connecting!  " + e);
        }

    }

}
