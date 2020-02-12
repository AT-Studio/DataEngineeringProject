import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class Main {

    private static Connection conn;

    private static Statement stmt;

    public static void main(String[] args) {

        initializeDB();

        // Parse Json File
        try {
            JsonReader jsonReader = new JsonReader(new FileReader("./src/data/authors"));
            Gson gson = new Gson();
            AuthorParser[] authors = gson.fromJson(jsonReader, AuthorParser[].class);

            for (AuthorParser author : authors) {
                System.out.println(author.getName());
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO author(author_name, " +
                            "author_email, author_url) VALUES(?, ?, ?)");
                    preparedStatement.setString(1, author.getName());
                    preparedStatement.setString(2, author.getEmail());
                    preparedStatement.setString(3, author.getUrl());

                    preparedStatement.execute();
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Failed to insert author: " + e.getMessage());
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + e.getMessage());
        }

        // Parse CSV File
        CsvParser parser = new CsvParser();
        try {
            parser.print("./src/data/bookstore_report2.csv");
            List<String[]> lines = parser.parse("./src/data/bookstore_report2.csv");
            Iterator<String[]> iterator = lines.iterator();
            iterator.next();
            while (iterator.hasNext()) {
                String[] line = iterator.next();
                try {
                    PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO book(isbn, publisher_name, " +
                            "author_name, book_year, book_title, book_price) VALUES(?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, line[0]);
                    preparedStatement.setNull(2, Types.VARCHAR);
                    preparedStatement.setString(3, line[2]);
                    preparedStatement.setNull(4, Types.INTEGER);
                    preparedStatement.setString(5, line[1]);
                    preparedStatement.setNull(6, Types.NUMERIC);

                    preparedStatement.execute();
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Failed to insert book " + e.getMessage());
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + e.getMessage());
        } catch (CsvValidationException e) {
            System.out.println("Could not validate CSV: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    /**
     * Initializes the database
     */
    private static void initializeDB() {
        final String JDBC_DRIVER = "org.sqlite.Driver";
        final String DB_URL = "jdbc:sqlite:./src/data/BookStore.db";

        final String USER = "";
        final String PASS = "";

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
