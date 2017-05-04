package Insert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsertExperiment {

    public static void main(String[] args) {
        String countCurrentLine;

        BufferedReader br = null;
        ResultSet rs = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("E:\\Soil\\2017\\salt experiment set up for rep 2.csv"));
            int counter = 0;
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Source=countCurrentLine.split(",")[3];
                String Name = countCurrentLine.split(",")[2];//.split("_")[0];
                String Treatment ="";// countCurrentLine.split(",")[10].split("_")[1];
                String Destination = countCurrentLine.split(",")[1].replaceAll(" ", "");
                String Rep = "2";//Destination.split("-")[2];
                String Plot = Destination.split("-")[3];

                String Origin = Destination.split("-")[0].substring(2, 4);
                String ExperimentType = Destination.split("-")[1];
                int SeedNo = Integer.parseInt(countCurrentLine.split(",")[4]);
                String Note = "";//countCurrentLine.split(",")[12];               

                
                int ExperimentType_ID = 0;
                int Rep_ID = 0;
                int Origin_ID = 0;
                int Experiment_ID = 0;
                int Plot_ID = 0;
                int Package_ID = 0;
                int Treatment_ID = 0;

                // Origin
                rs = statement.executeQuery("select ID from Origin where code = '" + Origin + "'");
                if (rs.next()) {
                    Origin_ID = rs.getInt(1);
                    //ExperimentType
                    rs = statement.executeQuery("select ID from ExperimentType where code = '" + ExperimentType + "'");
                    if (rs.next()) {
                        ExperimentType_ID = rs.getInt(1);
                        //Plot
                        rs = statement.executeQuery("select ID from Plot where Plot = '" + Plot + "'");
                        if (rs.next()) {
                            Plot_ID = rs.getInt(1);
                            //Treatment
//                            rs = statement.executeQuery("select ID from Treatment where Name = '" + Treatment + "'");
//                            if (rs.next()) {
//                                Treatment_ID = rs.getInt(1);
                                //Package
                                rs = statement.executeQuery("select ID from View_Package where replace(Name,' ','') = replace('"+Name+"',' ','') and replace(Origin,' ','') = replace('"+Source+"',' ','')");
                                if (rs.next()) {
                                    Package_ID = rs.getInt(1);
                                    //Rep
                                    rs = statement.executeQuery("select ID from Rep where code = '" + Rep + "'");
                                    //Insert Experiment
                                    if (rs.next()) {
                                        Rep_ID = rs.getInt(1);
                                        statement.executeUpdate("INSERT INTO [dbo].[Experiment] ([ExperimentType_ID],[location_ID],[Rep_ID],[Plot_ID],[Year],[Note],Treatment_ID) VALUES(" + ExperimentType_ID + "," + Origin_ID + "," + Rep_ID + ",'" + Plot_ID + "',17,'" + Note + "',null)");
                                    } else {
                                        statement.executeUpdate("INSERT INTO [dbo].[Experiment] ([ExperimentType_ID],[location_ID],[Rep_ID],[Plot_ID],[Year],[Note],Treatment_ID) VALUES(" + ExperimentType_ID + "," + Origin_ID + ",Null,'" + Plot_ID + "',17,'" + Note + "',null)");
                                    }
                                    counter++;
                                    //Experiment
                                    rs = statement.executeQuery("select IDENT_CURRENT('Experiment')");
                                    if (rs.next()) {
                                        Experiment_ID = rs.getInt(1);
                                        //Package

                                        //if (Note.length() > 1){    
                                        //    statement.executeUpdate("INSERT INTO [dbo].[PackageWithdrawal1] ([Package_ID],[Out_ID],[Experiment_ID],[Person_ID],[Date],[SeedRemove],[Note]) VALUES(" + Package_ID + ",null," + Experiment_ID + ",1,CONVERT (date, GETDATE())," + SeedNo + ",'" + Note + "')");//
                                        //} else {
                                        //Insert PackageWithdrawal
                                        statement.executeUpdate("INSERT INTO [dbo].[PackageWithdrawal] ([Package_ID],[Out_ID],[Experiment_ID],[Person_ID],[Date],[SeedRemove],[Note]) VALUES(" + Package_ID + ",null," + Experiment_ID + ",1,CONVERT (date, GETDATE())," + SeedNo + ",null)");

                                        // }
                                    } else {
                                        System.out.println("There is no record Inserted recently");
                                    }

                                } else {
                                    System.out.println("There is no record Package with " + Name + " and " + Source);
                                }
//                            } else {
//                                System.out.println("There is no record Treatment with " + Treatment);
//
//                            }
                        } else {
                            System.out.println("There is no record with " + Plot);
                        }
                    } else {
                        System.out.println("There is no record with " + ExperimentType);
                    }
                } else {
                    System.out.println("There is no record with " + Origin);
                }
            }
            System.out.println(counter + " Rows Data Inserted");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }
    }

}
