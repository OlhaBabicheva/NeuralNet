package Data;

public class LabeledImage {
    private Matrix data;
    private Matrix labelVector;
    private int label;

    public LabeledImage(double[][] data, int label) {
        this.data = new Matrix(data);
        this.label = label;
        this.labelVector = vectorizeLabel();
    }

    public Matrix vectorizeLabel() {
        double[][] vector = new double[10][1];
        vector[this.label][0] = 1.0;
        return new Matrix(vector);
    }

    public Matrix getData() {
        return data;
    }

    public Matrix getLabelVector() {
        return labelVector;
    }

    public int getLabel() {
        return label;
    }

    public void printData(){
        double[][] array = data.getArray();

        System.out.println("Label: " + label);

        for(int i=0; i < array.length; i++){
            for(int j=0; j < array[0].length; j++){
                System.out.print(" .^*$@#".charAt((int) Math.round((Math.round(array[i][j])/256.0)*6)) + " ");
            }
            System.out.println();
        }
    }
}
