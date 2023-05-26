package club.ppcat.minipadconfigtool;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainConfigController {
    @FXML
    private TextField nameBox;

    @FXML
    private Button applyButton, flashButton;

    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        nameBox.setText(Main.keypad.getName());
        try {
            for (Key key : Main.keypad.getKeys()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("keyconfig.fxml"));
                Pane keyConfigPane = loader.load();

                KeyConfigController keyConfigController = loader.getController();

                keyConfigController.setKey(key);
                keyConfigController.populate();

                Tab tab = new Tab("Key" + key.getKeyNum());
                tab.setContent(keyConfigPane);

                tabPane.getTabs().add(tab);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void nameBoxChange() {
        System.out.println(nameBox.getText());
        Main.keypad.setName(nameBox.getText());
        System.out.println("set name to " + Main.keypad.getName());
    }

    @FXML
    public void applyButtonClicked() {
        applyButton.setDisable(true);
        applyButton.setText("Applying");
        try {
            Main.keypad.apply();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        applyButton.setDisable(false);
        applyButton.setText("Apply");

    }

    @FXML
    public void saveButtonClicked() {
        Main.keypad.save();
    }

    @FXML
    public void flashButtonClicked() {
        Main.keypad.flash();
        Stage window = (Stage) flashButton.getScene().getWindow();
        window.close();
    }
}