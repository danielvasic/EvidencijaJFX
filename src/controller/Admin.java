package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import main.Main;
import model.Teacher;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
    ImageView teacherImage;

    @FXML
    TableView<Teacher> tableView;

    @FXML
    TableColumn<Teacher, String> firstnameTblCol;

    @FXML
    TableColumn<Teacher, String> lastnameTblCol;

    @FXML
    TableColumn<Teacher, String> emailTblCol;

    @FXML
    TableColumn<Teacher, ImageView> imageTblCol;

    BufferedImage buffImage;

    Image initialImage;

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

            SerialBlob image = new SerialBlob(imageToByte(this.buffImage));
            t.setTeacherImage(image);

            t.save();
            this.populateTableView();

            this.firstname.setText("");
            this.lastname.setText("");
            this.email.setText("");
            this.password.setText("");
            this.teacherImage.setImage(this.initialImage);
        }

    }

    @FXML
    public void deleteTeacherFromDatabase(ActionEvent evt) throws Exception {
        Teacher t = tableView.getSelectionModel().getSelectedItem();
        t.delete();
        this.populateTableView();
    }

    @FXML
    public void openFileDialog(MouseEvent e) throws Exception {
        FileChooser fc = new FileChooser();
        Node node = (Node) e.getSource();
        File file = fc.showOpenDialog(node.getScene().getWindow());
        this.buffImage = ImageIO.read(file);
        this.initialImage = teacherImage.getImage();
        teacherImage.setImage(SwingFXUtils.toFXImage(buffImage, null));
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
        this.imageTblCol.setCellValueFactory(new PropertyValueFactory<>("teacherImage"));

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
            System.out.println("Nismo uspjeli dohvatiti podatke" + e.getMessage());
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

    private byte[] imageToByte(BufferedImage bufferimage) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferimage, "jpg", output );
        } catch (IOException e) {
            System.out.println("Nastala je greška: " + e.getMessage());
            e.printStackTrace();
        }
        byte [] data = output.toByteArray();
        return data;
    }
}
