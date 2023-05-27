package club.ppcat.minipadconfigtool;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class KeyConfigController {

    Key key;

    @FXML
    private Slider rtusSlider, rtdsSlider, uhSlider, lhSlider;

    @FXML
    private CheckBox rtCheckBox, crtCheckBox, hidCheckBox;

    @FXML
    private Label rtusIndicator, rtdsIndicator, uhIndicator, lhIndicator;

    @FXML
    private TextField keyBox;



    public void populate() {
        setCheckBox(key.isRt(), rtCheckBox);
        setCheckBox(key.isCrt(), crtCheckBox);
        setCheckBox(key.isHid(), hidCheckBox);

        // not sure what max should be for this yet
        rtusSlider.setMax((double) key.getTrdt() / 100);
        rtdsSlider.setMax((double) key.getTrdt() / 100);

        uhSlider.setMax((double) (key.getTrdt() - 10) / 100);
        lhSlider.setMax((double) (key.getTrdt() - 20) / 100);

        rtusSlider.setValue((double) key.getRtus() / 100);
        rtdsSlider.setValue((double) key.getRtds() / 100);

        uhSlider.setValue((double) key.getUh() / 100);
        lhSlider.setValue((double) key.getLh() / 100);

        rtusIndicator.setText(rtusSlider.getValue() + "mm");
        rtdsIndicator.setText(rtdsSlider.getValue() + "mm");

        uhIndicator.setText(uhSlider.getValue() + "mm");
        lhIndicator.setText(lhSlider.getValue() + "mm");

        keyBox.setText(String.valueOf(key.getKey()));

        rtusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            rtusSliderChange(newValue.doubleValue());
        });

        rtdsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            rtdsSliderChange(newValue.doubleValue());
        });

        uhSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            uhSliderChange(newValue.doubleValue());
        });

        lhSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            lhSliderChange(newValue.doubleValue());
        });
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setCheckBox(int value, CheckBox checkBox) {
        if (value == 0) {
            checkBox.setSelected(false);
        } else {
            checkBox.setSelected(true);
        }
    }

    @FXML
    public void rtusSliderChange(Double newValue) {
        double roundedValue = Math.round(newValue.doubleValue() * 20) / 20.0;
        rtusIndicator.setText(roundedValue + "mm");
        int newRtus = (int) (roundedValue * 100);
        key.setRtus(newRtus);
    }

    @FXML
    public void rtdsSliderChange(Double newValue) {
        double roundedValue = Math.round(newValue.doubleValue() * 20) / 20.0;
        rtdsIndicator.setText(roundedValue + "mm");
        int newRtds = (int) (roundedValue * 100);
        key.setRtds(newRtds);
    }

    @FXML
    public void uhSliderChange(Double newValue) {
        double roundedValue = Math.round(newValue.doubleValue() * 20) / 20.0;
        uhIndicator.setText(roundedValue + "mm");
        int newUh = (int) (roundedValue * 100);
        key.setUh(newUh);
    }

    @FXML
    public void lhSliderChange(Double newValue) {
        double roundedValue = Math.round(newValue.doubleValue() * 20) / 20.0;
        lhIndicator.setText(roundedValue + "mm");
        int newLh = (int) (roundedValue * 100);
        key.setLh(newLh);
    }

    @FXML
    public void rtCheckBoxClicked() {
        if (rtCheckBox.isSelected()) {
            key.setRt(1);
        } else {
            key.setRt(0);
        }
    }

    @FXML
    public void crtCheckBoxClicked() {
        if (crtCheckBox.isSelected()) {
            key.setCrt(1);
        } else {
            key.setCrt(0);
        }
    }


    @FXML
    public void hidCheckBoxClicked() {
        if (hidCheckBox.isSelected()) {
            key.setHid(1);
        } else {
            key.setHid(0);
        }
    }

    @FXML
    public void keyBoxChange() {
        if (!keyBox.getText().isEmpty()) {
            key.setKey(keyBox.getText().charAt(0));
        }
    }

}
