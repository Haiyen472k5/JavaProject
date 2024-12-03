package project.libraryassistant;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class HelloController {
    @FXML
    private Button close;

    @FXML
    private Button login_btn;

    @FXML
    private Button minimize;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    public void minimize() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void exit() {
        System.exit(0);
    }
    private double x = 0;
    private double y = 0;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet rs;
    private Statement statement;
    public void login() {
        String sql = "SELECT * FROM admin where username = ? and password = ?";
        connect = Database.getConnection();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            rs = prepare.executeQuery();
            Alert alert;
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter your username and password!");
                alert.showAndWait();
            } else {
                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Message");
                    alert.setHeaderText(null);
                    alert.setContentText("You have successfully logged in.");
                    alert.showAndWait();
                    // to hide login form
                    login_btn.getScene().getWindow().hide();
                    // dashboard
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password.");
                    alert.showAndWait();
                }
            }


        } catch(Exception e) {
            e.printStackTrace();
        }

    }



}
