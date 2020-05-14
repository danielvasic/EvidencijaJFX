package controller;

import main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Teacher;

import java.io.IOException;

public class Login {
    public static Teacher loggedInTeacher;

    @FXML
    Button prijaviSeBtn;

    @FXML
    TextField korisnickoImeTxt;

    @FXML
    TextField lozinkaTxt;

    @FXML
    Label greskaLbl;

    @FXML
    Label uspjehLbl;

    @FXML
    public void prijaviSe(ActionEvent ev){
        String korisnickoIme = this.korisnickoImeTxt.getText().toString();
        String lozinka = this.lozinkaTxt.getText().toString();

        if (korisnickoIme.equals("") || lozinka.equals("")){
            greskaLbl.setVisible(true);
            uspjehLbl.setVisible(false);
        } else {
            greskaLbl.setVisible(false);
            uspjehLbl.setVisible(true);

            try {
                Login.loggedInTeacher = Teacher.login(korisnickoIme, lozinka);
                if (Login.loggedInTeacher != null) {
                    Main.showWindow(
                            getClass(),
                            "../view/Admin.fxml",
                            "Welcome to administration", 600, 400
                    );
                } else {
                    greskaLbl.setText("Unesite ispravne korisničke podatke");
                    greskaLbl.setVisible(true);
                    uspjehLbl.setVisible(false);
                }

            } catch (Exception e) {
                System.out.println("Nastala je greška " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
