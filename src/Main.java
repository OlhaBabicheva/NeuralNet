import Data.CsvReader;
import Data.ImageConverter;
import Data.LabeledImage;
import Data.Matrix;
import network.NeuralNetwork;
import layers.FullyConnectedLayer;
import layers.Layer;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static java.util.Collections.shuffle;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Scanner sc = new Scanner(System.in);
        // System.out.print("Path to image: ");
        // String path = sc.nextLine();
        // double[][] image = ImageConve2rter.convertImage(path);

        int miniBatchSize = 5;
        double learningRate = 1.5;

        // List<Layer> layers = new ArrayList<>();
        // FullyConnectedLayer fcl1 = new FullyConnectedLayer(784, 30, 2137, learningRate, miniBatchSize);
        // layers.add(fcl1);
        // FullyConnectedLayer fcl2 = new FullyConnectedLayer(30, 10, 2137, learningRate, miniBatchSize);
        // layers.add(fcl2);

        // NeuralNetwork net = new NeuralNetwork(layers, 255);

        String trainPath = "C:\\Users\\Mati\\Desktop\\mnist_train.csv";
        String testPath = "C:\\Users\\Mati\\Desktop\\mnist_test.csv";

        List<LabeledImage> imagesTrain = new CsvReader().readCsv(trainPath);
        List<LabeledImage> imagesTest = new CsvReader().readCsv(testPath);
        
        float rate = 0;
        int epochs = 1;

        for(int i = 0; i < epochs; i++){
            shuffle(imagesTest);
            shuffle(imagesTrain);

            List<LabeledImage[]> miniBatches = new ArrayList<>();

            for (int k = 0; k < 60000; k += miniBatchSize) {
                LabeledImage[] images = imagesTrain.subList(k, k + miniBatchSize).toArray(new LabeledImage[0]);
                miniBatches.add(images);
            }

            
            // net.train(imagesTrain);

            // rate = net.test(imagesTest);

            // System.out.println("Success rate after round " + i + ": " + rate);
        }
    }
}