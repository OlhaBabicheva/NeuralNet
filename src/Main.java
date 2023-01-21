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

        int miniBatchSize = 16;
        double learningRate = 0.5;

        List<Layer> layers = new ArrayList<>();
        FullyConnectedLayer fcl1 = new FullyConnectedLayer(784, 30, 513223, learningRate, miniBatchSize);
        layers.add(fcl1);
        FullyConnectedLayer fcl2 = new FullyConnectedLayer(30, 10, 513223, learningRate, miniBatchSize);
        layers.add(fcl2);

        NeuralNetwork net = new NeuralNetwork(layers, 255);

        String trainPath = "C:\\Users\\Mati\\Desktop\\mnist_train.csv";
        String testPath = "C:\\Users\\Mati\\Desktop\\mnist_test.csv";

        List<LabeledImage> imagesTrain = new CsvReader().readCsv(trainPath);
        List<LabeledImage> imagesTest = new CsvReader().readCsv(testPath);
        
        float rate = 0;
        int epochs = 5;

        for(int i = 0; i < epochs; i++){
            shuffle(imagesTrain);
            shuffle(imagesTest);

            List<LabeledImage[]> miniBatches = new ArrayList<>();

            for (int k = 0; k < 60000; k += miniBatchSize) {
                LabeledImage[] images = imagesTrain.subList(k, k + miniBatchSize).toArray(new LabeledImage[0]);
                miniBatches.add(images);
            }

            System.out.println("Training!!!");
            for (LabeledImage[] miniBatch:miniBatches) {
                net.train(miniBatch);
            }

            rate = net.test(imagesTest);

            System.out.println("Success rate after round " + i + ": " + rate);
        }

        List<Layer> asd = net.getLayers();
        // Matrix.printMatrix(asd.get(1).getweight());

    }
}