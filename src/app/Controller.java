package app;

import dissimlab.simcore.SimParameters;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;


public class Controller
{
    @FXML
    AnchorPane mainPane;
    @FXML
    AnchorPane supportPane;
    @FXML
    AnchorPane visualizationPane;
    @FXML
    Slider sliderSpeed;
    @FXML
    Button startButton;
    @FXML
    Slider sliderLength;
    @FXML
    TextField obslugaValue;
    @FXML
    TextField przyjscieKlientaValue;
    @FXML
    TextField awariaValue;
    @FXML
    TextField naprawaValue;
    @FXML
    TextField zniecierpliwienieValue;
    @FXML
    TextField pojemnoscKolejkiValue;
    @FXML
    TextField limitKlientowValue;
    @FXML
    TextField iloscOkienekValue;
    @FXML
    ChoiceBox<String> priorytetyValue;
    @FXML
    TextField dlugoscObslugiValue;


    private Simulation symulacja;
    static boolean symulacjaAktywna = false;
    private Drawer drawer;
    private double obsluga;
    private double przyjscieKlienta;
    private double awaria;
    private double naprawa;
    private double niecierpliwosc;
    private double maxKolejka;
    private double maxKlientow;
    private double iloscOkienek;
    private double iloscPriorytetow;
    private double dlugoscObslugi;
    private ArrayList<Control> controlArrayList = new ArrayList<>();


    public void startSimulation()
    {
        if (!symulacjaAktywna)
        {
            getSimulationValues();
            symulacja = new Simulation(obsluga, przyjscieKlienta, awaria, naprawa, niecierpliwosc, (int) maxKolejka,
                    (int) maxKlientow, (int) iloscOkienek, (int) iloscPriorytetow, (int) dlugoscObslugi, controlArrayList);
            symulacja.setDaemon(true);

            drawer = new Drawer(visualizationPane, symulacja.getBank(), sliderSpeed);
            drawer.setDaemon(true);

            drawer.setDraw(true);
            drawer.start();
            symulacja.start();
            symulacjaAktywna = true;
            symulacja.getSimManager().setEndSimTime(sliderLength.getValue());
            sliderLength.setDisable(true);
            sliderSpeed.setDisable(true);
        } else
        {
            symulacja.getSimManager().resumeSimulation();
        }
        startButton.setDisable(true);
    }

    private void getSimulationValues()
    {
        obsluga = (getValueFromTextField(obslugaValue) == -9.999 ? 2.0 : getValueFromTextField(obslugaValue));
        obslugaValue.setDisable(true);
        przyjscieKlienta = (getValueFromTextField(przyjscieKlientaValue) == -9.999 ? 1 : getValueFromTextField(przyjscieKlientaValue));
        przyjscieKlientaValue.setDisable(true);
        awaria = (getValueFromTextField(awariaValue) == -9.999 ? 14 : getValueFromTextField(awariaValue));
        awariaValue.setDisable(true);
        naprawa = (getValueFromTextField(naprawaValue) == -9.999 ? 1 : getValueFromTextField(naprawaValue));
        naprawaValue.setDisable(true);
        niecierpliwosc = (getValueFromTextField(zniecierpliwienieValue) == -9.999 ? 20 : getValueFromTextField(zniecierpliwienieValue));
        zniecierpliwienieValue.setDisable(true);
        maxKolejka = (getValueFromTextField(pojemnoscKolejkiValue) == -9.999 ? 50 : getValueFromTextField(pojemnoscKolejkiValue));
        pojemnoscKolejkiValue.setDisable(true);
        maxKlientow = (getValueFromTextField(limitKlientowValue) == -9.999 ? 100 : getValueFromTextField(limitKlientowValue));
        limitKlientowValue.setDisable(true);
        iloscOkienek = (getValueFromTextField(iloscOkienekValue) == -9.999 ? 6 : getValueFromTextField(iloscOkienekValue));
        iloscOkienekValue.setDisable(true);
        iloscPriorytetow = Double.valueOf(priorytetyValue.getValue());
        priorytetyValue.setDisable(true);
        dlugoscObslugi = (getValueFromTextField(dlugoscObslugiValue) == -9.999 ? 2 : getValueFromTextField(dlugoscObslugiValue));
        dlugoscObslugiValue.setDisable(true);
        controlArrayList.add(dlugoscObslugiValue);
        controlArrayList.add(obslugaValue);
        controlArrayList.add(przyjscieKlientaValue);
        controlArrayList.add(awariaValue);
        controlArrayList.add(naprawaValue);
        controlArrayList.add(zniecierpliwienieValue);
        controlArrayList.add(pojemnoscKolejkiValue);
        controlArrayList.add(limitKlientowValue);
        controlArrayList.add(iloscOkienekValue);
        controlArrayList.add(priorytetyValue);
        controlArrayList.add(dlugoscObslugiValue);
        controlArrayList.add(sliderSpeed);
        controlArrayList.add(sliderLength);
        controlArrayList.add(startButton);
    }

    private double getValueFromTextField(TextField textField)
    {
        double value;
        try
        {
            value = Double.valueOf(textField.getText());
        } catch (NumberFormatException e)
        {
            return -9.999;
        }
        return value;
    }

    public void pauseSimulation()
    {
        if (symulacja.getSimManager().controlState != SimParameters.SimProcessStatus.PAUSED)
        {
            symulacja.getSimManager().pauseSimulation();
        }
        else
        {
            symulacja.getSimManager().resumeSimulation();
        }
    }
}
