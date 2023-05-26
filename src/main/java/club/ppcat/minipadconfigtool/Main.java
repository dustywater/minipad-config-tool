package club.ppcat.minipadconfigtool;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.fazecast.jSerialComm.*;
import javafx.stage.Window;

import java.io.IOException;
import java.util.stream.Stream;


public class Main extends javafx.application.Application {

    public static Keypad keypad;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("selectport.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 600);
        stage.setTitle("minipad config tool");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();
    }

    public static void initialize(String serialPort) {
        SerialPort sp = SerialPort.getCommPort(serialPort);

        if (sp.openPort()) {
            System.out.println("success");
        } else {
            System.out.println("not success");
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                sp.closePort();
            }
        });

        sp.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);

        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING | SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        keypad = new Keypad(sp);
        keypad.update();
        System.out.println(keypad.getName());
        System.out.println(keypad.getKeys().get(1).getDown());

        try {
            Stream<Window> openWindows = Stage.getWindows().stream().filter(Window::isShowing);
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("mainconfig.fxml"));
            Stage stage = (Stage) openWindows.toArray()[0];
            stage.setTitle("Configuring " + keypad.getName());
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}