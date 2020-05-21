package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.Main;
import model.Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
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
    TableView<Teacher> tableView;

    @FXML
    TableColumn<Teacher, String> firstnameTblCol;

    @FXML
    TableColumn<Teacher, String> lastnameTblCol;

    @FXML
    TableColumn<Teacher, String> emailTblCol;

    @FXML
    public void addTeacherToDatabase (ActionEvent e) throws Exception{
        if(!this.firstname.getText().equals("") &&
                !this.lastname.getText().equals("") &&
                !this.email.getText().equals("") &&
                !this.password.getText().equals("")) {
            Teacher t = new Teacher();
            t.setFirstname(this.firstname.getText());
            t.setLastname(this.lastname.getText());
            t.setEmail(this.email.getText());
            t.setPassword(this.password.getText());
            t.save();
            this.populateTableView();

            this.firstname.setText("");
            this.lastname.setText("");
            this.email.setText("");
            this.password.setText("");
        }

    }

    @FXML
    public void deleteTeacherFromDatabase(ActionEvent evt) throws Exception {
        Teacher t = tableView.getSelectionModel().getSelectedItem();
        t.delete();
        this.populateTableView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loggedUserLbl.setText(
                Login.loggedInTeacher.getFirstname() +
                        " " +
                Login.loggedInTeacher.getLastname()
        );

        this.firstnameTblCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        this.lastnameTblCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        this.emailTblCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        this.firstnameTblCol.setEditable(true);

        /* Evo šta nam je falilo :-) */
        this.firstnameTblCol.setCellFactory(TextFieldTableCell.forTableColumn());

        this.lastnameTblCol.setEditable(true);

        /* Evo šta nam je falilo :-) */
        this.lastnameTblCol.setCellFactory(TextFieldTableCell.forTableColumn());

        this.populateTableView();
    }

    @FXML
    public void editFirstnameTeacherToDatabase(TableColumn.CellEditEvent<Teacher, String> evt) throws Exception {
        Teacher t = evt.getRowValue();
        t.setFirstname(evt.getNewValue());
        t.update();
    }

    @FXML
    public void editLastnameTeacherToDatabase(TableColumn.CellEditEvent<Teacher, String> evt) throws Exception {
        Teacher t = evt.getRowValue();
        t.setLastname(evt.getNewValue());
        t.update();
    }

    private void populateTableView(){
        try {
            this.tableView.getItems().setAll((Collection<? extends Teacher>) Teacher.list(Teacher.class));
            this.tableView.setEditable(true);
        } catch (Exception e) {
            System.out.println("Nismo uspjeli dohvatiti podatke");
        }
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
