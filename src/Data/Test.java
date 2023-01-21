package Data;

public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 3, 5);
        // Matrix x = m.sumOverColumns().addMatrix(m);

        Matrix.printMatrix(m);

        // Matrix d = new Matrix(3, 1, 2);
        // Matrix.printMatrix(d);
        // Matrix.printMatrix(x.broadcastAddMatrix(d));
    }
}
