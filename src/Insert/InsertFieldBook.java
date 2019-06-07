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
        BufferedReader br;
        ResultSet rs;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        int counter = 0;
        int dublication = 0;
        String Query ="";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("D:\\Soil Files\\2018\\FieldBook\\2018_GH_Crosses_seedCount.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String FieldMap_ID = countCurrentLine.split(",")[0];
                String trait = countCurrentLine.split(",")[1];
                String Value = countCurrentLine.split(",")[6];
                
                String ExcelDate = countCurrentLine.split(",")[5];
                Date DateTime = formatter.parse(ExcelDate);
                java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(DateTime.getTime());
               
                String Note = countCurrentLine.split(",")[4]+" ; "+countCurrentLine.split(",")[8];
                int year = sqlDateTime.getYear();

                Query = "select ID from Phenotype where Title = '" + trait + "'";
                rs = statement.executeQuery(Query);
                if (rs.next()) {
                    String phenotype_id = rs.getString(1);
                    rs = statement.executeQuery("select ID from FieldBook where Phenotype_ID = " + phenotype_id + " and FieldMap_ID = " + FieldMap_ID + " and year([Date])=" + year);
                    if (rs.next()) {
                        dublication++;
                        System.out.println(" Rows dublicated:" + FieldMap_ID + " trait: " + trait + " Date: " + sqlDateTime + " Value: " + Value + "  With  ID = " + rs.getString(1));
                        continue;
                    } else {
                        if (Note.length()>0)
                            Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + "," + phenotype_id + ", '" + Value + "','" + sqlDateTime + "', '"+Note+"')";
                        else                            
                            Query = "insert into FieldBook(FieldMap_ID,Phenotype_ID,Value,Date) values (" + FieldMap_ID + "," + phenotype_id + ", '" + Value + "','" + sqlDateTime + "')";
                    }

                } else {
                    System.out.println(" Phenotype didn't find" + trait);
                    System.out.println(" Query:  " + Query);
                }
                
                if (Query.length() > 1) {
                    statement.executeUpdate(Query);
                    counter++;
                }
            }
            System.out.println(counter + " Rows Data Inserted");
            System.out.println(dublication + " Rows Data Dublicated");

        } catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
            System.out.println("Query=  "+Query);
            System.err.println("Problem Connecting!  " + e);
        }

    }

}
