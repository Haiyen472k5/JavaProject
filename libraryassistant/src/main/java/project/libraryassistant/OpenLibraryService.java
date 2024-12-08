package project.libraryassistant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;

public class OpenLibraryService {
    public ObservableList<Book> fetchBooksFromAPI() {
        ObservableList<Book> books = FXCollections.observableArrayList();

        try {
            String upiUrl = "https://openlibrary.org/search.json?q=fiction&limit=10";
            URL url = new URL(upiUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
