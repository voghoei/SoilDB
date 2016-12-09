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


/**
 *
 * @author voghoei
 */
public class UpdateCount {

   public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br ;
        ResultSet rs ;
        int counter = 0;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("E:\\2016 winter data\\Files\\20_seed_weights.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Name = countCurrentLine.split(",")[0];
                int Weight = Integer.parseInt(countCurrentLine.split(",")[1]);
                             
                rs = statement.executeQuery("select Number, Package_ID from [[View_Package_Full]] where Pedigree_Name = "+Name);

                if (rs.next()) {
                    int Number = rs.getInt(1);
                    String Package_ID =rs.getString(2);
                    int SeedCount= (Number*20)/Weight;
                    String Query= "update Package1 set Number ='"+SeedCount+"' , Number = null where ID ="+Package_ID;
                    

                
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
