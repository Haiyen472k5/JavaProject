package project.libraryassistant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenLibraryService {
    public ObservableList<manageBook> fetchBooksFromAPI() {
        ObservableList<manageBook> books = FXCollections.observableArrayList();

        try {
            String upiUrl = "https://openlibrary.org/search.json?q=fiction&limit=10";
            URL url = new URL(upiUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
