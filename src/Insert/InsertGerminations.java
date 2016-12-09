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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Sahar
 */
public class InsertGerminations {

    public static void main(String[] args) {
        String countCurrentLine;
        BufferedReader br = null;
        ResultSet rs = null;
        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yy hh:mm a");

        int counter = 0;
        int dublication = 0;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; instance= DESKTOP-8SM3HF1\\SQLEXPRESS;databaseName=Soil;integratedSecurity=true");
            Statement statement = conn.createStatement();

            br = new BufferedReader(new FileReader("F:\\2016 winter data\\Phynotypes\\Germinatation-7-6.csv"));
            br.readLine();
            while ((countCurrentLine = br.readLine()) != null) {
                String FieldMap_ID = countCurrentLine.split(",")[0].replaceAll(" ", "");
                String Value = countCurrentLine.split(",")[2].replaceAll(" ", "");
                String trait = countCurrentLine.split(",")[1];
                Date DateTime = formatter.parse(countCurrentLine.split(",")[4]);
                java.sql.Timestamp sqlDateTime = new java.sql.Timestamp(DateTime.getTime());
                String Note = countCurrentLine.split(",")[3].replaceAll("'", "~");
                String Query = "";
                int LastValue = 0;
                

                //     Germination
                if (trait.equalsIgnoreCase("Germination")) {
                    rs = statement.executeQuery("select Value, Date from FieldBook where Phenotype_ID = 0 and FieldMap_ID = " + FieldMap_ID);
                    if (rs.next()) {
                        LastValue = rs.getInt(1);
                        dublication++;
                        System.out.println(" Rows dublicated:" + FieldMap_ID + " trait: " + trait + " Date: " + sqlDateTime + " LastDate: " + rs.getDate(2) + " Value: " + Value + " last Value: " + LastValue);

                        if (LastValue < Integer.parseInt(Value)) {
                            if (Note.length() > 0) {
                                Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",0, " + Value + ",'" + sqlDateTime + "','" + Note + "')";
                            } else {
                                Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",0, " + Value + ",'" + sqlDateTime + "',null)";
                            }

                        }
                    } else if (Note.length() > 0) {
                        Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",0, " + Value + ",'" + sqlDateTime + "','" + Note + "')";
                    } else {
                        Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",0, " + Value + ",'" + sqlDateTime + "',null)";
                    }
                }

                //Staminate Anthesis   
                if (trait.equalsIgnoreCase("Staminate Anthesis")) {
                    rs = statement.executeQuery("select Value, Date from FieldBook where Phenotype_ID = 1 and FieldMap_ID = " + FieldMap_ID);
                    if (rs.next()) {
                        dublication++;
                        System.out.println(" Rows dublicated:" + FieldMap_ID + " trait: " + trait + " Date: " + sqlDateTime + " LastDate: " + rs.getDate(2) + " Value: " + Value + " last Value: " + LastValue);

                        Date LastDateConvertor= formatter.parse(rs.getString(1));
                        java.sql.Timestamp LastDate = new java.sql.Timestamp(LastDateConvertor.getTime());
                        if (LastDate.after(sqlDateTime)) {
                            if (Note.length() > 0) {
                                Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",1, '" + Value + "','" + sqlDateTime + "','" + Note + "')";
                            } else {
                                Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",1, '" + Value + "','" + sqlDateTime + "',null)";
                            }
                        }
                    } else if (Note.length() > 0) {
                        Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",1, '" + sqlDateTime + "','" + sqlDateTime + "','" + Note + "')";
                    } else {
                        Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",1, '" + sqlDateTime + "','" + sqlDateTime + "',null)";
                    }

                }
                
                // Flowring Silk
                if (trait.equalsIgnoreCase("Flowring Silk")) {
                    rs = statement.executeQuery("select Value, Date from FieldBook where Phenotype_ID = 1 and FieldMap_ID = " + FieldMap_ID);
                    if (rs.next()) {
                        dublication++;
                        System.out.println(" Rows dublicated:" + FieldMap_ID + " trait: " + trait + " Date: " + sqlDateTime + " LastDate: " + rs.getDate(2) + " Value: " + Value + " last Value: " + LastValue);

                        Date LastDateConvertor= formatter.parse(rs.getString(1));
                        java.sql.Timestamp LastDate = new java.sql.Timestamp(LastDateConvertor.getTime());
                        if (LastDate.after(sqlDateTime)) {
                            if (Note.length() > 0) {
                                Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",2, '" + Value + "','" + sqlDateTime + "','" + Note + "')";
                            } else {
                                Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",2, '" + Value + "','" + sqlDateTime + "',null)";
                            }
                        }
                    } else if (Note.length() > 0) {
                        Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",2, '" + sqlDateTime + "','" + sqlDateTime + "','" + Note + "')";
                    } else {
                        Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",2, '" + sqlDateTime + "','" + sqlDateTime + "',null)";
                    }

                }
                
                //           Flowring Silk
                if (trait.equalsIgnoreCase("Week6 Sample Tupe")) {
                    rs = statement.executeQuery("select Value, Date from FieldBook where Phenotype_ID = 1 and FieldMap_ID = " + FieldMap_ID);
                    if (rs.next()) {
                        dublication++;
                        System.out.println(" Rows dublicated:" + FieldMap_ID + " trait: " + trait + " Date: " + sqlDateTime + " LastDate: " + rs.getDate(2) + " Value: " + Value + " last Value: " + LastValue);

                        Date LastDateConvertor= formatter.parse(rs.getString(1));
                        java.sql.Timestamp LastDate = new java.sql.Timestamp(LastDateConvertor.getTime());
                        if (LastDate.after(sqlDateTime)) {
                            if (Note.length() > 0) {
                                Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",3, '" + Value + "','" + sqlDateTime + "','" + Note + "')";
                            } else {
                                Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",3, '" + Value + "','" + sqlDateTime + "',null)";
                            }
                        }
                    } else if (Note.length() > 0) {
                        Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",3, '" + sqlDateTime + "','" + sqlDateTime + "','" + Note + "')";
                    } else {
                        Query = "insert into FieldBook1(FieldMap_ID,Phenotype_ID,Value,Date,Note) values (" + FieldMap_ID + ",3, '" + sqlDateTime + "','" + sqlDateTime + "',null)";
                    }

                }
                
                if (Query.length()>1){
                    statement.executeUpdate(Query);
                    counter++;
                }
                
                
            }
            System.out.println(counter + " Rows Data Inserted");
                System.out.println(dublication + " Rows Data Dublicated");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Problem Connecting!");
        }

    }
}
