package project.libraryassistant;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
    private final SimpleStringProperty id;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty genre;
    private final SimpleIntegerProperty quantity;
    private final SimpleStringProperty coverImageUrl; // URL của ảnh bìa

    public Book(String id, String title, String author, String genre, int quantity, String coverImageUrl) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.genre = new SimpleStringProperty(genre);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.coverImageUrl = new SimpleStringProperty(coverImageUrl);
    }

    // Getters
    public String getCoverImageUrl() {
        return coverImageUrl.get();
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl.set(coverImageUrl);
    }

    //getter, setter

    public int getQuantity() {
        return quantity.get();
    }
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
    public String getId() {
        return id.get();
    }
    public void setId(String id) {
        this.id.set(id);
    }
    public String getTitle() {
        return title.get();
    }
    public void setTitle(String title) {
        this.title.set(title);
    }
    public String getAuthor() {
        return author.get();
    }
    public void setAuthor(String author) {
        this.author.set(author);
    }
    public String getGenre() {
        return genre.get();
    }
    public void setGenre(String genre) {
        this.genre.set(genre);
    }
}

