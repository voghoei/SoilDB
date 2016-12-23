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


            br = new BufferedReader(new FileReader("F:\\2016 winter data\\Transactions\\Greenhouse Experiment Excel.csv"));
            int counter = 0;
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                
                String Name = countCurrentLine.split(",")[1].replaceAll(" ", "");
                String FileOrigin = countCurrentLine.split(",")[0].replaceAll(" ", "");
                String Destination = countCurrentLine.split(",")[10].replaceAll(" ", "");
                String Block = Destination.split("-")[2];
                String Plot = Destination.split("-")[3];
                String Origin = Destination.split("-")[0].substring(2, 4);
                String ExperimentType = Destination.split("-")[1];
                int SeedNo = 12;//countCurrentLine.split(",")[2].replaceAll(" ", "");
               // String Note = countCurrentLine.split(",")[3];


                int ExperimentType_ID = 0;
                int Block_ID = 0;
                int Origin_ID = 0;
                int Experiment_ID = 0;
                int Package_ID = 0;

                rs = statement.executeQuery("select ID from Origin where code = '" + Origin + "'");
                if (rs.next()) {
                    Origin_ID = rs.getInt(1);
                    rs = statement.executeQuery("select ID from ExperimentType where code = '" + ExperimentType + "'");

                    if (rs.next()) {
                        ExperimentType_ID = rs.getInt(1);
                        rs = statement.executeQuery("select ID from Block where code = '" + Block + "'");
                        if (rs.next()) {
                            Block_ID = rs.getInt(1);
                            statement.executeUpdate("INSERT INTO [dbo].[Experiment] ([ExperimentType_ID],[location_ID],[Block_ID],[Plot],[Year],[Note]) VALUES(" + ExperimentType_ID + "," + Origin_ID + "," + Block_ID + ",'" + Plot + "',16,null)");
                        } else {
                            statement.executeUpdate("INSERT INTO [dbo].[Experiment] ([ExperimentType_ID],[location_ID],[Block_ID],[Plot],[Year],[Note]) VALUES(" + ExperimentType_ID + "," + Origin_ID + ",Null,'" + Plot + "',16,null)");
                        }
                        counter++;
                        rs = statement.executeQuery("select IDENT_CURRENT('Experiment')");
                        if (rs.next()) {
                            Experiment_ID = rs.getInt(1);
                            String q ="select ID from View_Package where replace(Name,' ','') = '" + Name + "' and replace(Origin,' ','') = '" + FileOrigin + "'"; 
//                            String q ="select ID from View_Package where replace(Name,' ','') = '" + Name + "' and replace(Origin,' ','') = '" + FileOrigin + "' and Date_In = '2016-04-01'";
                            rs = statement.executeQuery(q);
                            if (rs.next()) {
                                Package_ID = rs.getInt(1);
//                                if (Note.length() > 1){    
//                                   // String Q = "INSERT INTO [dbo].[PackageWithdrawal1] ([Package_ID],[Out_ID],[Experiment_ID],[Person_ID],[Date],[SeedRemove],[Note]) VALUES(" + Package_ID + ",null," + Experiment_ID + ",1,CONVERT (date, GETDATE())," + SeedNo + "," + Note + ")";
//                                    statement.executeUpdate("INSERT INTO [dbo].[PackageWithdrawal1] ([Package_ID],[Out_ID],[Experiment_ID],[Person_ID],[Date],[SeedRemove],[Note]) VALUES(" + Package_ID + ",null," + Experiment_ID + ",1,CONVERT (date, GETDATE())," + SeedNo + ",'" + Note + "')");
//
//                                } else {
                                    statement.executeUpdate("INSERT INTO [dbo].[PackageWithdrawal] ([Package_ID],[Out_ID],[Experiment_ID],[Person_ID],[Date],[SeedRemove],[Note]) VALUES(" + Package_ID + ",null," + Experiment_ID + ",1,CONVERT (date, GETDATE())," + SeedNo + ",null)");

//                                }

                            } else {
                                System.out.println("There is no record Package with" + Name + " and " + FileOrigin);
                            }

                        } else {
                            System.out.println("There is no Experiment_ID");
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
