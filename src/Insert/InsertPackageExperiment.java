/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Insert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author voghoei
 */
public class InsertPackageExperiment {

    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-VT6LQFU\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("E:\\2016 winter data\\Soil\\2017\\Package info\\Plains.csv"));
            int counter = 0;
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Name = countCurrentLine.split(",")[10].split("_")[0];
                String Treatment = countCurrentLine.split(",")[10].split("_")[1];
                String Note = countCurrentLine.split(",")[13];
                String Plot = countCurrentLine.split(",")[8];
                String Block = countCurrentLine.split(",")[6];
                String No = countCurrentLine.split(",")[11];
                String Year = "17";
                String Location_code = countCurrentLine.split(",")[4];
                String Location_Name = countCurrentLine.split(",")[3];
                String ExperimentCode = countCurrentLine.split(",")[2];
                String ExperimentType = countCurrentLine.split(",")[0];
                //String Alias = countCurrentLine.split(",")[6];
                int Pedigree_ID=0;
                int Origin_ID=0;
                int Block_ID=0;
                int ExperimentType_ID=0;
                int Package_ID=0;
                int Experiment_ID=0;

                //Pedigree Selection
                rs = statement.executeQuery("select ID from Pedigree1 where replace(Name,' ','') = replace('" + Name + "',' ','')");
                if (rs.next()) {
                    Pedigree_ID = rs.getInt(1);
                } else {
//                    statement.executeUpdate("INSERT INTO [dbo].[Pedigree1] ([Name],[Alias1]) VALUES('" + Name.replaceAll(" ", "") + "','" + Alias.replaceAll(" ", "") + "' )");
                    statement.executeUpdate("INSERT INTO [dbo].[Pedigree1] ([Name],[Alias1]) VALUES('" + Name.replaceAll(" ", "") + "',null )");
                    rs = statement.executeQuery("select IDENT_CURRENT('Pedigree1')");
                    rs.next();
                    Pedigree_ID = rs.getInt(1);
                }

//                //ExperimentType Selection
//                rs = statement.executeQuery("select ID from [ExperimentType] where replace(Code,' ','') = replace('" + ExperimentType + "',' ','')");
//                if (rs.next()) {
//                    Block_ID = rs.getInt(1);
//                } else {
//                    statement.executeUpdate("INSERT INTO [dbo].[[ExperimentType]] ([Code],[Name]) VALUES('" + Block.replaceAll(" ", "") + "','" + Block.replaceAll(" ", "") + "' )");
//                    rs = statement.executeQuery("select IDENT_CURRENT('ExperimentType')");
//                    rs.next();
//                    ExperimentType_ID = rs.getInt(1);
//                }
                
                //Block1 Selection
                rs = statement.executeQuery("select ID from Block1 where replace(Code,' ','') = replace('" + Block + "',' ','')");
                if (rs.next()) {
                    Block_ID = rs.getInt(1);
                } else {
                    statement.executeUpdate("INSERT INTO [dbo].[Block1] ([Code],[Name]) VALUES('" + ExperimentCode.replaceAll(" ", "") + "','" + ExperimentType.replaceAll(" ", "") + "' )");
                    rs = statement.executeQuery("select IDENT_CURRENT('Block1')");
                    rs.next();
                    Block_ID = rs.getInt(1);
                }

//                //Origin1 Location_code
//                rs = statement.executeQuery("select ID from Origin1 where replace(Code,' ','') = replace('" + Location_code + "',' ','')");
//                if (rs.next()) {
//                    Block_ID = rs.getInt(1);
//                } else {
//                    statement.executeUpdate("INSERT INTO [dbo].[Origin1] ([Code],[Name]) VALUES('" + Location_code.replaceAll(" ", "") + "','" + Location_Name.replaceAll(" ", "") + "' )");
//                    rs = statement.executeQuery("select IDENT_CURRENT('Origin1')");
//                    rs.next();
//                    Origin_ID = rs.getInt(1);
//                }

                //Package selection
                rs = statement.executeQuery("select ID from Package where replace(Code,' ','') = replace('" + Location_code + "',' ','')");
                if (rs.next()) {
                    Block_ID = rs.getInt(1);
                } else {
                    statement.executeUpdate("INSERT INTO [dbo].[Origin1] ([Code],[Name]) VALUES('" + Location_code.replaceAll(" ", "") + "','" + Location_Name.replaceAll(" ", "") + "' )");
                    rs = statement.executeQuery("select IDENT_CURRENT('Origin1')");
                    rs.next();
                    Origin_ID = rs.getInt(1);
                }
                    

                statement.executeUpdate("INSERT INTO [dbo].[Experiment1] ([ExperimentType_ID],[location_ID],[Plot],[Year],Block_ID,Note) VALUES(8,20," + Plot + ",17,"+Block_ID+",'"+Note+"')");
                rs = statement.executeQuery("select IDENT_CURRENT('Experiment1')");
                rs.next();
                Experiment_ID = rs.getInt(1);
                    
                statement.executeUpdate("INSERT INTO [dbo].[PackageWithdrawal1] ([Package_ID],[Experiment_ID],[Person_ID],[Date],[SeedRemove],Note) VALUES(" + Package_ID + "," + Experiment_ID + ",1,'2016-04-01',100,'"+Treatment+"')");
                counter++;               

            }
            System.out.println(counter + " Rows Data Inserted");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }
}
