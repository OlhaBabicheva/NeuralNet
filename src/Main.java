import Data.CsvReader;
import Data.ImageConverter;
import Data.LabeledImage;
import Data.Matrix;
import network.NeuralNetwork;
import layers.FullyConnectedLayer;
import layers.Layer;

import java.util.*;

import static java.util.Collections.shuffle;

public class Main {
    public static void main(String[] args) {
        final long SEED = 513224;
        int miniBatchSize = 4;
        double learningRate = 0.6;

        // Temporary network creation
        List<Layer> layers = new ArrayList<>();
        FullyConnectedLayer fcl1 = new FullyConnectedLayer(784, 30, SEED, learningRate, miniBatchSize);
        layers.add(fcl1);
        FullyConnectedLayer fcl2 = new FullyConnectedLayer(30, 10, SEED, learningRate, miniBatchSize);
        layers.add(fcl2);

        NeuralNetwork net = new NeuralNetwork(layers, 255);

        // Type your paths for mnist dataset
        String trainPath = "C:\\Users\\Mati\\Desktop\\mnist_train.csv";
        String testPath = "C:\\Users\\Mati\\Desktop\\mnist_test.csv";
        
        List<LabeledImage> imagesTrain = new CsvReader().readCsv(trainPath);
        List<LabeledImage> imagesTest = new CsvReader().readCsv(testPath);
        
        int epochs = 20;
        float rate = 0;
        
        for(int i = 0; i < epochs; i++){
            shuffle(imagesTrain);
            shuffle(imagesTest);

            // Every element of this List is a MiniBatch consisting of many LabeledImages
            List<LabeledImage[]> miniBatches = new ArrayList<>();
            for (int k = 0; k < 60000; k += miniBatchSize) {
                LabeledImage[] images = imagesTrain.subList(k, k + miniBatchSize).toArray(new LabeledImage[0]);
                miniBatches.add(images);
            }
            float m = 0;
            for (LabeledImage[] miniBatch:miniBatches) {
                float percent = m/miniBatches.size()*100;
                System.out.print("Round " + (i+1) + ": " + Math.round(percent) + "%  [" + "#".repeat(Math.round(percent/10)) + " ".repeat(10 - Math.round(percent/10)) + "]\r");
                net.train(miniBatch);
                m++;
            }

            // Checking network accuracy
            rate = net.test(imagesTest);

            System.out.println("Success rate after round " + (i+1) + ": " + String.format("%.4f", rate) + ", Average cost: " + String.format("%.4f", net.average));
        }
        while (true){
            Scanner sc = new Scanner(System.in);
            System.out.print("Path to image: ");
            String path = sc.nextLine();
            Matrix image = new Matrix(ImageConverter.convertImage(path));
            image.printAsImage();
            System.out.println("Predicted: " + net.predict(image));
        }
    }
}
