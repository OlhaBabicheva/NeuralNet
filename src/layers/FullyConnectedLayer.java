package layers;

import Data.Matrix;
import java.util.Random;
import java.lang.Math;

public class FullyConnectedLayer extends Layer {

    private long SEED;
    private final double leak = 0.01;

    private int miniBatchSize;

    private int _inLength;
    private int _outLength;
    private double _learningRate;


    public FullyConnectedLayer(int _inLength, int _outLength, long SEED, double learningRate, int miniBatchSize,
                               ActivationFunction activationFunction) {
        this._inLength = _inLength;
        this._outLength = _outLength;
        this.SEED = SEED;
        this._learningRate = learningRate;
        this.miniBatchSize = miniBatchSize;
        this.activationFunction = activationFunction;

        setRandomBiases();
        setRandomWeights();
    }

    /**
     * Multiplies inputs by weights, adds bias vector, applies activation function. Keeps activation
     * and activation before function in order to perform backpropagation later on. 
     * 
     * @return Matrix of layer activation
     */
    public Matrix fullyConnectedForwardPass(Matrix input) {
        // Would be lovely if there was a way to choose activation function, 
        // but first there needs to be more than one working activation function...
        
        if (_previousLayer == null)
            this.dataX = input;   

        this.lastZ = weights.product(input).broadcastAddMatrix(biases);
        this.lastX = this.activationFunction.apply(this.lastZ);

        // Matrix.printMatrix(lastX);
        // System.out.println("/////////////////");
        return this.lastX;
    }








    // FullyConnectedLayer class
//    public class FullyConnectedLayer {
//        private ActivationFunction activationFunction;
//
//        public FullyConnectedLayer(ActivationFunction activationFunction) {
//            this.activationFunction = activationFunction;
//        }
//
//        public Matrix fullyConnectedForwardPass(Matrix input) {
//            if (_previousLayer == null)
//                this.dataX = input;
//
//            this.lastZ = weights.product(input).broadcastAddMatrix(biases);
//            this.lastX = this.activationFunction.apply(this.lastZ);
//
//            // Matrix.printMatrix(lastX);
//            // System.out.println("/////////////////");
//            return this.lastX;
//        }
//    }

    /**
     * Computes output of layer and forwards it to next layers if there are any.
     * 
     * @return Matrix of layer activation
     */
    public Matrix getOutput(Matrix input) {
        Matrix forwardPass = fullyConnectedForwardPass(input);

        if (_nextLayer != null)
            return _nextLayer.getOutput(forwardPass);
        else 
            return forwardPass;
        
    }

    /**
     * Implements backpropagation algorithm. It uses fully Matrix based approach, so it takes as input a Matrix
     * of 784xMiniBatchSize and thus propagates forwards and backwards efficently.
     */
    public void backPropagation(Matrix error) {
        Matrix nablaBiases;
        Matrix nablaWeights;

        // If it's the last layer
        if (_nextLayer == null) {
            error = error.productHadamard(this.activationFunction.applyDerivative(lastZ));
            nablaBiases = error;
            nablaWeights = error.product(_previousLayer.lastX.transpose());
        }
        else {
            error = _nextLayer.weights.transpose().product(error).productHadamard(this.activationFunction.applyDerivative(lastZ));
            nablaBiases = error;
            // If it's the first layer we take network inputs
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
    /**
     * Randomly initializes weights from normal distribiution
     */
    public void setRandomWeights(){
        // TO DO: IMPLEMENT ANOTHER METHODs TO RANDOMIZE NOT FROM 
        // NORMAL DISTRIBUTION BUT FROM GLOROT/XAVIER OR LECUN OR HE

        Random random = new Random(SEED);

        double[][] init = new double[_outLength][_inLength];
        
        for(int i = 0; i < _outLength; i++) {
            for(int j = 0; j < _inLength; j++) {
                init[i][j] = random.nextGaussian();
            }
        }
        this.weights = new Matrix(init);

    }
    public void setRandomWeightsGlorot(){
        // TO DO: IMPLEMENT ANOTHER METHODs TO RANDOMIZE NOT FROM
        // NORMAL DISTRIBUTION BUT FROM GLOROT/XAVIER OR LECUN OR HE

        Random random = new Random(SEED);

        double[][] init = new double[_outLength][_inLength];
        double range = Math.sqrt(6.0 / (_inLength + _outLength));
        for(int i = 0; i < _outLength; i++) {
            for(int j = 0; j < _inLength; j++) {
                init[i][j] = (random.nextDouble() * 2 * range) - range;
            }
        }
        this.weights = new Matrix(init);
    }


    /**
     * Initializes Biases with random values, currently with zeroes, because why not
     */
    public void setRandomBiases(){
        Random random = new Random(SEED);

        double[][] init = new double[_outLength][1];
        
        for(int i = 0; i < _outLength; i++) {
            init[i][0] = 0;
        }
        this.biases = new Matrix(init);
    }
}
