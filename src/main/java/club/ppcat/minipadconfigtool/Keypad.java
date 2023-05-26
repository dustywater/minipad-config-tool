package club.ppcat.minipadconfigtool;

import com.fazecast.jSerialComm.SerialPort;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Keypad {

    private String name;
    private ArrayList<Key> keys;
    private SerialPort serialPort;



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

        String command = "get";
        serialPort.writeBytes(command.getBytes(), command.length());

        InputStream in = serialPort.getInputStream();

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

            name = data.get("name");

            keys = new ArrayList<Key>();

            String finalKey = null;

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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void apply() throws InterruptedException {
        String nameCommand = "name " + name + "\n";
        serialPort.writeBytes(nameCommand.getBytes(), nameCommand.length());


        for (Key k : keys) {
            String keyString = "key" + k.getKeyNum();

            String rtCommand =  keyString + ".rt " + k.isRt() + "\n";
            serialPort.writeBytes(rtCommand.getBytes(), rtCommand.length());
            System.out.println(rtCommand);

            String crtCommand =  keyString + ".crt " + k.isCrt() + "\n";
            serialPort.writeBytes(crtCommand.getBytes(), crtCommand.length());
            System.out.println(crtCommand);

            String rtusCommand =  keyString + ".rtus " + k.getRtus() + "\n";
            serialPort.writeBytes(rtusCommand.getBytes(), rtusCommand.length());
            System.out.println(rtusCommand);

            String rtdsCommand =  keyString + ".rtds " + k.getRtds() + "\n";
            serialPort.writeBytes(rtdsCommand.getBytes(), rtdsCommand.length());
            System.out.println(rtdsCommand);

            String uhCommand =  keyString + ".uh " + k.getUh() + "\n";
            serialPort.writeBytes(uhCommand.getBytes(), uhCommand.length());
            System.out.println(uhCommand);

            String lhCommand =  keyString + ".lh " + k.getLh() + "\n";
            serialPort.writeBytes(lhCommand.getBytes(), lhCommand.length());
            System.out.println(lhCommand);

            serialPort.writeBytes(uhCommand.getBytes(), uhCommand.length());

            String restCommand = keyString + ".rest " + k.getRest() + "\n";
            serialPort.writeBytes(restCommand.getBytes(), restCommand.length());
            System.out.println(restCommand);

            String downCommand = keyString + ".down " + k.getDown() + "\n";
            serialPort.writeBytes(downCommand.getBytes(), downCommand.length());
            System.out.println(downCommand);

            serialPort.writeBytes(restCommand.getBytes(), restCommand.length());
            System.out.println(restCommand);

            String hidCommand = keyString + ".hid " + k.isHid() + "\n";
            serialPort.writeBytes(hidCommand.getBytes(), hidCommand.length());
            System.out.println(hidCommand);

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
