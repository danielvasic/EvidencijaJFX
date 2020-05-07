package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Teacher;


public class Admin {
    @FXML
    TextField firstname;

    @FXML
    TextField lastname;

    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    public void addTeacherToDatabase (ActionEvent e) throws Exception{
        Teacher t = new Teacher();
        t.setFirstname(this.firstname.getText());
        t.setLastname(this.lastname.getText());
        t.setEmail(this.email.getText());
        t.setPassword(this.password.getText());
        t.save();
    }

}
