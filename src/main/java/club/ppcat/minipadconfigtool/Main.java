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

    public static void initialize(String serialPath) {
        SerialPort sp = SerialManager.openPort(serialPath);
        keypad = new Keypad(sp);
        keypad.update();

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