import Data.CsvReader;
import Data.ImageConverter;
import Data.LabeledImage;
import Data.Matrix;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        // System.out.print("Path to image: ");
        // String path = sc.nextLine();
        // double[][] image = ImageConve2rter.convertImage(path);

        System.out.print("Path to dataset: ");
        String d_path = "C:\\Users\\Mati\\Desktop\\mnist_test.csv";
        List<LabeledImage> images = new CsvReader().readCsv(d_path);

        Matrix m = new Matrix(images.get(0).getData());
        Matrix.printMatrix(m.timesScalar((double)1/255));

    }
}