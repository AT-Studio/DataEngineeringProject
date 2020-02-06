import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for parsing and printing CSV files.
 */
public class CsvParser {

    /**
     * Parses a CSV file
     * @param path path to CSV file
     * @return List containing the lines of the parsed CSV file
     * @throws IOException
     * @throws CsvValidationException
     */
    public List<String[]> parse(String path) throws IOException, CsvValidationException {
        FileInputStream fileInStream = new FileInputStream(path);
        InputStreamReader inStreamReader = new InputStreamReader(fileInStream, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(inStreamReader);

        List<String[]> lines = new ArrayList<>();

        while (reader.peek() != null) {
            lines.add(reader.readNext());
        }

        reader.close();

        return lines;
    }

    /**
     * Prints a CSV file to the console
     * @param path path to CSV file
     * @throws IOException
     * @throws CsvValidationException
     */
    public void print(String path) throws IOException, CsvValidationException {
        List<String[]> lines = parse(path);

        for (String[] line : lines) {
            for (String word : line) {
                System.out.print(word + ", ");
            }
            System.out.println("\b\b\n---------------------");
        }
    }

}
