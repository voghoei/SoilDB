package Insert;



import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class InsertExperimentTransaction {

    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        int counter = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-VT6LQFU\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("F:\\2016 winter data\\2016 transactionsFull.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String Name = countCurrentLine.split(",")[0];
                String Plot = countCurrentLine.split(",")[2].split("-")[2];
                String No = countCurrentLine.split(",")[3];
                String Source = countCurrentLine.split(",")[3];

//                if (countCurrentLine.split(",")[1].contains("USDA")) {
//                    Source = countCurrentLine.split(",")[3];
//                }
                // Date d = new Date();
                // java.sql.Date d1 = new java.sql.Date(d.getTime());

                rs = statement.executeQuery("select ID from Pedigree where Name = '" + Name + "'");
                if (rs.next()) {
                    String Pedigree_ID = rs.getString(1);
                    // if (Amount.length() > 0) {
                    //Query = "INSERT INTO [dbo].[Package1] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Amount],[Note]) VALUES(" + Pedigree_ID + ", 8,'" + OrgAccession + "','2016-03-14'," + No + ",'"+Amount+"',null)";
                    // } else {
                    //      Query = "INSERT INTO [dbo].[Package1] ([Pedigree_ID],[Origin_ID],[Origin_Accession],[Date_In],[Number],[Amount],[Note]) VALUES(" + Pedigree_ID + ", 8,'" + OrgAccession + "','2016-03-14'," + No + ",null,null)";
                    //}

                    //  Query = "INSERT INTO [dbo].[Package1] ([Pedigree_ID],[Origin_ID],[Date_In],[Number],[Amount]) VALUES(" + Pedigree_ID + ", 3,'2016-03-14'," + No + ",null )";
                    counter++;
                    // statement.executeUpdate(Query);
                }
            }
            System.out.println(counter + " Rows Data Inserted");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }
    }
}
