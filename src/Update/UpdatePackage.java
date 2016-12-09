package Update;

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
public class UpdatePackage {

    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br ;
        ResultSet rs ;
        int counter = 0;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("C:\\Users\\voghoei\\Desktop\\update2.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Package_ID = countCurrentLine.split(",")[0];
                             
                rs = statement.executeQuery("select Number, Amount from [Package] where ID = "+Package_ID);

                if (rs.next()) {
                    String Number = rs.getString(1);
                    String Amount ="";
                    if (rs.getString(2)!=null)
                         Amount = rs.getString(2).split("-")[0];
                    else
                        Amount = "null";
                    String Query = "";
                    if (Amount.equalsIgnoreCase("NULL") || Amount.equalsIgnoreCase("N/S")){
                        Query= "update Package1 set Amount ='"+Number+"' , Number = null where ID ="+Package_ID;
                    }else{
                        Query = "update Package1 set Amount ='"+Number+"' , Number= '"+ Amount+ "' where ID ="+Package_ID;
                    }

                
                statement.executeUpdate(Query);
                   counter++;
                }
                           
            }
            System.out.println(counter + " Rows Data Inserted");


        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.err.println("Problem Connecting!  " + e);
        }

    }

}
