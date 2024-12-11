package project.libraryassistant;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private TextField account;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button sign_up_btn_login;

    /// sign up.
    @FXML
    private AnchorPane sign_up_form;

    @FXML
    private TextField account_register;

    @FXML
    private TextField password_register;

    @FXML
    private TextField username_register;

    @FXML
    private Button register;

    @FXML
    private Button login_btn_register;

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

    @FXML
    private void signup_form() {
        login_form.setVisible(false);
        account.setText(null);
        password.setText(null);
        sign_up_form.setVisible(true);
    }

    @FXML
    private void loginForm() {
        login_form.setVisible(true);
        sign_up_form.setVisible(false);
        account_register.setText(null);
        password_register.setText(null);
        username_register.setText(null);
    }

    private String accountNumber;

    public void register() {
        if (account_register.getText() == null || account_register.getText().trim().isEmpty() ||
                password_register.getText() == null || password_register.getText().trim().isEmpty() ||
                username_register.getText() == null || username_register.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter account number, password, and username!");
            alert.showAndWait();
            return;
        } else {
            String account = account_register.getText().trim();
            for (Login login : loginList) {
                if (login.getAccount().equals(account)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Account already exists!");
                    alert.showAndWait();
                    return;
                }
            }
            Login newLogin = new Login(account, password_register.getText().trim(), username_register.getText().trim(), "");
            db_login.saveLogins(FXCollections.observableArrayList(newLogin));
            loginList.add(newLogin);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Account successfully registered and Press LOGIN button to login");
            alert.showAndWait();
        }
    }


    public void login() {
        String sql = "SELECT * FROM admin where account = ? and password = ?";
        connect = DatabaseLogin.getConnection();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, account.getText());
            prepare.setString(2, password.getText());
            rs = prepare.executeQuery();
            Alert alert;
            if (account.getText().isEmpty() || password.getText().isEmpty()) {
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
                    getData.path = rs.getString("image");
                    getData.account = rs.getString("account");

                    // to hide login form
                    login_btn.getScene().getWindow().hide();

                    // dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Parent root = loader.load();
                    DashboardController controller = loader.getController();

                    //set username
                    String userName = rs.getString("username");
                    controller.setUserName(userName);


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
                    stage.setOnCloseRequest(event -> {
                        controller.onCloseRequest();
                    });
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

    DatabaseLogin db_login = new DatabaseLogin();
    ObservableList<Login> loginList = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        loginList = db_login.loadLogins();
    }
}
