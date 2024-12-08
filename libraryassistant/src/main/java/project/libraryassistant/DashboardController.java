package project.libraryassistant;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.animation.Transition.*;

public class DashboardController {
    /// top
    @FXML
    private Button close;

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

    /// left.
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
     * left form.
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
    private Button manage_member;

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
    private Button manage_book_hl;

    @FXML
    private Button manage_member_hl;

    @FXML
    private Button issue_book_hl;

    @FXML
    private Button return_book_hl;

    @FXML
    private Button view_issued_book_hl;

    @FXML
    private Button logout_btn_hl;

    @FXML
    void logout_hl(ActionEvent event) {
        logout_main(event, logout_btn_hl);
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
    private Label no_issuedBook;

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
    private ImageView bookCoverImageView;

    @FXML
    private TextField id_book;

    @FXML
    private TextField title_book;

    @FXML
    private TextField author_book;

    @FXML
    private TextField genre_book;

    @FXML
    private TextField quantity_book;

    @FXML
    private Button delete_book_btn;

    @FXML
    private Button add_book_btn;

    @FXML
    private Button update_book_btn;

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
    private Button search_book_btn;

    private TextField search_book;

    /**
     * manage student.
     */
    @FXML
    private TextField search_student;

    @FXML
    private Button search_student_btn;

    @FXML
    private TableView<Student> student_table;

    @FXML
    private TableColumn<Student, String> col_student_id;

    @FXML
    private TableColumn<Student, String> col_student_name;

    @FXML
    private TableColumn<Student, String> col_student_university;

    @FXML
    private TableColumn<Student, String> col_student_faculty;

    @FXML
    private TextField id_student;

    @FXML
    private TextField name_student;

    @FXML
    private TextField faculty_student;

    @FXML
    private ComboBox<String> university_student;

    @FXML
    private Button add_student_btn;

    @FXML
    private Button delete_student_btn;

    private String[] uni = {"UET", "ULIS", "UEB", "UEd", "VJU", "UL", "HUS", "UMP", "IS", "SIS"};
    public void initialize() {
        university_student.getItems().addAll(uni);

    }

}
