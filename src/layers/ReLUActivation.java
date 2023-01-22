package layers;

import Data.Matrix;

// ReLUActivation class
public class ReLUActivation implements ActivationFunction {
    @Override
    public Matrix apply(Matrix input) {
        double[][] Array = input.getArray();
        Matrix X = new Matrix(input.getnRows(), input.getnCols());

        double[][] C = X.getArray();

        for (int row = 0; row < input.getnRows(); row++) {
            for (int col = 0; col < input.getnCols(); col++) {
                C[row][col] = Array[row][col] <= 0 ? 0 : Array[row][col];
            }
        }
        return X;
    }

    public Matrix applyDerivative(Matrix input) {
        double[][] Array = input.getArray();
        Matrix X = new Matrix(input.getnRows(), input.getnCols());

        double[][] C = X.getArray();
        double leak = 0.01;
        for (int row = 0; row < input.getnRows(); row++) {
            for (int col = 0; col < input.getnCols(); col++) {
                C[row][col] = Array[row][col] <= 0 ? leak : 1;
            }
        }
        return X;
    }
}
