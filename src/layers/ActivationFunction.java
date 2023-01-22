package layers;

import Data.Matrix;

// ActivationFunction interface
public interface ActivationFunction {
    Matrix apply(Matrix input);
    Matrix applyDerivative(Matrix input);
}

