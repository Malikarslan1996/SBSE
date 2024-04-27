package guioptimiser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChargeCalculator {
    private static final double RED_MAX_CHARGE = 131.0; // Max charge for red at full intensity
    private static final double GREEN_MAX_CHARGE = 142.0; // Max charge for green at full intensity
    private static final double BLUE_MAX_CHARGE = 241.0; // Max charge for blue at full intensity
    private static final int MAX_INTENSITY = 255;
    private static final int NEXUS_PIXELS = 3686400; // Total pixels in Nexus 6

    public static double calculateChargeConsumptionPerPixel(int red, int green, int blue) {
        double redCharge = (red / (double) MAX_INTENSITY) * RED_MAX_CHARGE;
        double greenCharge = (green / (double) MAX_INTENSITY) * GREEN_MAX_CHARGE;
        double blueCharge = (blue / (double) MAX_INTENSITY) * BLUE_MAX_CHARGE;
        return redCharge + greenCharge + blueCharge;
    }

    public static double calculateImageCharge(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            int width = img.getWidth();
            int height = img.getHeight();
            double totalCharge = 0;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = img.getRGB(x, y);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    totalCharge += calculateChargeConsumptionPerPixel(red, green, blue);
                }
            }
            // Adjust charge based on the resolution difference
            return totalCharge * NEXUS_PIXELS / (double) (width * height);
        } catch (IOException e) {
            System.err.println("Error reading the image file.");
            return 0;
        }
    }

    public static void main(String[] args) {
        String[] imagePaths = {"/Users/arslanmalik/Documents/SBSE/sbseAssignment2/GuiOptimiser/src/image1.png", "/Users/arslanmalik/Documents/SBSE/sbseAssignment2/GuiOptimiser/src/image2.JPG", "/Users/arslanmalik/Documents/SBSE/sbseAssignment2/GuiOptimiser/src/image3.png"};
        int i = 1;
        for (String path : imagePaths) {
            double totalCharge = calculateImageCharge(path);
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(path));
            } catch (IOException e) {
                System.err.println("Failed to read image file: " + path);
                continue;
            }
            int width = img.getWidth();
            int height = img.getHeight();
            int totalPixels = width * height;

            System.out.println("Total charge for image " + i + ": " + totalCharge + " mAh");
            System.out.println("Number of pixels in image " + i + ": " + totalPixels);
            i++;
        }
    }
}
