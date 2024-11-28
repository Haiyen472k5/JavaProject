package project.libraryassistant;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.animation.Transition.*;

public class DashboardController {
    @FXML private Button close;

    @FXML private Button minimize;
    @FXML
    private ImageView availableBook1_image;

    @FXML
    private ImageView availableBook2_image;

    @FXML
    private ImageView availableBook3_image;

    @FXML
    private ImageView borrowedBook1_image;

    @FXML
    private ImageView borrowedBook2_image;

    @FXML
    private ImageView borrowedBook3_image;

    @FXML
    private Circle circle_image;

    @FXML
    private Button home_page;

    @FXML
    private AnchorPane home_page_anchor;

    @FXML
    private Button issue_book;

    @FXML
    private Button logout_btn;

    @FXML
    private Button manage_book;

    @FXML
    private Button manage_member;

    @FXML
    private Label no_book;

    @FXML
    private Label no_issuedBook;

    @FXML
    private Label no_member;

    @FXML
    private Button return_book;

    @FXML
    private AnchorPane left_form;

    @FXML
    private Button bars_btn;

    @FXML
    private Button chevron_btn;

    @FXML
    private Button view_issued_book;
    private double x = 0;
    private double y = 0;

    @FXML
    public void logout(ActionEvent event) {
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
        slide2.setNode(home_page_anchor);
        slide2.setToX(-189);

        slide.setOnFinished((ActionEvent event) -> {
            bars_btn.setVisible(true);
            chevron_btn.setVisible(false);
        });

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
        slide2.setNode(home_page_anchor);
        slide2.setToX(0);

        slide.setOnFinished((ActionEvent event) -> {
            bars_btn.setVisible(false);
            chevron_btn.setVisible(true);
        });

        slide2.play();
        slide.play();

    }

}
