package layers;

import java.util.Random;

import Data.Matrix;

public class FullyConnectedLayer extends Layer {

    private long SEED;
    private final double leak = 0.01;

    private int miniBatchSize;

    private int _inLength;
    private int _outLength;
    private double _learningRate;

    public Matrix getweight() {
        return weights;
    }

    public FullyConnectedLayer(int _inLength, int _outLength, long SEED, double learningRate, int miniBatchSize) {
        this._inLength = _inLength;
        this._outLength = _outLength;
        this.SEED = SEED;
        this._learningRate = learningRate;
        this.miniBatchSize = miniBatchSize;

        setRandomBiases();
        setRandomWeights();
    }

    public Matrix fullyConnectedForwardPass(Matrix input) {
        if (_previousLayer == null)
            this.dataX = input;   

        this.lastZ = weights.product(input).broadcastAddMatrix(biases);
        this.lastX = this.lastZ.applySigmoid();

        return this.lastX;
    }

    @Override
    public Matrix getOutput(Matrix input) {
        Matrix forwardPass = fullyConnectedForwardPass(input);

        if (_nextLayer != null)
            return _nextLayer.getOutput(forwardPass);
        else 
            return forwardPass;
        
    }

    @Override
    public void backPropagation(Matrix error) {
        Matrix nablaBiases;
        Matrix nablaWeights;

        // If it's the last layer
        if (_nextLayer == null) {
            error = error.productHadamard(lastZ.applyDerivativeSigmoid());
            nablaBiases = error;
            nablaWeights = error.product(_previousLayer.lastX.transpose());
        }
        else {
            error = _nextLayer.weights.transpose().product(error).productHadamard(lastZ.applyDerivativeSigmoid());
            nablaBiases = error;

            if (_previousLayer == null)
                nablaWeights = error.product(dataX.transpose());
            else
                nablaWeights = error.product(_previousLayer.lastX.transpose());
        }

        this.biases = this.biases.minusMatrix(nablaBiases.sumOverColumns().timesScalar(_learningRate/miniBatchSize));
        this.weights = this.weights.minusMatrix(nablaWeights.timesScalar(_learningRate/miniBatchSize));


        if (_previousLayer != null) {
            _previousLayer.backPropagation(error);
        } 
    }

    public void setRandomWeights(){

        double limit = Math.sqrt(6.0/(_inLength+_outLength));
        Random random = new Random(SEED);

        double[][] init = new double[_outLength][_inLength];
        
        for(int i = 0; i < _outLength; i++) {
            for(int j = 0; j < _inLength; j++) {
                init[i][j] = random.nextDouble(-limit, limit);
            }
        }
        this.weights = new Matrix(init);
    }

    public void setRandomBiases(){
        Random random = new Random(SEED);

        double[][] init = new double[_outLength][1];
        
        for(int i = 0; i < _outLength; i++) {
            init[i][0] = 0;
        }
        this.biases = new Matrix(init);
    }
}
