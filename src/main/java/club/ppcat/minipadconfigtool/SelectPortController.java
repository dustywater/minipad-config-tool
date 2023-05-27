package club.ppcat.minipadconfigtool;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortTimeoutException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class SelectPortController {
    String selectedPort;

    @FXML
    private ComboBox portBox;

    @FXML
    private Button startButton;

    @FXML
    public void initialize() {
        for (SerialPort s : SerialPort.getCommPorts()) {
            portBox.getItems().add(s.getDescriptivePortName() + ": " + s.getSystemPortPath());
        }

    }

    @FXML
    public void startButtonClick() {
        try {
            Main.initialize(selectedPort);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot access keypad. The port may already be in use by another application, you may not have permission to access it, the keypad firmware could be incompatible, or you selected the wrong port.");
            alert.showAndWait();

        }

    }


    @FXML
    public void portBoxChange() {
        String port = portBox.getValue().toString();
        String[] parts = port.split(": ");
        selectedPort = parts[1];
        System.out.println(selectedPort);
    }


}