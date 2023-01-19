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

    public void prettyPrintData(){

        System.out.println("Label: " + label);

        for(int i =0; i < data.length; i++){
            for(int j =0; j < data[0].length; j++){

                // to be changed after updating ImageConverter to represent image as numbers from 0 to 256
                System.out.print(" .^*$@#".charAt((int) Math.round((Math.round(data[i][j]*-1)/16777216.0)*6)) + " ");
            }
            System.out.println();
        }
    }
}
