package Data;

public class LabeledImage {
    private double[][] data;
    private int label;

    public LabeledImage(double[][] data, int label) {
        this.data = data;
        this.label = label;
    }

    public double[][] getData() {
        return data;
    }

    public int getLabel() {
        return label;
    }


    public void printData(){

        System.out.println("Label: " + label);

        for(int i =0; i < data.length; i++){
            for(int j =0; j < data[0].length; j++){
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}
