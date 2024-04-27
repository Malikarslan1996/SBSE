package guioptimiser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class GuiOptimiser {


    private static final int ITERATION = 5;
    private static String TARGET_APP = "calculator.jar";
    private static final String TARGET_APP_COLOR = "color.csv";
    private static final int MAX_INTENSITY = 255;
    private static final int TARGET_APP_RUNNINGTIME = 1000;
    private static final String JAVA_COMMAND = "java -jar ";
    private static String parentDir = "";

    public static void main(String[] args) {
        switch (args[0]) {
            case "calculator.jar":
            case "simpleApp.jar":
                TARGET_APP = args[0].trim();
                System.out.println(args[0]);
                break;
            default:
                System.out.println("Invalid app name. Exiting.");
                return;
        }
        parentDir = getParentDir();
        for (int i = 0; i < ITERATION; i++) {
            runApp(TARGET_APP, TARGET_APP_RUNNINGTIME);
            changeColorAll(i == 0);  // Append on iterations after the first
        }
        calculateAndDisplayEnergyConsumption(parentDir.concat(TARGET_APP_COLOR));
    }

    public static void runApp(String path, int targetAppRunningtime) {
        try {
            System.out.println("Target App: " + path);
            Process process = Runtime.getRuntime().exec(JAVA_COMMAND.concat(path));
            Thread.sleep(targetAppRunningtime);
            new Capture().takeScreenShoot();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void changeColorAll(boolean isFirstIteration) {
        ArrayList<String> guiComponents = new ArrayList<>(Arrays.asList(
                "mainFrameColor", "jButton1", "jButton2", "jButton3", "jButton4", "jButton5", "jButton6", "jButton7",
                "jButton8", "jButton9", "jButton10", "jButton11", "jButton12", "jButton13", "jButton14", "jButton15",
                "jButton16", "jButton17", "jButton18", "jTextField1", "jTextField1TextColor", "jLabel1",
                "jPanel1", "jPanel2", "jPanel3", "jPanel4", "jPanel5"));
        ArrayList<ArrayList<Integer>> RGB = new ArrayList<>();
        randomSearch(guiComponents,RGB, isFirstIteration);
    }

    public static void randomSearch(ArrayList<String> guiComponents,ArrayList<ArrayList<Integer>> RGB, boolean isFirstIteration){

        Random randomInt = new Random();
        for (int i = 0; i < guiComponents.size(); i++) {
            RGB.add(new ArrayList<>(Arrays.asList(randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256))));
        }
        saveToCSV(parentDir.concat(TARGET_APP_COLOR), guiComponents, RGB, !isFirstIteration);
    }





    public static void calculateAndDisplayEnergyConsumption(String filePath) {
        try {
            File inputFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            ArrayList<String> updatedLines = new ArrayList<>();
            String line;
            double totalEnergyConsumption = 0;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int r = Integer.parseInt(parts[1]);
                    int g = Integer.parseInt(parts[2]);
                    int b = Integer.parseInt(parts[3]);

                    double average = (r + g + b) / 3.0;
                    double normalization = average / MAX_INTENSITY;
                    double energyConsumption = average + normalization;

                    line += "," + String.format("%.2f", energyConsumption);
                    totalEnergyConsumption += energyConsumption;
                    count++;
                }
                updatedLines.add(line);
                if (count == 27) {  // Assuming there are 26 components\
                    double averageEnergy = totalEnergyConsumption / count;
                    updatedLines.add("Total energy consumption = " + String.format("%.2f", totalEnergyConsumption) +
                            " / " + count + " = " + String.format("%.2f", averageEnergy));
                    totalEnergyConsumption = 0; // Reset for next iteration
                    count = 0; // Reset component count for next iteration
                }

            }
            reader.close();

            // Write updated lines back to the CSV
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    public static void saveToCSV(String filePath, ArrayList<String> guiComponents, ArrayList<ArrayList<Integer>> RGB, boolean append) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(new File(filePath), append))) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < guiComponents.size(); i++) {
                line.append(guiComponents.get(i))
                        .append(",")
                        .append(RGB.get(i).toString().replace("[", "").replace("]", "").replaceAll("\\s", ""))
                        .append("\n");
            }
            br.write(line.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getParentDir() {
        try {
            File temp = new File("temp");
            return temp.getAbsolutePath().replace("temp", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
