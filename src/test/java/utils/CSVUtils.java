package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVUtils {
    public static List<String[]> loadCSVData(String resourcePath) {
        List<String[]> data = new ArrayList<>();
        try (InputStream inputStream = CSVUtils.class.getResourceAsStream("/" + resourcePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String[] trimmedValues = Arrays.copyOfRange(values, 1, values.length);

                for (int i = 0; i < trimmedValues.length; i++) {
                    if (trimmedValues[i].trim().isEmpty()) {
                        trimmedValues[i] = "";
                    }
                }
                data.add(trimmedValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
