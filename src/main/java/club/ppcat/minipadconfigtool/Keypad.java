package club.ppcat.minipadconfigtool;

import com.fazecast.jSerialComm.SerialPort;

import java.util.ArrayList;
import java.util.Map;

public class Keypad {

    private String name;
    private ArrayList<Key> keys;
    private final SerialPort serialPort;


    public Keypad(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }

    public void update() {

        Map<String, String> data = SerialManager.get(serialPort);
        keys = new ArrayList<Key>();

        String finalKey = null;

        name = data.get("name");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();

            if (key.startsWith("hkey")) {
                finalKey = key;
            }
        }

        String keyNumber = finalKey.substring(4, finalKey.indexOf('.'));
        int numKeys = Integer.parseInt(keyNumber);


        for (int i = 0; i < numKeys; i++) {
            String k = "hkey" + (i + 1);
            int rt = Integer.parseInt(data.get(k + ".rt"));
            int crt = Integer.parseInt(data.get(k + ".crt"));
            int rtus = Integer.parseInt(data.get(k + ".rtus"));
            int rtds = Integer.parseInt(data.get(k + ".rtds"));
            int lh = Integer.parseInt(data.get(k + ".lh"));
            int uh = Integer.parseInt(data.get(k + ".uh"));
            int hid = Integer.parseInt(data.get(k + ".hid"));
            int trdt = Integer.parseInt(data.get("trdt"));
            char keyChar = (char) Integer.parseInt(data.get(k + ".char"));

            Key key = new Key(i + 1, rt, crt, hid, rtds, rtus, lh, uh, trdt, keyChar);
            keys.add(key);
        }


        System.out.println("name = " + name);

    }

    public void apply() throws InterruptedException {
        String nameCommand = "name " + name + "\n";
        serialPort.writeBytes(nameCommand.getBytes(), nameCommand.length());


        for (Key k : keys) {
            String keyString = "hkey" + k.getKeyNum();

            // Build serial commands
            String rtCommand = keyString + ".rt " + k.isRt();
            String crtCommand = keyString + ".crt " + k.isCrt();
            String rtusCommand = keyString + ".rtus " + k.getRtus();
            String rtdsCommand = keyString + ".rtds " + k.getRtds();
            String uhCommand = keyString + ".uh " + k.getUh();
            String lhCommand = keyString + ".lh " + k.getLh();
            String keyCommand = keyString + ".char " + (int) k.getKey();

            String hidCommand = keyString + ".hid " + k.isHid();

            // Send serial commands to keypad, uh sent twice in case uh is lower than old lh
            SerialManager.sendSerial(serialPort, rtCommand);
            SerialManager.sendSerial(serialPort, crtCommand);

            SerialManager.sendSerial(serialPort, rtusCommand);
            SerialManager.sendSerial(serialPort, rtdsCommand);

            SerialManager.sendSerial(serialPort, uhCommand);
            SerialManager.sendSerial(serialPort, lhCommand);
            SerialManager.sendSerial(serialPort, uhCommand);

            SerialManager.sendSerial(serialPort, hidCommand);
            SerialManager.sendSerial(serialPort, keyCommand);


        }
    }


    public void save() {
        SerialManager.sendSerial(serialPort,"save");
    }

    public void flash() {
        SerialManager.sendSerial(serialPort, "boot");
    }

}
