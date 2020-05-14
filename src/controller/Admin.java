package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Main;
import model.Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Admin implements Initializable {
    @FXML
    TextField firstname;

    @FXML
    TextField lastname;

    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    Label loggedUserLbl;

    @FXML
    public void addTeacherToDatabase (ActionEvent e) throws Exception{
        Teacher t = new Teacher();
        t.setFirstname(this.firstname.getText());
        t.setLastname(this.lastname.getText());
        t.setEmail(this.email.getText());
        t.setPassword(this.password.getText());
        t.save();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loggedUserLbl.setText(
                Login.loggedInTeacher.getFirstname() +
                        " " +
                Login.loggedInTeacher.getLastname()
        );
    }

    @FXML
    public void logout(ActionEvent ev) throws IOException {
        Login.loggedInTeacher = null;
        Main.showWindow(
                getClass(),
                "../view/Login.fxml",
                "Login to system", 600, 215
        );
    }
}
