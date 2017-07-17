package Changes;

/*
 * To Update Package as a discarded one
 * You need the packageID 
 * 
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
public class DiscardPackages {

    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-VT6LQFU\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("F:\\2016 winter data\\Discards 6-7-16.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Name = countCurrentLine.split(",")[0].replaceAll(" ", "");
                String Origin = countCurrentLine.split(",")[1].replaceAll(" ", "");
                rs = statement.executeQuery("select ID from View_Package where replace(Origin,' ','')='" + Origin + "' and (replace(Name,' ','') = '" + Name + "' or replace(Origin_Accession,' ','')= '" + Name + "' )");
                if (rs.next()) {
                    String Package_ID = rs.getString(1);
                    String Query = "Update [Package] set  Note= 'Discarded', Date_Out = '2016-05-20' where ID =" + Package_ID;
                    statement.executeUpdate(Query);
                    counter++;
                } else {
                    System.out.println(" Package Does not exist name: " + Name + "  Origin: " + Origin);
                }
            }
            System.out.println(counter + " Rows Data Updated");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }

}
