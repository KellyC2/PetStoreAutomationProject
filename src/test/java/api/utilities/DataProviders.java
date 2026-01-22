package api.utilities;

import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataProviders{
    public static Stream<Arguments> getAllData() throws IOException {
        String path = System.getProperty("user.dir") + "/testData/Userdata.xlsx";
        XLUtility xlUtility = new XLUtility(path);
        int rownum = xlUtility.getRowCount("Sheet1");
        int colcount = xlUtility.getColumnCount("Sheet1", 1);
        return IntStream.rangeClosed(1, rownum).mapToObj((i) -> {
            try {
                Object[] data = new Object[colcount];

                for(int j = 0; j < colcount; ++j) {
                    data[j] = xlUtility.getCellData("Sheet1", i, j);
                }

                return Arguments.of(data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Stream<String> getUserNames() throws IOException {
        String path = System.getProperty("user.dir") + "/testData/Userdata.xlsx";
        XLUtility xlUtility = new XLUtility(path);
        int rownum = xlUtility.getRowCount("Sheet1");
        return IntStream.rangeClosed(1, rownum).mapToObj((i) -> {
            try {
                return xlUtility.getCellData("Sheet1", i, 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
