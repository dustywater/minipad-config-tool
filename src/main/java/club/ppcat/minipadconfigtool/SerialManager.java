package club.ppcat.minipadconfigtool;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SerialManager {


    public static void sendSerial(SerialPort sp, String command) {
        command += "\n";
        sp.writeBytes(command.getBytes(), command.length());
        System.out.println(command);
    }

    public static SerialPort openPort(String serialPath) {
        SerialPort sp = SerialPort.getCommPort(serialPath);

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

        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING | SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 5000, 5000);

        return sp;
    }

    public static Map<String, String> get(SerialPort sp) {
        sendSerial(sp, "get");

        InputStream in = sp.getInputStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            Map<String, String> data = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("GET END")) {
                    break;
                }

                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim().replace("GET ", "");
                    String value = parts[1].trim();
                    data.put(key, value);
                }

            }
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
