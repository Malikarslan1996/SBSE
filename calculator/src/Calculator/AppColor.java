package Calculator;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Mahmoud-Uni
 */
public class AppColor {

    // list all bachground color of all GUI components.
    // Their values are retrieved from csv file.
    public Color mainFrameColor;
    public Color jButton2;
    public Color jTextField1;
    public Color jLabel1;
    public Color jPanel1;
    public Color jPanel5;
    public Color jPanel2;
    public Color jButton1;
    public Color jButton3;
    public Color jButton4;
    public Color jButton5;
    public Color jButton6;
    public Color jButton7;
    public Color jButton8;
    public Color jButton9;
    public Color jButton10;
    public Color jPanel3;
    public Color jButton11;
    public Color jButton15;
    public Color jButton16;
    public Color jButton17;
    public Color jButton18;
    public Color jPanel4;
    public Color jButton12;
    public Color jButton13;
    public Color jButton14;
    public Color jTextField1TextColor;

    public AppColor() {
        int[] RGB = readFromConfigurationFile("mainFrameColor");
        mainFrameColor = new Color(RGB[0], RGB[1], RGB[2]);
        System.out.println(mainFrameColor.toString());
        RGB = readFromConfigurationFile("jButton1");
        jButton1 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton2");
        jButton2 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton3");
        jButton3 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton4");
        jButton4 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton5");
        jButton5 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton6");
        jButton6 = new Color(RGB[0], RGB[1], RGB[2]);
        
        RGB = readFromConfigurationFile("jButton7");
        jButton7 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton8");
        jButton8 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton9");
        jButton9 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton10");
        jButton10 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton11");
        jButton11 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton12");
        jButton12 = new Color(RGB[0], RGB[1], RGB[2]);
        
        RGB = readFromConfigurationFile("jButton13");
        jButton13 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton14");
        jButton14 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton15");
        jButton15 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton16");
        jButton16 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton17");
        jButton17 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jButton18");
        jButton18 = new Color(RGB[0], RGB[1], RGB[2]);
        
        RGB = readFromConfigurationFile("jTextField1");
        jTextField1 = new Color(RGB[0], RGB[1], RGB[2]);
        
        RGB = readFromConfigurationFile("jTextField1TextColor");
        jTextField1TextColor = new Color(RGB[0], RGB[1], RGB[2]);

        RGB = readFromConfigurationFile("jLabel1");
        jLabel1 = new Color(RGB[0], RGB[1], RGB[2]);

        RGB = readFromConfigurationFile("jPanel1");
        jPanel1 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jPanel2");
        jPanel2 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jPanel3");
        jPanel3 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jPanel4");
        jPanel4 = new Color(RGB[0], RGB[1], RGB[2]);
        RGB = readFromConfigurationFile("jPanel5");
        jPanel5 = new Color(RGB[0], RGB[1], RGB[2]);
    }

    public int[] readFromConfigurationFile(String key) {

        final String csvFile = "color.csv";

        BufferedReader br = null;
        String line = "";
        final String cvsSplitBy = ",";
        try {
            //File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
            //System.out.println(jarDir.getAbsolutePath());
            String dir = (new File("dir").getAbsolutePath().replace("dir", ""));
            //System.out.println(dir);
            br = new BufferedReader(new FileReader(new File(dir, csvFile)));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                //System.out.println(line);
                String[] value = line.split(cvsSplitBy);

                if (value.length != 4) {
                    throw new IOException(csvFile + " should be CSV with 4 columns: <key>,<value1,value2,value3>");
                }

                if (value[0].equals(key)) {
                    br.close();
                    int[] RGB = new int[value.length - 1];
                    for (int i = 0; i < RGB.length; i++) {
                        RGB[i] = Integer.parseInt(value[i + 1]);
                    }
                    return RGB;
                }
            }
            throw new IOException("Cannot find key '" + key + "' within configuration value '" + csvFile + "'");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null; // or "-1" check with Markus.
    }
}
