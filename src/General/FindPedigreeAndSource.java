package General;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FindPedigreeAndSource {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        BufferedReader br = null;
        ResultSet rs = null;
        ResultSet rs_Match = null;

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-VT6LQFU\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
        Statement statement = conn.createStatement();

        String sample;
        BufferedWriter writer = null;

        try {

            String CurrentLine;
            br = new BufferedReader(new FileReader("F:\\2016 winter data\\Transactions\\2016 transactionsFull.csv"));
            writer = new BufferedWriter(new FileWriter("F:\\2016 winter data\\Transactions\\2016 transactionsFull P and S.csv"));

            br.readLine();
            while ((CurrentLine = br.readLine()) != null) {
                String Name = CurrentLine.split(",")[0].replaceAll(" ", "");;
                String FileOrigin = CurrentLine.split(",")[1];
                rs_Match = statement.executeQuery("select * from View_Package where Name = '" + Name + "' and Origin = '" + FileOrigin + "'");
                
                String Origin = "";
                sample = Name + ",";
                if (!rs_Match.next()) {
                    sample += FileOrigin +",";
                    rs = statement.executeQuery("select * from View_Package where Name = '" + Name + "'");
                    while (rs.next()) {
                        sample = sample + " " + rs.getString(2);
                    }
                }
           
                sample +=  ", \n";
                writer.write(sample);

            }
            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
