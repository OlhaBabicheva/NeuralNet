package Data;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix(3, 3, 5);
        // Matrix x = m.sumOverColumns().addMatrix(m);

        Matrix.printMatrix(m.applyDerivativeSigmoid());

        // Matrix d = new Matrix(3, 1, 2);
        // Matrix.printMatrix(d);
        // Matrix.printMatrix(x.broadcastAddMatrix(d));

        double limit = Math.sqrt(6.0/(784 + 30));
        Random random = new Random();

        double[][] init = new double[784][30];
        
        for(int i = 0; i < 784; i++) {
            for(int j = 0; j < 30; j++) {
                init[i][j] = random.nextDouble(-limit, limit);
            }
        }
        Matrix weights = new Matrix(init);
        Matrix.printMatrix(weights);

    }
}
