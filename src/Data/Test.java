package Data;

import Data.Matrix;

public class Test {
    public static void main(String[] args) {
        Matrix A = new Matrix(3, 4);
        Matrix B = new Matrix(3, 3, 4);

        double[][] C = A.getArray();
        C[2][1] = 5.0;
        Matrix.printMatrix(A.transpose());
    }
}
