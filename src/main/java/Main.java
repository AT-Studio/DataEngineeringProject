import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        // Parse CSV File
        CsvParser parser = new CsvParser();
        try {
            parser.print("./src/data/SEOExample.csv");
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + e.getMessage());
        } catch (CsvValidationException e) {
            System.out.println("Could not validate CSV: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }

        // Parse Json File
        try {
            JsonReader jsonReader = new JsonReader(new FileReader("./src/data/authors"));
            Gson gson = new Gson();
            AuthorParser[] authors = gson.fromJson(jsonReader, AuthorParser[].class);

            for (AuthorParser author : authors) {
                System.out.println(author.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + e.getMessage());
        }
    }

}
