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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.animation.Transition.*;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

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

    private void calculateTotalBook() {
        // Lấy danh sách dữ liệu từ TableView
        ObservableList<Book> books = book_table_manage.getItems();

        // Tính tổng quantity
        int totalBook = books.size();

        // Hiển thị kết quả (nếu có label)
        no_book.setText(String.valueOf(totalBook));
    }

    private void calculateTotalStudent() {
        // Lấy danh sách dữ liệu từ TableView
        ObservableList<Student> students = student_table.getItems();

        // Tính tổng quantity
        int totalStudent = students.size();

        // Hiển thị kết quả (nếu có label)
        no_member.setText(String.valueOf(totalStudent));
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
    private FontAwesomeIconView edit_icon;


    private String imagePath;
    private Image image = null;

    public void insertImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file", "*png", "*jpg"));
        Stage stage = (Stage)left_form.getScene().getWindow();

        File file = open.showOpenDialog(stage);

        if (file != null) {
            imagePath = file.getAbsolutePath().replace("\\", "/");
            image = new Image(file.toURI().toString());
            circle_image.setFill(new ImagePattern(image));
            circle_hl.setFill(new ImagePattern(image));
        }
        changeProfile();
    }

    public void designInsertImage() {
        edit_icon.setVisible(false);
        circle_image.setOnMouseEntered((MouseEvent e) -> {
            edit_btn.setVisible(true);
        });

        circle_image.setOnMouseExited((MouseEvent e) -> {
            edit_btn.setVisible(false);
        });

        edit_btn.setOnMouseEntered((MouseEvent e) -> {
            edit_btn.setVisible(true);
            edit_icon.setVisible(true);
            edit_icon.setFill(Color.BLACK);
        });

        edit_btn.setOnMousePressed((MouseEvent e) -> {
            edit_btn.setVisible(true);
            edit_icon.setVisible(true);
            edit_icon.setFill(Color.RED);
        });

        edit_btn.setOnMouseExited((MouseEvent e) -> {
            edit_btn.setVisible(false);
        });

    }

    public void changeProfile() {
        String sql = "UPDATE admin SET image = ? where account = ?";
        try (Connection connect = DatabaseLogin.getConnection(); PreparedStatement pstmt = connect.prepareStatement(sql)) {
            pstmt.setString(1, imagePath);
            pstmt.setString(2, getData.account);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProfile() {
        if (getData.path != null && !getData.path.equals("")) {
            image = new Image(getData.path);
            circle_image.setFill(new ImagePattern(image));
            circle_hl.setFill(new ImagePattern(image));
        }
    }
    @FXML
    private Label user_name;

    public void setUserName(String userName) {
        user_name.setText(userName);
    }

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
    private Button edit_btn;

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);
            
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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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

            clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
            clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                    author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);

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
    private Label no_issued;

    @FXML
    private ImageView availableBook1_image;

    @FXML
    private ImageView availableBook2_image;

    @FXML
    private ImageView availableBook3_image;

    private void displayAvailableBookImages(ObservableList<Book> books, ImageView image1, ImageView image2, ImageView image3) {
        clearAllImageViews(image1, image2, image3);
        if (books.isEmpty()) {
            return;
        }

        Collections.shuffle(books);
        int bookToShow = Math.min(books.size(), 3);

        for (int i = 0; i < bookToShow; i++) {
            String coverImageUrl = books.get(i).getCoverImageUrl();
            switch(i) {
                case 0 -> loadImageToImageView(image1, coverImageUrl);
                case 1 -> loadImageToImageView(image2, coverImageUrl);
                case 2 -> loadImageToImageView(image3, coverImageUrl);
            }
        }
    }

    private void displayBorrowedBook(ObservableList<IssuedBook> books, ImageView image1, ImageView image2, ImageView image3) {
        clearAllImageViews(image1, image2, image3);
        if (books.isEmpty()) {
            return;
        }
        Collections.shuffle(books);
        int bookToShow = Math.min(books.size(), 3);
        for (int i = 0; i < bookToShow; i++) {
            String url = books.get(i).getImage();
            switch(i) {
                case 0 -> loadImageToImageView(image1, url);
                case 1 -> loadImageToImageView(image2, url);
                case 2 -> loadImageToImageView(image3, url);
            }
        }
    }


    private void clearAllImageViews(ImageView image1, ImageView image2, ImageView image3) {
        image1.setImage(null);
        image2.setImage(null);
        image3.setImage(null);
    }



    // Hàm tải ảnh vào ImageView
    private void loadImageToImageView(ImageView imageView, String imageUrl) {
        try {
            Image image = new Image(imageUrl, true);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
    private Button search_book_btn_manage;

    private ObservableList<Book> addedBookList = FXCollections.observableArrayList(); /// book_table_manage

    @FXML
    public void addBookForm(ActionEvent event) {
        if (event.getSource() == add_book_btn_manage) {
            manage_book_form.setVisible(false);
            add_book_form.setVisible(true);
        }
    }

    private final DatabaseBook db_book = new DatabaseBook();


    /**
     * manage student.
     */

    private final DatabaseStudent db_student = new DatabaseStudent();
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane manage_student_form;

    @FXML
    private TextField search_student;

    @FXML
    private Button search_student_btn;

    @FXML
    private TableView<Student> student_table;

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
    private ComboBox<String> university_faculty;

    @FXML
    private Button add_student_btn;

    @FXML
    private Button delete_student_btn;

    public void addStudent() {
        if (id_student.getText().isEmpty() || name_student.getText().isEmpty() || university_faculty.getValue() == null || university_student.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter in full.");
            alert.show();
            return;
        }
        String id = id_student.getText().trim();
        String name = name_student.getText().trim();
        String faculty = university_faculty.getValue().trim();
        String university = university_student.getValue().trim();

        for (Student student : studentList) {
            if (id.equals(student.getId())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "A student with this ID already exists in the student table.");
                alert.show();
                return;
            }
        }

        Student newStudent = new Student(id, name, university, faculty);
        studentList.add(newStudent);
        db_student.saveStudent(FXCollections.observableArrayList(newStudent));
        student_table.getSelectionModel().clearSelection();

        clearFieldsStudent(id_student, name_student, university_faculty, university_student);


        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Student added to the table successfully!");
        alert.show();

        student_table.setItems(FXCollections.observableArrayList(studentList));
        student_table.refresh();
        calculateTotalStudent();
    }

    @FXML
    private void deleteStudent() {
        // Lấy sách được chọn từ bảng
        Student selectedStudent = student_table.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert(Alert.AlertType.WARNING, "WARNING", "Please select a student to delete.");
            return;
        }
        for (IssuedBook issuedBooks : issuedBookList) {
            if (issuedBooks.getStudentID().equals(selectedStudent.getId())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You can't delete this student!");
                alert.show();
                return;
            }
        }
        if (selectedStudent != null) {
            // Xóa sách khỏi danh sách
            studentList.remove(selectedStudent);
            db_student.deleteStudent(selectedStudent.getId());

            // Làm mới lại bảng
            student_table.getSelectionModel().clearSelection();
            student_table.setItems(FXCollections.observableArrayList(studentList));
            student_table.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully!");
            alert.show();
            calculateTotalStudent();
            // Chọn dòng cuối cùng nếu danh sách không trống
            if (!studentList.isEmpty()) {
                student_table.getSelectionModel().selectLast();
            } else {
                clearFieldsStudent(id_student, name_student, university_faculty, university_student);
            }
        }
    }

    @FXML
    void searchStudent() {
        String student_id = search_student.getText();
        if (student_id == null || student_id.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter student id!");
            alert.show();
            return;
        }

        student_id = student_id.trim();

        ObservableList<Student> searchResults = FXCollections.observableArrayList();

        for (Student student : studentList) {
            if (student.getId().contains(student_id)) {
                searchResults.add(student);
            }
        }

        search_student_btn.getStyleClass().add("search_btn_active");
        if (searchResults.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Student not found!");
            alert.show();
            student_table.setItems(searchResults);
            student_table.refresh();
            return;
        }

        student_table.setItems(searchResults);
        student_table.refresh();
        clearFieldsStudent(id_student, name_student, university_faculty, university_student);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Search students successfully!");
        alert.show();
    }

    @FXML
    void return_student_table() {
        search_student_btn.getStyleClass().removeAll("search_btn_active");
        student_table.getSelectionModel().clearSelection();
        search_student.setText(null);
        student_table.setItems(studentList);
        student_table.refresh();

    }


    /**
     * add_book.
     */
    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane add_book_form;

    @FXML
    private Button back_btn_book;

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
    private ImageView bookCoverImageView;


    @FXML
    private Button add_book_btn;


    @FXML
    private TextField title_book;

    @FXML
    private TextField search_book_title;

    @FXML
    private Button search_book_btn;



    @FXML
    public void return_manage_book(ActionEvent event) {
        if (event.getSource() == back_btn_book) {
            add_book_form.setVisible(false);
            manage_book_form.setVisible(true);
            book_table_manage.setItems(addedBookList);
        }
    }



    @FXML
    private void addBook() {
        // Lấy thông tin từ các trường nhập liệu
        Book selected = book_table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a book from the source table.");
            alert.show();
            return;
        }
        String id = id_book.getText().trim();
        String title = title_book.getText().trim();
        String author = author_book.getText().trim();
        String genre = genre_book.getText().trim();
        String quantityText = quantity_book.getText().trim();
        String coverImageUrl = selected.getCoverImageUrl(); // Nếu cần thêm URL ảnh


        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity must be a positive integer.");
            alert.show();
            return;
        }

        // Kiểm tra trùng ID trong bảng thứ hai (tuỳ chọn)
        for (Book book : addedBookList) {
            if (book.getId().equals(id)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "A book with this ID already exists in the added books table.");
                alert.show();
                return;
            }
        }

        // Thêm sách vào danh sách của bảng thứ hai
        Book newBook = new Book(id, title, author, genre, quantity, coverImageUrl);
        addedBookList.add(newBook);
        db_book.saveBooks(FXCollections.observableArrayList(newBook));

        // Làm sạch các trường nhập liệu
        clearFields(id_book, title_book, author_book, genre_book, quantity_book, bookCoverImageView);

        // Hiển thị thông báo thành công
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book added to the table successfully!");
        alert.show();

        book_table_manage.setItems(addedBookList);
        book_table_manage.refresh();
        calculateTotalBook();
    }

    @FXML
    private void updateBook() {
        Book selected = book_table_manage.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a book from the source table.");
            alert.show();
            return;
        }
        String id = id_book_manage.getText().trim();

        // Ngăn thay đổi ID
        if (!id.equals(selected.getId())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You cannot change the Book ID!");
            alert.show();
            id_book_manage.setText(selected.getId()); // Khôi phục ID cũ
            return;
        }

        String title = title_book_manage.getText().trim();
        String author = author_book_manage.getText().trim();
        String genre = genre_book_manage.getText().trim();
        String quantityText = quantity_book_manage.getText().trim();

        if(title == null || author == null || genre == null || quantityText == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill all fields.");
            alert.show();
            return;
        }


        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity must be a positive integer.");
            alert.show();
            return;
        }

        selected.setTitle(title);
        selected.setAuthor(author);
        selected.setGenre(genre);
        selected.setQuantity(quantity);

        boolean updateSuccess = db_book.updateBookInDatabase(selected);

        if (updateSuccess) {
            for (int i = 0; i < addedBookList.size(); i++) {
                if (addedBookList.get(i).getId().equals(id)) {
                    addedBookList.set(i, selected);
                    break;
                }
            }

            book_table_manage.refresh();


            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book updated successfully!");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update book!");
            alert.show();
        }
    }


    @FXML
    void searchBooks() {
        // Lấy văn bản từ TextField tìm kiếm (giả sử bạn có một TextField tên là `searchField`)
        String searchText = search_book_title.getText();

        // Kiểm tra xem ô tìm kiếm có trống không
        if (searchText == null || searchText.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Enter text to search!");
            alert.show();
            return;
        }
        searchText = searchText.trim().toLowerCase();

        // Danh sách kết quả tìm kiếm
        ObservableList<Book> searchResults = FXCollections.observableArrayList();

        // Duyệt qua danh sách sách hiện tại để kiểm tra
        for (Book book : addedBookList) {
            if (book.getTitle().toLowerCase().contains(searchText)) {
                searchResults.add(book);
            }
        }

        search_book_btn_manage.getStyleClass().add("search_btn_active");
        // Nếu không tìm thấy kết quả
        if (searchResults.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No books found matching the search text.");
            alert.show();
            book_table_manage.setItems(searchResults);
            book_table_manage.refresh();
            return;
        }

        // Hiển thị kết quả tìm kiếm trên bảng
        book_table_manage.getSelectionModel().clearSelection();
        book_table_manage.setItems(searchResults);
        book_table_manage.refresh();
        clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Search books successfully!");
        alert.show();
    }

    @FXML
    void resetBookTable() {
        search_book_btn_manage.getStyleClass().removeAll("search_btn_active");
        book_table_manage.getSelectionModel().clearSelection();
        search_book_title.setText(null);
        book_table_manage.setItems(addedBookList);
        book_table_manage.refresh();

    }
    /**
     * issue book.
     */
    @FXML
    private AnchorPane issue_book_form;

    @FXML
    private TextField id_issue;

    @FXML
    private TextField id_student_issue;

    @FXML
    private TextField id_book_issue;

    @FXML
    private DatePicker issue_date;

    @FXML
    private DatePicker due_date;

    @FXML
    private ImageView image_book_detail;

    @FXML
    private TextField id_book_detail;

    @FXML
    private TextField name_book_detail;

    @FXML
    private TextField author_book_detail;

    @FXML
    private TextField genre_book_detail;

    @FXML
    private TextField quantity_book_detail;

    @FXML
    private Button delete_issue;

    @FXML
    private Button check_available_issue;

    @FXML
    private Button record_issue;

    @FXML
    void delete_issue() {
        clearFieldsIssueBook(id_issue, id_student_issue, id_book_issue, issue_date, due_date, id_book_detail, name_book_detail,
                author_book_detail, genre_book_detail, quantity_book_detail, image_book_detail);
    }
    private boolean checkSuccess = false;
    @FXML
    void check_available_issue() {
        String id_student = id_student_issue.getText().trim();
        String id_book = id_book_issue.getText().trim();

        if (id_student == null || id_book == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter both Book ID and Student ID.");
            alert.show();
            return;
        }

        boolean check_student = false;
        boolean check_book = false;
        Book detail_book = null;
        for (Student student : studentList) {
            if (student.getId().equals(id_student)) {
                check_student = true;
                break;
            }
        }
        if (check_student == false) {
            showAlert(Alert.AlertType.WARNING, "WARNING", "Student does not exist!");
            return;
        }

        for (Book book : addedBookList) {
            if (book.getId().equals(id_book)) {
                check_book = true;
                detail_book = book;
                break;
            }
        }
        if (check_book == false) {
            showAlert(Alert.AlertType.WARNING, "WARNING", "Book does not exist!");
            return;
        }
        id_book_detail.setText(detail_book.getId());
        name_book_detail.setText(detail_book.getTitle());
        author_book_detail.setText(detail_book.getAuthor());
        genre_book_detail.setText(detail_book.getGenre());
        quantity_book_detail.setText(String.valueOf(detail_book.getQuantity()));
        Image coverImage = new Image(detail_book.getCoverImageUrl(), true);
        image_book_detail.setImage(coverImage);
        checkSuccess = true;
    }

    ///  Tính tổng số sách đang mượn
    private void calculateTotalIssuedBook() {
        ObservableList<IssuedBook> issuedBooks = issued_book_table.getItems();
        // Tính tổng quantity
        int totalStudent = issuedBooks.size();

        // Hiển thị kết quả (nếu có label)
        no_issued.setText(String.valueOf(totalStudent));
    }
    /// mượn sách
    @FXML
    void record() {
        String student_id_record = id_student_issue.getText().trim();
        String book_id_record = id_book_issue.getText().trim();
        LocalDate issueDate = issue_date.getValue();
        LocalDate dueDate = due_date.getValue();
        String status = "Pending";

        if (checkSuccess == false || issueDate == null || dueDate == null) {
            showAlert(Alert.AlertType.WARNING, "WARNING", "Cannot record book.");
            return;
        }
        if (issueDate.compareTo(dueDate) >= 0) {
            showAlert(Alert.AlertType.WARNING, "WARNING", "Invalid date.");
            return;
        }
        id_issue.setText(generateIssuedID());
        String issue_id_record = id_issue.getText().trim();
        for (IssuedBook issuedBook : issuedBookList) {
            if (issuedBook.getIssuedID().equals(issue_id_record)) {
                showAlert(Alert.AlertType.WARNING, "WARNING", "Book already exists.");
                return;
            }
        }
        String bookName = null;
        String urlImage = null;
        Book issued_book = null;
        for (int i = 0; i < addedBookList.size(); i++) {
            if (addedBookList.get(i).getId().equals(book_id_record)) {
                int quantity = addedBookList.get(i).getQuantity();
                bookName = addedBookList.get(i).getTitle();
                urlImage = addedBookList.get(i).getCoverImageUrl();
                if (quantity == 0) {
                    showAlert(Alert.AlertType.WARNING, "WARNING", "This book is out of stock.");
                    return;
                }
                issued_book = addedBookList.get(i);
                issued_book.setQuantity(quantity-1);
                addedBookList.set(i, issued_book);
                db_book.updateBookInDatabase(issued_book);
                book_table_manage.refresh();
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String issue_date = issueDate.format(formatter);
        String due_date = dueDate.format(formatter);


        IssuedBook newBook = new IssuedBook(issue_id_record, student_id_record, bookName , issue_date, due_date, status, urlImage, book_id_record);
        issuedBookList.add(newBook);
        db_issuedBook.saveIssuedBooks(FXCollections.observableArrayList(newBook));
        showAlert(Alert.AlertType.INFORMATION, "SUCCESS", "Book added to the table successfully!");

        issued_book_table.setItems(issuedBookList);
        issued_book_table.refresh();
        calculateTotalIssuedBook();

        if (borrowedBook1_image.getImage() == null) {
            Image newImage = new Image(urlImage, true);
            borrowedBook1_image.setImage(newImage);
            return;
        }
        if (borrowedBook2_image.getImage() == null) {
            Image newImage = new Image(urlImage, true);
            borrowedBook2_image.setImage(newImage);
            return;
        }
        if (borrowedBook3_image.getImage() == null) {
            Image newImage = new Image(urlImage, true);
            borrowedBook3_image.setImage(newImage);
        }
    }

    /**
     * view issued book form.
     */

    private ObservableList<IssuedBook> issuedBookList = FXCollections.observableArrayList();
    private final DatabaseIssuedBook db_issuedBook = new DatabaseIssuedBook();
    @FXML
    private AnchorPane view_issued_book_form;

    @FXML
    private TableView<IssuedBook> issued_book_table;

    @FXML
    private TableColumn<IssuedBook, String> col_issuedID;

    @FXML
    private TableColumn<IssuedBook, String> col_studentID;

    @FXML
    private TableColumn<IssuedBook, String> col_bookName;

    @FXML
    private TableColumn<IssuedBook, String>  col_issuedDate;

    @FXML
    private TableColumn<IssuedBook, String>  col_dueDate;

    @FXML
    private TableColumn<IssuedBook, String>  col_status;

    @FXML
    private Button search_issued_btn;

    @FXML
    private TextField search_issued_text;

    @FXML
    void search_issued() {
        String searchText = search_issued_text.getText();

        // Kiểm tra xem ô tìm kiếm có trống không
        if (searchText == null || searchText.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "WARNING", "Please enter search text.");
            return;
        }
        searchText = searchText.trim().toLowerCase();

        // Danh sách kết quả tìm kiếm
        ObservableList<IssuedBook> searchResults = FXCollections.observableArrayList();

        // Duyệt qua danh sách sách hiện tại để kiểm tra
        for (IssuedBook book : issuedBookList) {
            if (book.getIssuedID().toLowerCase().contains(searchText) || book.getStudentID().toLowerCase().contains(searchText) ||
                book.getBookName().toLowerCase().contains(searchText) || book.getBookID().toLowerCase().contains(searchText) ||
                book.getIssueDate().toLowerCase().contains(searchText) || book.getDueDate().toLowerCase().contains(searchText)) {
                searchResults.add(book);
            }
        }

        search_issued_btn.getStyleClass().add("search_btn_active");
        // Nếu không tìm thấy kết quả
        if (searchResults.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Error", "Invalid.");
            return;
        }

        // Hiển thị kết quả tìm kiếm trên bảng
        issued_book_table.getSelectionModel().clearSelection();
        issued_book_table.setItems(searchResults);
        issued_book_table.refresh();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Found!");
    }

    @FXML
    void return_issued_table() {
        search_issued_btn.getStyleClass().removeAll("search_btn_active");
        issued_book_table.getSelectionModel().clearSelection();
        search_issued_text.setText(null);
        issued_book_table.setItems(issuedBookList);
        issued_book_table.refresh();

    }

    /**
     * return book.
     */

    @FXML
    private AnchorPane return_book_form;

    @FXML
    private TextField bookCondition_return;

    @FXML
    private TextField bookId_return;

    @FXML
    private ImageView bookImage_return;

    @FXML
    private Label bookNameField;

    @FXML
    private TextField issueDateField_return;

    @FXML
    private TextField dueDateField_return;

    @FXML
    private TextField issueId_return;

    @FXML
    private TextField penaltyAmt1_return;

    @FXML
    private TextField penaltyAmt2_return;

    @FXML
    private TextField penaltyNote1_return;

    @FXML
    private TextField penaltyNote2_return;

    @FXML
    private TextField studentId_return;

    @FXML
    private Button record_return_book_btn;

    @FXML
    private Button check_available_btn;

    @FXML
    private DatePicker return_date;

    private Connection connection = db_book.getConnection();

    // Xử lý sự kiện khi nhấn nút 'Check Available'
    @FXML
    public void handleCheckAvailable(ActionEvent event) {
        String issueID = issueId_return.getText().trim();

        if (issueID.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "WARNING", "Please fill all fields!");
            return;
        }

        try {
            // Kiểm tra nếu sách đã được mượn
            PreparedStatement ps = connection.prepareStatement("SELECT issueID, issueDate, dueDate, bookID, studentID, bookName, image FROM issuedbook WHERE issueID = ?");
            ps.setString(1, issueID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Nếu có bản ghi, hiển thị thông tin sách
                String bookID = rs.getString("bookID");
                bookId_return.setText(rs.getString("bookID"));
                issueDateField_return.setText(rs.getString("issueDate"));
                dueDateField_return.setText(rs.getString("dueDate"));
                studentId_return.setText(rs.getString("studentID"));
                bookNameField.setText(rs.getString("bookName"));
                bookImage_return.setImage(new Image(rs.getString("image")));

            } else {
                clearIssueAvailPane();
                showAlert(Alert.AlertType.WARNING, "WARNING", "No issue available.");
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong!");
            alert.show();
            throw new RuntimeException(e);
        }
    }

    private void clearIssueAvailPane() {
        issueId_return.setText("");
        issueDateField_return.setText("");
        dueDateField_return.setText("");
        bookId_return.clear();
        studentId_return.clear();
        bookImage_return.setImage(null); // Không hiển thị ảnh
    }

    //return book
    @FXML
    private void recordReturnBook(ActionEvent event) {
        String issueID = issueId_return.getText();
        int bookCondition = Integer.parseInt(bookCondition_return.getText());
        java.sql.Date returnDate = java.sql.Date.valueOf(return_date.getValue());

        try {
            // Lấy thông tin từ bảng issuedbook
            String issuedQuery = "SELECT bookID, dueDate FROM issuedbook WHERE issueID = ?";
            PreparedStatement issuedStmt = connection.prepareStatement(issuedQuery);
            issuedStmt.setString(1, issueID);
            ResultSet issuedRs = issuedStmt.executeQuery();

            if (issuedRs.next()) {
                String bookID = issuedRs.getString("bookID");
                Date dueDate = issuedRs.getDate("dueDate");

                // Kiểm tra phạt trễ hạn
                int lateDays = (int) ((returnDate.getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24));
                System.out.println("Days late: " + lateDays);
                int latePenalty = 0;
                if (lateDays <= 0)  lateDays = 0;
                if (lateDays > 0) {
                    String lateQuery = "SELECT fine FROM returnbooklaterule WHERE ? BETWEEN min_days AND max_days";;
                    PreparedStatement lateStmt = connection.prepareStatement(lateQuery);
                    lateStmt.setInt(1, lateDays);
                    ResultSet lateRs = lateStmt.executeQuery();
                    if (lateRs.next()) {
                        latePenalty = lateRs.getInt("fine");
                        penaltyAmt1_return.setText(latePenalty + "");
                        penaltyNote1_return.setText("Penalty for late return: " + lateDays);
                    }
                }

                // Kiểm tra tổn hại sách
                int damagePenalty = 0;
                String action = "";
                String damageQuery = "SELECT fine, action FROM returnbookdamagerule WHERE ? BETWEEN bookdamagemin AND bookdamagemax";
                PreparedStatement damageStmt = connection.prepareStatement(damageQuery);
                damageStmt.setInt(1, bookCondition);
                ResultSet damageRs = damageStmt.executeQuery();
                if (damageRs.next()) {
                    damagePenalty = damageRs.getInt("fine");
                    action = damageRs.getString("action");
                    penaltyAmt2_return.setText(damagePenalty + "");
                    penaltyNote2_return.setText("Penalty for book damage");
                }

                // Cập nhật số lượng sách trong bảng book
                if ("Giữ sách".equals(action)) {
                    String updateBookQuery = "UPDATE book SET quantity = quantity + 1 WHERE id = ?";
                    PreparedStatement updateBookStmt = connection.prepareStatement(updateBookQuery);
                    updateBookStmt.setString(1, bookID);
                    updateBookStmt.executeUpdate();
                    Book issued_book = null;
                    for (int i = 0; i < addedBookList.size(); i++) {
                        if (addedBookList.get(i).getId().equals(bookID)) {
                            int quantity = addedBookList.get(i).getQuantity();
                            issued_book = addedBookList.get(i);
                            issued_book.setQuantity(quantity + 1);
                            addedBookList.set(i, issued_book);
                            book_table_manage.refresh();
                            break;
                        }
                    }


                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book returned successfully. The book is in acceptable condition.");
                } else if ("Bỏ sách".equals(action)) {
                    showAlert(Alert.AlertType.WARNING, "Success", "Book returned successfully. The book is not in acceptable condition.");
                }

                // Cập nhật issuedbook
                issuedBookList.removeIf(book -> book.getIssuedID().equals(issueID));
                issued_book_table.setItems(FXCollections.observableArrayList(issuedBookList));
                issued_book_table.refresh();
                calculateTotalIssuedBook();


                String updateIssuedQuery = "DELETE FROM issuedbook WHERE issueID = ?";
                PreparedStatement updateIssuedBook = connection.prepareStatement(updateIssuedQuery);
                updateIssuedBook.setString(1, issueID);
                updateIssuedBook.executeUpdate();

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid Issue ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while processing the return.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void connectBook(TableColumn<Book, String> id_col, TableColumn<Book, String> title_col, TableColumn<Book, String> author_col,
                            TableColumn<Book, String> genre_col, TableColumn<Book, Integer> quantity_col, TableView<Book> bookTable,
                            TextField id, TextField title, TextField author, TextField genre, TextField quantity, ImageView bookCover, ObservableList<Book> book_list) {
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        author_col.setCellValueFactory(new PropertyValueFactory<>("author"));
        genre_col.setCellValueFactory(new PropertyValueFactory<>("genre"));
        quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Đổ dữ liệu ban đầu vào TableView
        bookTable.setItems(book_list);

        bookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                id.setText(newSelection.getId());
                title.setText(newSelection.getTitle());
                author.setText(newSelection.getAuthor());
                genre.setText(newSelection.getGenre());
                quantity.setText(String.valueOf(newSelection.getQuantity()));

                // Hiển thị ảnh bìa
                String coverImageUrl = newSelection.getCoverImageUrl();
                if (coverImageUrl != null && !coverImageUrl.isEmpty()) {
                    Image coverImage = new Image(coverImageUrl, true); // true để tải ảnh không đồng bộ
                   bookCover.setImage(coverImage);
                } else {
                    bookCover.setImage(null); // Không có ảnh bìa
                }
            }
        });
    }

    public void connectStudent(TableColumn<Student, String> id, TableColumn<Student, String> name, TableColumn<Student, String> university,
                                TableColumn<Student, String> faculty, ObservableList<Student> studentList,
                               TextField idField, TextField nameField, ComboBox<String> faculty_field, ComboBox<String> university_field) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        university.setCellValueFactory(new PropertyValueFactory<>("university"));
        faculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        student_table.setItems(studentList);

        student_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(newSelection.getId());
                nameField.setText(newSelection.getName());
                faculty_field.setEditable(true);
                faculty_field.getEditor().setText(newSelection.getFaculty());
                university_field.setEditable(true);
                university_field.getEditor().setText(newSelection.getUniversity());
            }
        });
    }

    public void connectIssuedBook(TableColumn<IssuedBook, String> issuedID, TableColumn<IssuedBook, String> studentID,
                                  TableColumn<IssuedBook, String> bookName, TableColumn<IssuedBook, String> issueDate,
                                  TableColumn<IssuedBook, String> dueDate, TableColumn<IssuedBook, String> status,
                                  ObservableList<IssuedBook> issuedBookList, TableView<IssuedBook> issued_book_table) {
        issuedID.setCellValueFactory(new PropertyValueFactory<>("issuedID"));
        studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        issued_book_table.setItems(issuedBookList);
    }
    List<University> universities = University.getUniversities();
    HelloController helloController = new HelloController();
    @FXML
    public void initialize() {
        search_student_btn.getStyleClass().removeAll("search_btn_active");
        search_book_btn_manage.getStyleClass().removeAll("search_book_btn_active");
        search_book_btn.getStyleClass().removeAll("search_book_btn_active");

        designInsertImage();
        showProfile();

        universities.forEach(u -> university_student.getItems().add(u.getName()));
        university_student.setOnAction(event -> {
            String selectedUniversity = university_student.getValue();
            if (selectedUniversity != null) {
                // Tìm trường đại học tương ứng từ danh sách
                University university = universities.stream()
                        .filter(u -> u.getName().equals(selectedUniversity))
                        .findFirst()
                        .orElse(null);

                // Cập nhật danh sách khoa cho trường đại học đã chọn
                if (university != null) {
                    university_faculty.getItems().clear();  // Xóa danh sách khoa cũ
                    university_faculty.getItems().addAll(university.getFaculties());  // Thêm các khoa của trường đại học
                }
            }
        });


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

        //manage student
        studentList = db_student.loadStudents();
        student_table.setItems(studentList);
        connectStudent(col_student_id, col_student_name, col_student_university, col_student_faculty, studentList,
                id_student, name_student, university_faculty, university_student);
        calculateTotalStudent();


        //manage book
        addedBookList = db_book.loadBooks();
        book_table_manage.setItems(addedBookList);
        calculateTotalBook();


        connectBook(col_book_id, col_book_title, col_book_author, col_book_genre, col_book_quantity, book_table,
                    id_book, title_book, author_book, genre_book, quantity_book, bookCoverImageView, bookList);

        connectBook(col_book_id_manage, col_book_title_manage, col_book_author_manage, col_book_genre_manage, col_book_quantity_manage, book_table_manage,
                    id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage, addedBookList);


        // view issued book

        connectIssuedBook(col_issuedID, col_studentID, col_bookName, col_issuedDate, col_dueDate, col_status, issuedBookList, issued_book_table);
        issuedBookList = db_issuedBook.loadIssuedBooks();
        issued_book_table.setItems(issuedBookList);
        calculateTotalIssuedBook();

        //
        displayAvailableBookImages(addedBookList, availableBook1_image, availableBook2_image, availableBook3_image);
        displayBorrowedBook(issuedBookList, borrowedBook1_image, borrowedBook2_image, borrowedBook3_image);
    }

    @FXML
    private void deleteBook() {
        // Lấy sách được chọn từ bảng
        Book selectedBook = book_table_manage.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            // Xóa sách khỏi danh sách
            addedBookList.remove(selectedBook);
            db_book.deleteBookFromDatabase(selectedBook.getId());
            book_table_manage.getSelectionModel().clearSelection();
            // Làm mới lại bảng
            ///book_table_manage.setItems(FXCollections.observableArrayList(addedBookList));
            book_table_manage.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book deleted successfully!");
            alert.show();
            calculateTotalBook();
            clearFields(id_book_manage, title_book_manage, author_book_manage, genre_book_manage, quantity_book_manage, bookCoverImageView_manage);
        }
    }

    private void clearFields(TextField id, TextField title, TextField author, TextField genre, TextField quantity, ImageView coverImage) {
        id.clear();
        title.clear();
        author.clear();
        genre.clear();
        quantity.clear();
        coverImage.setImage(null);

    }
    @FXML
    public void clearFieldsStudent(TextField idField, TextField nameField, ComboBox<String> facultyField, ComboBox<String> universityComboBox) {
        // Xóa nội dung TextField
        idField.clear();
        nameField.clear();
        facultyField.setValue(null);
        facultyField.getEditor().clear();

        // Đặt ComboBox về giá trị mặc định (hoặc xóa chọn)
        universityComboBox.setValue(null);
        universityComboBox.getEditor().clear();
    }

    @FXML
    public void clearFieldsIssueBook(TextField issueID, TextField studentID, TextField bookName, DatePicker issueDate,
                                     DatePicker dueDate, TextField bookID, TextField book_name, TextField book_author,
                                     TextField book_genre, TextField book_quantity, ImageView book_image) {
        issueID.clear();
        studentID.clear();
        bookName.clear();
        issueDate.setValue(null);
        dueDate.setValue(null);
        clearFields(bookID, book_name, book_author, book_genre, book_quantity, book_image);
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
                String id = generateCustomBookID(volumeInfo.path("title").asText());
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

    private String generateCustomBookID(String title) {
        String hash = Integer.toHexString(title.hashCode()).toUpperCase(); // Mã băm từ tiêu đề sách
        String randomPart = Integer.toHexString((int) (Math.random() * 0xFFFF)).toUpperCase(); // Tạo số ngẫu nhiên
        return (hash + randomPart).substring(0, 6); // Lấy 6 ký tự đầu tiên
    }

    private String generateIssuedID() {
        long currentTimeMillis = System.currentTimeMillis();
        String base36ID = Long.toString(currentTimeMillis, 36).toUpperCase();
        return base36ID.substring(base36ID.length() - 4); // Lấy 4 ký tự cuối
    }

    private String fetchBooksFromGoogleAPI(String keyword) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = "https://www.googleapis.com/books/v1/volumes?q=" + keyword + "&maxResults=40&key=AIzaSyAE5rH654BuA54v4B12Qucy0PnSOW6O5lA";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new Exception("Unexpected code " + response);

            return response.body().string();
        }
    }

    public void onCloseRequest() {
        // Lưu danh sách sách khi đóng chương trình
        db_book.saveBooks(addedBookList);
    }
}
