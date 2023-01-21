package layers;

import java.util.Random;

import Data.Matrix;

public class FullyConnectedLayer extends Layer {

    private long SEED;
    private final double leak = 0.01;

    private Matrix weights;
    private Matrix biases;
    private int miniBatchSize;

    private int _inLength;
    private int _outLength;
    private double _learningRate;

    private Matrix lastZ;
    private Matrix lastX;


    public FullyConnectedLayer(int _inLength, int _outLength, long SEED, double learningRate, int miniBatchSize) {
        this._inLength = _inLength;
        this._outLength = _outLength;
        this.SEED = SEED;
        this._learningRate = learningRate;
        this.miniBatchSize = miniBatchSize;

        this.biases = new Matrix(_outLength, miniBatchSize);
        setRandomWeights();
    }

    public Matrix fullyConnectedForwardPass(Matrix input) {

        lastZ = weights.product(input).addMatrix(biases);
        lastX = lastZ.applyReLu();

        // Matrix.printMatrix(lastX);
        System.out.println("/////////////////////////////////////////////");
        System.out.println(lastX.getnRows() + "|||" + lastX.getnCols());
        System.out.println("/////////////////////////////////////////////");
        return lastX;
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
            error = error.productHadamard(lastZ.applyDerivativeReLu(leak));
            nablaBiases = error;
            nablaWeights = error.product(lastX);
        }
        else {
            error = weights.transpose().product(error).productHadamard(lastX.applyDerivativeReLu(leak));
            nablaBiases = error;
            nablaWeights = error.product(lastX);
        }

        this.biases = this.biases.minusMatrix(nablaBiases.timesScalar(_learningRate/miniBatchSize));
        this.weights = this.weights.minusMatrix(nablaWeights.timesScalar(_learningRate/miniBatchSize));


        if (_previousLayer != null) {
            _previousLayer.backPropagation(error);
        }
        
    }

    public void setRandomWeights(){
        Random random = new Random(SEED);

        double[][] init = new double[_outLength][_inLength];
        
        // 
        for(int i = 0; i < _outLength; i++) {
            for(int j = 0; j < _inLength; j++) {
                init[i][j] = random.nextGaussian();
            }
        }
        this.weights = new Matrix(init);
    }

    public void ReLu(Matrix A) {
        A.applyReLu();
    }

    public void derivativeReLu(Matrix A) {
        A.applyDerivativeReLu(leak);
    }
    
}
