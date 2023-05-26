module club.ppcat.minipadconfigtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;


    opens club.ppcat.minipadconfigtool to javafx.fxml;
    exports club.ppcat.minipadconfigtool;
}