package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private final int img_size = 28;

    public List<LabeledImage> readCsv(String path){
        List<LabeledImage> images = new ArrayList<>();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(path))){
            String line;
            while((line = csvReader.readLine()) != null){
                String[] line_data = line.split(",");
                double[][] data = new double[img_size][img_size];
                int label = Integer.parseInt(line_data[0]);
                int i = 1;

                for(int row = 0; row < img_size; ++row){
                    for(int col = 0; col < img_size; ++col){
                        data[row][col] = (double) Integer.parseInt(line_data[i]);
                        ++i;
                    }
                }
                images.add(new LabeledImage(data, label));
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return images;
    }
}
