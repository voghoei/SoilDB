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
import java.util.Arrays;

/**
 *
 * @author Sahar
 */
public class FileMixer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader br = null;
        String sample;
        BufferedWriter writer = null;
        String[][] count = new String[1149][542];
        String[] count60561 = new String[542];
        String[] Samples = new String[542];
        try {

            String countCurrentLine;
            br = new BufferedReader(new FileReader("F:\\documents-export-2016-02-09\\Analysis\\Count1000.txt"));
            int line = 0;
            while ((countCurrentLine = br.readLine()) != null) {
                if (line == 0) {
                    Samples = countCurrentLine.split("\\t");
                }
                if (line == 1) {
                    count60561 = countCurrentLine.split("\\t");
                }
                count[line] = countCurrentLine.split("\\t");
                line++;
            }
            //System.out.println(Arrays.deepToString(count));
            //System.out.println(Arrays.asList(Samples).indexOf("LMAD.26.14A0093"));

            String dayCurrentLine;
            br = new BufferedReader(new FileReader("F:\\documents-export-2016-02-09\\Analysis\\day_genotypes.txt"));

            line = 0;
            while ((dayCurrentLine = br.readLine()) != null) {
                sample = dayCurrentLine.substring(0, dayCurrentLine.indexOf("\t"));
                //System.out.println(sample);
                if (line == 0) {
                    dayCurrentLine += "\t" + " Bactery_60561";
                } else if (Arrays.asList(Samples).indexOf(sample) > -1) {
                    dayCurrentLine += "\t" + count60561[Arrays.asList(Samples).indexOf(sample)];
                } else {
                    dayCurrentLine += "\t" + "NA";
                }

                //   System.out.println(dayCurrentLine);
                writer = new BufferedWriter(new FileWriter("F:\\documents-export-2016-02-09\\Analysis\\dayCount60561.txt"));
                writer.write(dayCurrentLine);
                line++;
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
