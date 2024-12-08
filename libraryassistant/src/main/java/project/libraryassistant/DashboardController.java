package project.libraryassistant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.animation.Transition.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DashboardController {
    /// top
    @FXML
    private Button close;

    @FXML
    private Label current_form_label;

    @FXML
    private Button minimize;

    @FXML
    private Button bars_btn;

    @FXML
    private Button chevron_btn;


    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void sliderArrow() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(.5));
        slide.setNode(left_form);
        slide.setToX(-189);
        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(center_form);
        slide2.setToX(-189 + 130);

        TranslateTransition slide3 = new TranslateTransition();
        slide3.setDuration(Duration.seconds(.5));
        slide3.setNode(left_form_half);
        slide3.setToX(0);

        slide.setOnFinished((ActionEvent event) -> {
            bars_btn.setVisible(true);
            chevron_btn.setVisible(false);
        });

        slide3.play();
        slide2.play();
        slide.play();
    }

    @FXML
    void sliderBars() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(.5));
        slide.setNode(left_form);
        slide.setToX(0);

        TranslateTransition slide2 = new TranslateTransition();
        slide2.setDuration(Duration.seconds(.5));
        slide2.setNode(center_form);
        slide2.setToX(0);

        TranslateTransition slide3 = new TranslateTransition();
        slide3.setDuration(Duration.seconds(.5));
        slide3.setNode(left_form_half);
        slide3.setToX(-101);

        slide.setOnFinished((ActionEvent event) -> {
            bars_btn.setVisible(false);
            chevron_btn.setVisible(true);

        });

        slide3.play();
        slide2.play();
        slide.play();

    }

    /// left
    private double x = 0;
    private double y = 0;
    public void logout_main(ActionEvent event, Button logout_btn) {
        try {
            if (event.getSource() == logout_btn) {
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {
                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
                logout_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * left form
     */
    @FXML
    private AnchorPane left_form;

    @FXML
    private Circle circle_image;

    @FXML
    private Label user_name;

    @FXML
    private Button home_page;

    @FXML
    private Button manage_book;

    @FXML
    private Button manage_student;

    @FXML
    private Button issue_book;

    @FXML
    private Button return_book;

    @FXML
    private Button view_issued_book;

    @FXML
    private Button logout_btn;

    @FXML
    void logout(ActionEvent event) {
        logout_main(event, logout_btn);
    }

    /**
     * left form half.
     */
    @FXML AnchorPane left_form_half;

    @FXML
    private Circle circle_hl;

    @FXML
    private Button home_page_hl;

    @FXML
    private Button issue_book_hl;

    @FXML
    private Button manage_book_hl;

    @FXML
    private Button manage_student_hl;

    @FXML
    private Button return_book_hl;

    @FXML
    private Button view_issued_book_hl;

    @FXML
    private Button logout_btn_hl;

    @FXML
    public void logout_hl(ActionEvent event) {
        logout_main(event, logout_btn_hl);
    }

    @FXML
    public void navButtonDesign(ActionEvent event) {
        // Xóa tất cả class liên quan đến trạng thái active khỏi tất cả các nút
        home_page.getStyleClass().removeAll("bt_active");
        manage_book.getStyleClass().removeAll("bt_active");
        manage_student.getStyleClass().removeAll("bt_active");
        issue_book.getStyleClass().removeAll("bt_active");
        return_book.getStyleClass().removeAll("bt_active");
        view_issued_book.getStyleClass().removeAll("bt_active");

        home_page_hl.getStyleClass().removeAll("bt_half_left_active");
        manage_book_hl.getStyleClass().removeAll("bt_half_left_active");
        manage_student_hl.getStyleClass().removeAll("bt_half_left_active");
        issue_book_hl.getStyleClass().removeAll("bt_half_left_active");
        return_book_hl.getStyleClass().removeAll("bt_half_left_active");
        view_issued_book_hl.getStyleClass().removeAll("bt_half_left_active");

        // Gán class "bt_active" cho nút được nhấn
        if (event.getSource() == home_page) {
            home_page_form.setVisible(true);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);


            home_page.getStyleClass().add("bt_active");
            home_page_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Home Page");
        } else if (event.getSource() == manage_book) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(true);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);

            manage_book.getStyleClass().add("bt_active");
            manage_book_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Manage Book");
        } else if (event.getSource() == manage_student) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(true);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);

            manage_student.getStyleClass().add("bt_active");
            manage_student_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Manage Student");
        } else if (event.getSource() == issue_book) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(true);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);

            issue_book.getStyleClass().add("bt_active");
            issue_book_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Issue Book");
        } else if (event.getSource() == return_book) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(true);
            view_return_book_form.setVisible(false);

            return_book.getStyleClass().add("bt_active");
            return_book_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Return Book");
        } else if (event.getSource() == view_issued_book) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(true);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);

            view_issued_book.getStyleClass().add("bt_active");
            view_issued_book_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("View Issued Book");
        }
    }

    @FXML
    public void sideNavButtonDesign(ActionEvent event) {
        // Xóa tất cả class liên quan đến trạng thái active khỏi tất cả các nút
        home_page.getStyleClass().removeAll("bt_active");
        manage_book.getStyleClass().removeAll("bt_active");
        manage_student.getStyleClass().removeAll("bt_active");
        issue_book.getStyleClass().removeAll("bt_active");
        return_book.getStyleClass().removeAll("bt_active");
        view_issued_book.getStyleClass().removeAll("bt_active");

        home_page_hl.getStyleClass().removeAll("bt_half_left_active");
        manage_book_hl.getStyleClass().removeAll("bt_half_left_active");
        manage_student_hl.getStyleClass().removeAll("bt_half_left_active");
        issue_book_hl.getStyleClass().removeAll("bt_half_left_active");
        return_book_hl.getStyleClass().removeAll("bt_half_left_active");
        view_issued_book_hl.getStyleClass().removeAll("bt_half_left_active");


        // Gán class "bt_active" cho nút được nhấn
        if (event.getSource() == home_page_hl) {
            home_page_form.setVisible(true);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);


            home_page.getStyleClass().add("bt_active");
            home_page_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Home Page");
        } else if (event.getSource() == manage_book_hl) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(true);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);

            manage_book.getStyleClass().add("bt_active");
            manage_book_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Manage Book");
        } else if (event.getSource() == manage_student_hl) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(true);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);

            manage_student.getStyleClass().add("bt_active");
            manage_student_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Manage Student");
        } else if (event.getSource() == issue_book_hl) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(true);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);

            issue_book.getStyleClass().add("bt_active");
            issue_book_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Issue Book");
        } else if (event.getSource() == return_book_hl) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(false);
            return_book_form.setVisible(true);
            view_return_book_form.setVisible(false);

            return_book.getStyleClass().add("bt_active");
            return_book_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("Return Book");
        } else if (event.getSource() == view_issued_book_hl) {
            home_page_form.setVisible(false);
            manage_book_form.setVisible(false);
            add_book_form.setVisible(false);
            manage_student_form.setVisible(false);
            issue_book_form.setVisible(false);
            view_issued_book_form.setVisible(true);
            return_book_form.setVisible(false);
            view_return_book_form.setVisible(false);

            view_issued_book.getStyleClass().add("bt_active");
            view_issued_book_hl.getStyleClass().add("bt_half_left_active");

            current_form_label.setText("View Issued Book");
        }
    }

    /// Center
    @FXML
    private AnchorPane center_form;

    /**
     * Home page.
     */

    @FXML
    private AnchorPane home_page_form;

    @FXML
    private ImageView borrowedBook1_image;

    @FXML
    private ImageView borrowedBook2_image;

    @FXML
    private ImageView borrowedBook3_image;

    @FXML
    private Label no_book;

    @FXML
    private Label no_member;

    @FXML
    private ImageView availableBook1_image;

    @FXML
    private ImageView availableBook2_image;

    @FXML
    private ImageView availableBook3_image;
    /**
     * manage book.
     */
    @FXML
    private AnchorPane manage_book_form;

    @FXML
    private ImageView bookCoverImageView_manage;

    @FXML
    private TextField id_book_manage;

    @FXML
    private TextField title_book_manage;

    @FXML
    private TextField author_book_manage;

    @FXML
    private TextField genre_book_manage;

    @FXML
    private TextField quantity_book_manage;

    @FXML
    private TableView<Book> book_table_manage;

    @FXML
    private Button update_book_btn_manage;

    @FXML
    private Button delete_book_btn_manage;

    @FXML
    private TableColumn<Book, String> col_book_author_manage;

    @FXML
    private TableColumn<Book, String> col_book_genre_manage;

    @FXML
    private TableColumn<Book, String> col_book_id_manage;

    @FXML
    private TableColumn<Book, Integer> col_book_quantity_manage;

    @FXML
    private TableColumn<Book, String> col_book_title_manage;

    @FXML
    private Button add_book_btn_manage;

    @FXML
    private Button back_btn_manage;

    /**
     * manage student.
     */

    @FXML
    private AnchorPane manage_student_form;

    @FXML
    private TextField search_student;

    @FXML Button search_student_btn;

    @FXML
    private TableColumn<Student, String> col_student_faculty;

    @FXML
    private TableColumn<Student, String> col_student_id;

    @FXML
    private TableColumn<Student, String> col_student_university;

    @FXML
    private TableColumn<Student, String> col_student_name;

    @FXML
    private ComboBox<String> university_student;

    @FXML
    private TextField id_student;

    @FXML
    private TextField name_student;

    @FXML
    private TextField faculty_student;

    @FXML
    private Button add_student_btn;

    @FXML
    private Button delete_student_btn;


    /**
     * add_book.
     */

    @FXML
    private AnchorPane add_book_form;

    @FXML
    private TableView<Book> book_table;

    @FXML
    private TableColumn<Book, String> col_book_author;

    @FXML
    private TableColumn<Book, String> col_book_genre;

    @FXML
    private TableColumn<Book, String> col_book_id;

    @FXML
    private TableColumn<Book, Integer> col_book_quantity;

    @FXML
    private TableColumn<Book, String> col_book_title;

    @FXML
    private Button delete_book_btn;

    @FXML
    private TextField genre_book;

    @FXML
    private TextField id_book;

    @FXML
    private TextField search_book;

    @FXML
    private TextField author_book;

    @FXML
    private TextField quantity_book;

    @FXML
    private ImageView image_book;


    @FXML
    private Button add_book_btn;


    @FXML
    private TextField title_book;

    /**
     * issue book.
     */
    @FXML
    private AnchorPane issue_book_form;

    /**
     * view issue book form.
     */
    @FXML
    private AnchorPane view_issued_book_form;

    /**
     * return book.
     */
    @FXML
    private AnchorPane return_book_form;

    /**
     * view return book form.
     */

    @FXML
    private AnchorPane view_return_book_form;




    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    private ImageView bookCoverImageView;

    private String[] uni = {"UET", "ULIS", "UEB", "UEd", "VJU", "UL", "HUS", "UMP", "IS", "SIS"};
    @FXML
    public void initialize() {
        university_student.getItems().addAll(uni);

        home_page.getStyleClass().add("bt_active"); // Home Page mặc định là nút "active"
        manage_book.getStyleClass().add("bt");
        manage_student.getStyleClass().add("bt");
        issue_book.getStyleClass().add("bt");
        return_book.getStyleClass().add("bt");
        view_issued_book.getStyleClass().add("bt");

        home_page_hl.getStyleClass().add("bt_half_left_active");
        manage_book_hl.getStyleClass().add("bt_half_left");
        manage_student_hl.getStyleClass().add("bt_half_left");
        issue_book_hl.getStyleClass().add("bt_half_left");
        return_book_hl.getStyleClass().add("bt_half_left");
        view_issued_book_hl.getStyleClass().add("bt_half_left");


        // Hiển thị giao diện Home Page mặc định
        home_page_form.setVisible(true);
        manage_book_form.setVisible(false);
        add_book_form.setVisible(false);
        manage_student_form.setVisible(false);
        issue_book_form.setVisible(false);
        view_issued_book_form.setVisible(false);
        return_book_form.setVisible(false);
        view_issued_book_form.setVisible(false);

        // Cập nhật nhãn (label) hiển thị
        current_form_label.setText("Home Page");


        

        // Kết nối các cột trong bảng với thuộc tính của đối tượng Book
        col_book_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_book_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_book_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_book_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_book_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Đổ dữ liệu ban đầu vào TableView
        book_table.setItems(bookList);

        book_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                id_book.setText(newSelection.getId());
                title_book.setText(newSelection.getTitle());
                author_book.setText(newSelection.getAuthor());
                genre_book.setText(newSelection.getGenre());
                quantity_book.setText(String.valueOf(newSelection.getQuantity()));

                // Hiển thị ảnh bìa
                String coverImageUrl = newSelection.getCoverImageUrl();
                if (coverImageUrl != null && !coverImageUrl.isEmpty()) {
                    Image coverImage = new Image(coverImageUrl, true); // true để tải ảnh không đồng bộ
                    bookCoverImageView.setImage(coverImage);
                } else {
                    bookCoverImageView.setImage(null); // Không có ảnh bìa
                }
            }
        });

    }

    @FXML
    private void deleteBook() {
        // Xóa sách đang được chọn trong bảng
        Book selectedBook = book_table.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookList.remove(selectedBook);
            clearFields();
        }
    }

    private void clearFields() {
        id_book.clear();
        title_book.clear();
        author_book.clear();
        genre_book.clear();
        quantity_book.clear();
    }

    @FXML
    private void searchBook() {
        String keyword = search_book.getText().toLowerCase();

        if (keyword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a search keyword.");
            alert.show();
            return;
        }

        try {
            // Gọi Google Books API
            bookList.clear();
            String jsonResponse = fetchBooksFromGoogleAPI(keyword);

            // Phân tích dữ liệu JSON trả về
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode items = rootNode.path("items");

            if (items.isMissingNode() || items.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No books found for the keyword.");
                alert.show();
                return;
            }

            // Chọn quyển sách đầu tiên từ API và tự động điền thông tin vào các trường
            JsonNode firstBook = items.get(0).path("volumeInfo");

            title_book.setText(firstBook.path("title").asText());
            author_book.setText(firstBook.path("authors").toString());
            genre_book.setText(firstBook.path("categories").isMissingNode() ? "Unknown" : firstBook.path("categories").get(0).asText());

            quantity_book.setText("5"); // Mặc định số lượng là 5
            id_book.setText(firstBook.path("id").asText());

            // Hiển thị thông báo
            /*
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book details filled successfully.");
            alert.show();
            */

            for (JsonNode item : items) {
                JsonNode volumeInfo = item.path("volumeInfo");
                String id = item.path("id").asText();
                String title = volumeInfo.path("title").asText("Unknown Title");
                String author = volumeInfo.path("authors").isMissingNode() ? "Unknown Author"
                        : volumeInfo.path("authors").toString();
                String genre = volumeInfo.path("categories").isMissingNode() ? "Unknown Genre"
                        : volumeInfo.path("categories").get(0).asText();

                int quantity = 5; // Default value
                String coverImageUrl = volumeInfo.path("imageLinks").path("thumbnail").asText(""); // Lấy URL ảnh bìa

                bookList.add(new Book(id, title, author, genre, quantity, coverImageUrl));
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to fetch data from Google Books API.");
            alert.show();
        }
    }

    private String fetchBooksFromGoogleAPI(String keyword) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = "https://www.googleapis.com/books/v1/volumes?q=" + keyword;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new Exception("Unexpected code " + response);

            return response.body().string();
        }
    }
}
