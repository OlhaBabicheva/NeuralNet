package Data;

public class Matrix {
    private double[][] data;
    private int nRows;
    private int nCols;

    public Matrix(int rows, int cols) {
        nRows = rows;
        nCols = cols;
        data = new double[nRows][nCols];
    }

    public Matrix(double[][] array) {
        nRows = array.length;
        nCols = array[0].length;
        data = array;
    }


    public int getnRows() {
        return this.nRows;
    }

    public int getnCols() {
        return this.nCols;
    }

    public static double[][] multiply(Matrix A, Matrix B) {
        
    }

    public static double[][] add(Matrix A, Matrix B) {

        
    }
}
