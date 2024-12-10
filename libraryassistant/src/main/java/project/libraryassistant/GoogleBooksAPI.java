package project.libraryassistant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class GoogleBooksAPI {
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "AIzaSyAE5rH654BuA54v4B12Qucy0PnSOW6O5lA"; // Replace with your API key

    public static String searchBooks(String query) throws Exception {
        String urlString = API_URL + query + "&key=" + API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();
        return content.toString();
    }

    public static void parseBooksJson(String jsonResponse) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonResponse);
        JsonNode items = rootNode.path("items");

        for (JsonNode item : items) {
            // Tạo ID ngẫu nhiên
            String id = generateRandomID(); // Sử dụng phương pháp generateRandomID()

            // Parse Title
            String title = item.path("volumeInfo").path("title").asText();

            // Parse Authors
            JsonNode authorsNode = item.path("volumeInfo").path("authors");
            String authors = authorsNode.isArray() ? authorsNode.toString() : "Unknown";

            // Parse Genre (Categories)
            JsonNode categoriesNode = item.path("volumeInfo").path("categories");
            String genre = categoriesNode.isArray() ? categoriesNode.get(0).asText() : "Unknown";

            // Parse Quantity (default to 1)
            int quantity = 5; // Không có từ API, cần thêm mặc định.

            // Output hoặc xử lý dữ liệu
            System.out.println("ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("Authors: " + authors);
            System.out.println("Genre: " + genre);
            System.out.println("Quantity: " + quantity);
        }
    }

    private static String generateRandomID() {
        return UUID.randomUUID().toString();
    }

}
