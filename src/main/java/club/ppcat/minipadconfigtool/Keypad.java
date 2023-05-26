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

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setKeys(ArrayList<Key> keys) {
        this.keys = keys;
    }

    public void addKey(Key keyObj) {
        keys.add(keyObj);
    }

    public void update() {

        Map<String, String> data = SerialManager.get(serialPort);
        keys = new ArrayList<Key>();

        String finalKey = null;

        name = data.get("name");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();

            if (key.startsWith("key")) {
                finalKey = key;
            }
        }

        String keyNumber = finalKey.substring(3, finalKey.indexOf('.'));
        int numKeys = Integer.parseInt(keyNumber);


        for (int i = 0; i < numKeys; i++) {
            String k = "key" + (i + 1);
            int rt = Integer.parseInt(data.get(k + ".rt"));
            int crt = Integer.parseInt(data.get(k + ".crt"));
            int rtus = Integer.parseInt(data.get(k + ".rtus"));
            int rtds = Integer.parseInt(data.get(k + ".rtds"));
            int lh = Integer.parseInt(data.get(k + ".lh"));
            int uh = Integer.parseInt(data.get(k + ".uh"));
            int rest = Integer.parseInt(data.get(k + ".rest"));
            int down = Integer.parseInt(data.get(k + ".down"));
            int hid = Integer.parseInt(data.get(k + ".hid"));
            int trdt = Integer.parseInt(data.get("trdt"));

            Key key = new Key(i + 1, rt, crt, rest, down, hid, rtds, rtus, lh, uh, trdt);
            keys.add(key);
        }


        System.out.println("name = " + name);

    }

    public void apply() throws InterruptedException {
        String nameCommand = "name " + name + "\n";
        serialPort.writeBytes(nameCommand.getBytes(), nameCommand.length());


        for (Key k : keys) {
            String keyString = "key" + k.getKeyNum();

            // Build serial commands
            String rtCommand = keyString + ".rt " + k.isRt();
            String crtCommand = keyString + ".crt " + k.isCrt();
            String rtusCommand = keyString + ".rtus " + k.getRtus();
            String rtdsCommand = keyString + ".rtds " + k.getRtds();
            String uhCommand = keyString + ".uh " + k.getUh();
            String lhCommand = keyString + ".lh " + k.getLh();
            String restCommand = keyString + ".rest " + k.getRest();
            String downCommand = keyString + ".down " + k.getDown();
            String hidCommand = keyString + ".hid " + k.isHid();

            // Send serial commands to keypad, uh and rest sent twice in case rest/uh is lower than old down/lh
            SerialManager.sendSerial(serialPort, rtCommand);
            SerialManager.sendSerial(serialPort, crtCommand);

            SerialManager.sendSerial(serialPort, rtusCommand);
            SerialManager.sendSerial(serialPort, rtdsCommand);

            SerialManager.sendSerial(serialPort, uhCommand);
            SerialManager.sendSerial(serialPort, lhCommand);
            SerialManager.sendSerial(serialPort, uhCommand);

            SerialManager.sendSerial(serialPort, restCommand);
            SerialManager.sendSerial(serialPort, downCommand);
            SerialManager.sendSerial(serialPort, restCommand);

            SerialManager.sendSerial(serialPort, hidCommand);


        }
    }


    public void save() {
        String saveCommand = "save" + "\n";
        serialPort.writeBytes(saveCommand.getBytes(), saveCommand.length());
    }

    public void flash() {
        String flashCommand = "flash" + "\n";
        serialPort.writeBytes(flashCommand.getBytes(), flashCommand.length());
    }

}
