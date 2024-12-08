package project.libraryassistant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class StaticBookData {

    public static ObservableList<Book> getBooks() {
        return FXCollections.observableArrayList(
                new Book("OL123456", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 5,
                        "https://ia802309.us.archive.org/view_archive.php?archive=/20/items/l_covers_0008/l_covers_0008_23.zip&file=0008231991-L.jpg"),
                new Book("OL654321", "To Kill a Mockingbird", "Harper Lee", "Fiction", 3,
                        "https://ia802309.us.archive.org/view_archive.php?archive=/20/items/l_covers_0008/l_covers_0008_22.zip&file=0008225226-L.jpg"),
                new Book("OL987654", "1984", "George Orwell", "Dystopian", 7,
                        "https://ia600100.us.archive.org/view_archive.php?archive=/5/items/l_covers_0012/l_covers_0012_72.zip&file=0012725444-L.jpg")
                // Add more books as needed
        );
    }
}
