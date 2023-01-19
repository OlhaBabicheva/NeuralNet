import Data.CsvReader;
import Data.ImageConverter;
import Data.LabeledImage;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Path to image: ");
        String path = sc.nextLine();
        double[][] image = ImageConverter.convertImage(path);

        System.out.print("Path to dataset: ");
        String d_path = sc.nextLine();
        List<LabeledImage> images = new CsvReader().readCsv(d_path);
        images.get(0).printData();
    }
}