package network;

import layers.Layer;
import Data.LabeledImage;
import Data.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NeuralNetwork {

    List<Layer> _layers;
    double scaleFactor;
    public double average;

    public NeuralNetwork(List<Layer> _layers, double scaleFactor) {
        this._layers = _layers;
        this.scaleFactor = scaleFactor;
        linkLayers();
    }

    /**
     * Goes through all layers and connects them to each other.
     * 
     */
    private void linkLayers() {
        if (_layers.size() <= 1) {
            return;
        }

        for (int i = 0; i < _layers.size(); i++) {

            if (i == 0) {
                _layers.get(i).set_nextLayer(_layers.get(i+1));
            } 
            else if (i == _layers.size()-1) {
                _layers.get(i).set_previousLayer(_layers.get(i-1));
            } 
            else {
                _layers.get(i).set_previousLayer(_layers.get(i-1));
                _layers.get(i).set_nextLayer(_layers.get(i+1));
            }
        }
    }

    /**
     * Computes cost function value and returns Matrix of dimensions 1xMiniBatchSize
     * 
     * @return Matrix sum(y - a)^2
     */
    public Matrix costFunction(Matrix networkOutput, Matrix correctAnswer) {
        Matrix inside = correctAnswer.minusMatrix(networkOutput);
        return inside.productHadamard(inside).sumOverRows();
    }

    /**
     * Computes cost function derivative
     * 
     * @return Matrix (a - y)
     */
    public Matrix costFunctionDerivative(Matrix networkOutput, Matrix correctAnswer) {
        Matrix result = networkOutput.minusMatrix(correctAnswer);
        double[][] res_arr = result.getArray();
        List<Double> err = new ArrayList<>();
        for(int i = 0; i < result.getnRows(); ++i){
            for(int j = 0; j < result.getnCols(); ++j){
                err.add(Math.abs(res_arr[i][j]));
            }
        }
        Double[] Cost = new Double[err.size()];
        err.toArray(Cost);
        average = err.stream().mapToDouble(val -> val).average().orElse(0.0);
        return result;
    }

    /**
     * Finds index where, output is the highest
     * 
     * @return location of max
     */
    private int getMaxIndex(Matrix in) {
        double[][] array = in.getArray();
        double max = 0;
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] >= max) {
                    max = array[i][j];
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * Converts image to a nice Matrix 784x1 then gives this image to network
     * and uses getMaxIndex to find highest activation, ie. network prediction
     * 
     * @return location of max
     */
    public int guess(LabeledImage image) {

        double[][] data = new double[784][1];
        double[] vector;

        vector = image.getData().timesScalar(1.0/255).toVector();

            for (int i = 0; i < 784; i++) {
                data[i][0] = vector[i];
            }

        Matrix imData = new Matrix(data);

        Matrix out = _layers.get(0).getOutput(imData);
        return getMaxIndex(out);
    }

    public int predict(Matrix image) {
        double[][] data = new double[784][1];
        double[] vector;
        vector = image.timesScalar(1.0/255).toVector();

        for (int i = 0; i < 784; i++) {
            data[i][0] = vector[i];
        }

        Matrix imData = new Matrix(data);

        Matrix out = _layers.get(0).getOutput(imData);
        return getMaxIndex(out);
    }

    /**
     * Takes test images and computes our network accuracy
     * 
     * @return precision
     */
    public float test (List<LabeledImage> images) {
        int correct = 0;

        for(LabeledImage img: images) {
            int guess = guess(img);

            if (guess == img.getLabel()) {
                correct++;
            }
        }
        return ((float)correct/images.size());
    }

    /**
     * Converts LabeledImages batch to Matrix 784xMiniBatchSize, basically flattens every Image data
     * and puts in into a single column. So that we can input them into network. Does the same for image Labels.
     * Then scales down every pixel data to a number between (0, 1), and performs backpropagation. 
     */
    public void train (LabeledImage[] miniBatch) {
        // TODO: little bit of refactoring, maybe take this image preprocessing stuff and make it into a method,
        // Additionally we need nice printing like in Tensorflow, use Cariage Return for this to overwrite
        // current line when printing stuff, yeah and make nice printing, some progress bar maybe.

        double[][] inputBatch = new double[784][miniBatch.length];
        double[][] labelBatch = new double[10][miniBatch.length];
        double[] vector;
        double[] label;

        for (int n = 0; n < miniBatch.length; n++) {
            vector = miniBatch[n].getData().toVector();
            label = miniBatch[n].getLabelVector().toVector();

            for (int i = 0; i < 784; i++) {
                inputBatch[i][n] = vector[i];
            }

            for (int j = 0; j < 10; j++) {
                labelBatch[j][n] = label[j];
            }
        }

        Matrix imData = new Matrix(inputBatch).timesScalar(1.0/255);
        Matrix imLabel = new Matrix(labelBatch);

        // Forward Pass
        Matrix out = _layers.get(0).getOutput(imData);

        // Cost derivative
        Matrix deltaLast = costFunctionDerivative(out, imLabel);

        // Value of loss function
        Matrix lossValue = costFunction(out, imLabel);

        // Backprop
        _layers.get((_layers.size()-1)).backPropagation(deltaLast);

    }
}
