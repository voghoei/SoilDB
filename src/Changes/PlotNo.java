package Changes;
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
import java.util.Arrays;

/**
 *
 * @author Sahar
 */
public class PlotNo {

     public static void main(String[] args) {
        BufferedReader br = null;
        String sample;
        BufferedWriter writer = null;
        
        try {

            String CurrentLine;
            br = new BufferedReader(new FileReader("F:\\2016 winter data\\Experiment Packages.csv"));
            writer = new BufferedWriter(new FileWriter("F:\\2016 winter data\\Plots.csv"));
                
            br.readLine();
            while ((CurrentLine = br.readLine()) != null) {
                sample = CurrentLine.split(",")[2].split("-")[2]+"\n";
                //sample = CurrentLine.substring(0, CurrentLine.indexOf("\t"));
                //CurrentLine += "\t" + count60561[Arrays.asList(Samples).indexOf(sample)];
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
