package Data;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageConverter {
    private static final int img_size = 28;

    public static double[][] convertImage(String path) {
        double[][] grayscale = new double[img_size][img_size];
        Scanner sc = new Scanner(System.in);
        File file = new File(path);
        while(!file.exists()){
            System.out.print("Provide correct path to image: ");
            path = sc.nextLine();
            file = new File(path);
        }
        try {
            BufferedImage original_image = ImageIO.read(file);
            Image rescaledImage = original_image.getScaledInstance(img_size, img_size, Image.SCALE_DEFAULT);
            BufferedImage image = new BufferedImage(img_size, img_size, BufferedImage.TYPE_BYTE_GRAY);
            image.getGraphics().drawImage(rescaledImage, 0, 0, null);

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int p = image.getRGB(x,y);
                    grayscale[y][x] = Math.round((Math.round(p*-1)/16777216.0)*256);
                    System.out.print(grayscale[y][x] + " ");
                }
                System.out.println();
            }
        }catch(IOException e) {
            System.out.println(e);
        }

        return grayscale;
    }
}