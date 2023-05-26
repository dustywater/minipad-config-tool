package club.ppcat.minipadconfigtool;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
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
        Main.initialize(selectedPort);
    }

    @FXML
    public void portBoxChange() {
        String port = portBox.getValue().toString();
        String[] parts = port.split(": ");
        selectedPort = parts[1];
        System.out.println(selectedPort);
    }


}