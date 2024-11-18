package utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

public class DataProvider {

    private static final String CSV_USERS_PATH_FILE = "PairwiseUsers.csv";

    public static Stream<Arguments> csvUsersData(){
        List<String[]> data = CSVUtils.loadCSVData(CSV_USERS_PATH_FILE);

        return data.stream()
                .filter(values -> values.length == 9)
                .map(values -> Arguments.of(values[0], values[1], values[2], values[3], values[4], values[5],
                        values[6], values[7], values[8]));
    }
}
