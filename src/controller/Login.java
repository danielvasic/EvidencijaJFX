package controller;

import main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login {
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
                Main.showWindow(
                        getClass(),
                        "../view/Admin.fxml",
                        "Dobrodošli u administraciju", 600, 400
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
