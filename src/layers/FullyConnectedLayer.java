package layers;

import java.util.List;

public class FullyConnectedLayer extends Layer {

    private final double leak = 0.01;

    @Override
    public double[] getOutput(List<double[][]> input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double[] getOutput(double[] input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void backPropagation(double[] dLdO) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void backPropagation(List<double[][]> dLdO) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getOutputLength() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getOutputRows() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getOutputCols() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getOutputElements() {
        // TODO Auto-generated method stub
        return 0;
    }

    public double ReLu()
    
}
