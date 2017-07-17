package Insert;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class InsertPedigree {

    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-VT6LQFU\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("E:\\Soil\\2017\\Package info\\2017 transient seed packets G2F.csv"));
            int counter = 0;
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Name = countCurrentLine.split(",")[1];
                //String Alias = countCurrentLine.split(",")[6];
          

                rs = statement.executeQuery("select ID from Pedigree where replace(Name,' ','') = replace('" + Name + "',' ','')");
                if (rs.next()) {
                    System.out.println(rs.getInt(1) + "   Have  ");

//                    if (!Alias.replaceAll(" ", "").equals(rs.getString(3).replaceAll(" ", ""))) {
//                        statement.executeUpdate("Update [dbo].[Pedigree1] set [Alias2] = '" + Alias.replaceAll(" ", "") + "' where ID = " + rs.getInt(1));
//                    }
                } else {
//                    statement.executeUpdate("INSERT INTO [dbo].[Pedigree1] ([Name],[Alias1]) VALUES('" + Name.replaceAll(" ", "") + "','" + Alias.replaceAll(" ", "") + "' )");
                    statement.executeUpdate("INSERT INTO [dbo].[Pedigree] ([Name],[Alias1]) VALUES('" + Name.replaceAll(" ", "") + "','"+Name+"' )");
                    counter++;
                }
            }
            System.out.println(counter + " Rows Data Inserted");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }
}
