
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

    public NeuralNetwork(List<Layer> _layers, double scaleFactor) {
        this._layers = _layers;
        this.scaleFactor = scaleFactor;
        linkLayers();
    }

    public List<Layer> getLayers() {
        return _layers;
    }

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

    public Matrix costFunction(Matrix networkOutput, Matrix correctAnswer) {
        return networkOutput.minusMatrix(correctAnswer);
    }

    public Matrix costFunctionDerivative(Matrix networkOutput, Matrix correctAnswer) {
        return correctAnswer.minusMatrix(networkOutput);
    }

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

    public float test (List<LabeledImage> images) {
        int correct = 0;

        for(LabeledImage img: images) {
            int guess = guess(img);

            if (guess == img.getLabel()) {
                correct++;
            }
        }
        return((float)correct/images.size());
    }

    public void train (LabeledImage[] miniBatch) {
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

        // Backprop
        _layers.get((_layers.size()-1)).backPropagation(deltaLast);

    }
}
