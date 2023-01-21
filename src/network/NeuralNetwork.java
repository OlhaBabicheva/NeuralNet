
package network;

import layers.Layer;
import Data.LabeledImage;
import Data.Matrix;

import java.util.ArrayList;
import java.util.List;


public class NeuralNetwork {

    List<Layer> _layers;
    double scaleFactor;

    public NeuralNetwork(List<Layer> _layers, double scaleFactor) {
        this._layers = _layers;
        this.scaleFactor = scaleFactor;
        linkLayers();
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
        Matrix imData = image.getData().timesScalar(1.0/255);

        Matrix out = _layers.get(0).getOutput(imData);
        int guess = getMaxIndex(out);

        return guess;
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

    public void train (List<LabeledImage> images) {
        
        for(LabeledImage img:images) {
            
            Matrix imData = img.getData().timesScalar(1.0/255);
            Matrix imLabel = img.getLabelVector();        

            // Forward Pass
            Matrix out = _layers.get(0).getOutput(imData);

            // Cost derivative
            Matrix deltaLast = costFunctionDerivative(out, imLabel);

            // Backprop
            _layers.get((_layers.size()-1)).backPropagation(deltaLast);
        }

    }
}