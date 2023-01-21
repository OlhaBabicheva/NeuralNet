package Data;

public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(5, 5, 5);
        Matrix.printMatrix(m.timesScalar(1.0/255).sumOverRows());
    }
}
