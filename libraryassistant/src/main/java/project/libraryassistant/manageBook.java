package project.libraryassistant;

import javafx.scene.image.Image;

public class manageBook {
    private final String id;
    private final String title;
    private final String author;
    private final String genre;
    private final int quantity;
    private final String coverImage;

    public manageBook(String id, String title, String author, String genre, int quantity, String coverUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.quantity = quantity;
        this.coverImage = coverUrl;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCoverImage() {
        return coverImage;
    }
}