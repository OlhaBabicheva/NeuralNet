package Data;

import java.util.Arrays;

public class Matrix {
    protected double[][] A;
    protected int nRows;
    protected int nCols;


  /**
   * Construct an rows x cols Matrix filled with 0.
   * 
   * @param rows Number of rows.
   * @param cols Number of colums.
   */
    public Matrix(int rows, int cols) {
        nRows = rows;
        nCols = cols;
        A = new double[nRows][nCols];
    }

  /**
   * Construct Matrix from given arary.
   * 
   * @param array array of doubles to convert into Matrix.
   */
    public Matrix(double[][] array) {
        nRows = array.length;
        nCols = array[0].length;
        this.A = array;
    }

  /**
   * Construct an rows x cols Matrix filled with given scalar.
   * 
   * @param rows Number of rows.
   * @param cols Number of colums.
   * @param scalar Fill the matrix with this scalar value.
   */
    public Matrix(int rows, int cols, double scalar) {
        nRows = rows;
        nCols = cols;
        A = new double[nRows][nCols];

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
              A[row][col] = scalar;
            }
        }
    }

    public double[][] getArray() {
        return this.A;
    }

    public int getnRows() {
        return this.nRows;
    }

    public int getnCols() {
        return this.nCols;
    }

  /**
   * Matrix transpose.
   * 
   * @return A'
   */
  public Matrix transpose() {
        Matrix X = new Matrix(nCols, nRows);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                C[col][row] = A[row][col];
            }
        }
        return X;
    }

    /**
     * Standard Matrix multiplication A * B
     * 
     * @param B Matrix
     * @return Matrix product A * B
     * @throws ArithmeticException Matrix dimensions do not match
     */
    public Matrix product(Matrix B) {
        if (this.nCols != B.nRows) {
            throw new ArithmeticException("Dimensions do not match: " + this.nCols + "!=" + B.nRows);
        }

        Matrix X = new Matrix(this.nRows, B.nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < B.nCols; col++) {
                for (int i = 0; i < nCols; i++) {
                    C[row][col] += A[row][i] * B.A[i][col];
                }   
            }
        }
        return X;
    }

    /**
     * Hadamard Matrix product, where C[0][0] = A[0][0] * B[0][0] for every entry in Matrix
     * 
     * @param B Matrix
     * @return A * B
     * @throws ArithmeticException Matrix dimensions do not match
     */
    public Matrix productHadamard(Matrix B) {
        if (this.nRows != B.nRows || this.nCols != B.nCols) {
            throw new ArithmeticException("Dimensions must be the same");
        }

        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
              C[row][col] = A[row][col] * B.A[row][col];
            }
        }
        return X;
    }

    /**
     * Matrix addition C = A + B
     * 
     * @param B Matrix
     * @return Matrix addition A + B
     * @throws ArithmeticException Matrix dimensions do not match
     */
    public Matrix addMatrix(Matrix B) {
        if (this.nRows != B.nRows || this.nCols != B.nCols) {
            throw new ArithmeticException("Dimensions must be the same");
        }

        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                C[row][col] = A[row][col] + B.A[row][col];
            }
        }
        return X;
    }

    /**
     * Matrix subtraction C = A - B
     * 
     * @param B Matrix
     * @return Matrix subtraction A - B
     * @throws ArithmeticException Matrix dimensions do not match
     */
    public Matrix minusMatrix(Matrix B) {
        if (this.nRows != B.nRows || this.nCols != B.nCols) {
            throw new ArithmeticException("Dimensions must be the same");
        }

        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                C[row][col] = A[row][col] - B.A[row][col];
            }
        }
        return X;
    }

    /**
     * Multiply Matrix by scalar, C = A*s
     * 
     * @param s scalar
     * @return A*s
     */
    public Matrix timesScalar(double scalar) {
        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
              C[row][col] = scalar * A[row][col];
            }
        }
        return X;
    }

    public double[] toVector() {
        int newRows = nRows * nCols;
        double[] C = new double[newRows];

        int i = 0;
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
              C[i] = A[row][col];
              i++;
            }
        }
        return C;
    }

    public Matrix sumOverColumns() {
        Matrix X = new Matrix(nRows, 1);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            int sum = 0;
            for (int col = 0; col < nCols; col++) {
                sum += A[row][col]; 
            }
            C[row][0] = sum;
        }
        return X;
    }

    /**
     * Broadcasts Matrix B in order to perform addition, only works for broadcasting columns
     * 
     * @param B Matrix
     * @return C = A + B
     */
    public Matrix broadcastAddMatrix(Matrix B) {
        if (this.nRows != B.nRows) {
            throw new ArithmeticException("Dimensions must be the same");
        }
        if (this.nCols == B.nCols) {
            return this.addMatrix(B);
        }

        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                C[row][col] = A[row][col] + B.A[row][0];
            }
        }
        return X;
    }

    public Matrix applyReLu() {
        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
              C[row][col] = A[row][col] <= 0 ? 0 : A[row][col];
            }
        }
        return X;
    }

    public Matrix applyDerivativeReLu(double leak) {
        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
              C[row][col] = A[row][col] <= 0 ? leak : 1;
            }
        }
        return X;
    }

    public double Sigmoid(double z) {
        if (z >= 0)
            return 1/(1.0 + Math.exp(-z));
        else {
            double e = Math.exp(z);
            return e / (1.0+e);
        }
    }

    public Matrix applySigmoid() {
        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                C[row][col] = Sigmoid(A[row][col]);
            }
        }
        return X;
    }

    public Matrix applyDerivativeSigmoid() {
        Matrix X = new Matrix(nRows, nCols);
        double[][] C = X.getArray();

        double sigmoid;
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                sigmoid = Sigmoid(A[row][col]);
                C[row][col] = sigmoid * (1.0-sigmoid);
            }
        }
        return X;
    }

    /**
     * Static method to print Matrix
     * 
     * @param A Matrix
     */
    public static void printMatrix(Matrix A) {
        double[][] C = A.getArray();

        for (int i = 0; i < C.length; i++) {
            System.out.println(Arrays.toString(C[i]));
        }
    }
}
