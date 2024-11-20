package application.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    private int count = 1;
    @FXML
    private TextField author;

    @FXML
    private Button cancel;

    @FXML
    private TextField id;

    @FXML
    private Label label;

    @FXML
    private TextField publisher;

    @FXML
    private Button save;

    @FXML
    private TextField title;

    @FXML
    void addBook(ActionEvent event) {
        label.setText("Added" + count++);
    }

}