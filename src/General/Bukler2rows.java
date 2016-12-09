package General;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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

/**
 *
 * @author Sahar
 */
public class Bukler2rows {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws ClassNotFoundException, SQLException  {
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
            writer = new BufferedWriter(new FileWriter("F:\\2016 winter data\\Transactions\\2016 transactionsFull_Result.csv"));

            br.readLine();
            while ((CurrentLine = br.readLine()) != null) {
                int Count =0;
                String Name = CurrentLine.split(",")[0].replaceAll(" ", "");;
                String FileOrigin = CurrentLine.split(",")[1];
                rs_Match = statement.executeQuery("select ID from View_Package where replace(Name,' ','') = replace('" + Name + "',' ','') and replace(Origin,' ','') = replace('" + FileOrigin + "',' ','')");
                
                String Origin = "";
                sample = Name + ",";
                while (rs_Match.next()) {
                    Count++;
                }
                if (Count>1){
                    sample += ", "+Count;                    
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
