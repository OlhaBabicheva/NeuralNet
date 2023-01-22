package layers;

import Data.Matrix;

// SigmoidActivation class
public class SigmoidActivation implements ActivationFunction {
    @Override
    public Matrix apply(Matrix input) {
        double[][] Array = input.getArray();
        Matrix X = new Matrix(input.getnRows(), input.getnCols());

        double[][] C = X.getArray();

        for (int row = 0; row < input.getnRows(); row++) {
            for (int col = 0; col < input.getnCols(); col++) {
                C[row][col] = Sigmoid(Array[row][col]);
            }
        }
        return X;
    }

    public Matrix applyDerivative(Matrix input) {
        double[][] Array = input.getArray();
        Matrix X = new Matrix(input.getnRows(), input.getnCols());

        double[][] C = X.getArray();
        double sigmoid;
        for (int row = 0; row < input.getnRows(); row++) {
            for (int col = 0; col < input.getnCols(); col++) {
                sigmoid = Sigmoid(Array[row][col]);
                C[row][col] = sigmoid * (1.0 - sigmoid);
            }
        }
        return X;
    }

    public double Sigmoid(double z) {
        if (z >= 0)
            return 1 / (1.0 + Math.exp(-z));
        else {
            double e = Math.exp(z);
            return e / (1.0 + e);
        }
    }

}
