package app;

import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;


public class Controller {
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
    TextField serviceValue;
    @FXML
    TextField customerArrivalValue;
    @FXML
    TextField breakdownValue;
    @FXML
    TextField reparationValue;
    @FXML
    TextField impatienceValue;
    @FXML
    TextField queueCapacityValue;
    @FXML
    TextField clientLimitValue;
    @FXML
    TextField numberOfWindowsValue;
    @FXML
    ChoiceBox<String> prioritiesValue;
    @FXML
    TextField serviceLengthValue;


    private Simulation simulation;
    static boolean isSimulationActive = false;
    private Drawer drawer;
    private double service;
    private double customerArrival;
    private double breakdown;
    private double reparation;
    private double impatience;
    private double maxSizeOfQueue;
    private double maxLimitOfClients;
    private double numberOfWindows;
    private double numberOfPriorities;
    private double serviceLength;
    private ArrayList<Control> controlArrayList = new ArrayList<>();


    public void startSimulation() {
        if (!isSimulationActive) {
            getSimulationValues();
            simulation = new Simulation(service, customerArrival, breakdown, reparation, impatience, (int) maxSizeOfQueue,
                    (int) maxLimitOfClients, (int) numberOfWindows, (int) numberOfPriorities, (int) serviceLength, controlArrayList);
            simulation.setDaemon(true);

            drawer = new Drawer(visualizationPane, simulation.getBank(), sliderSpeed);
            drawer.setDaemon(true);

            drawer.setDraw();
            drawer.start();
            simulation.start();
            isSimulationActive = true;
            simulation.getSimManager().setEndSimTime(sliderLength.getValue());
            sliderLength.setDisable(true);
            sliderSpeed.setDisable(true);
        } else {
            simulation.getSimManager().resumeSimulation();
        }
        startButton.setDisable(true);
    }

    private void getSimulationValues() {
        service = (getValueFromTextField(serviceValue) == -9.999 ? 2.0 : getValueFromTextField(serviceValue));
        serviceValue.setDisable(true);
        customerArrival = (getValueFromTextField(customerArrivalValue) == -9.999 ? 1 : getValueFromTextField(customerArrivalValue));
        customerArrivalValue.setDisable(true);
        breakdown = (getValueFromTextField(breakdownValue) == -9.999 ? 14 : getValueFromTextField(breakdownValue));
        breakdownValue.setDisable(true);
        reparation = (getValueFromTextField(reparationValue) == -9.999 ? 1 : getValueFromTextField(reparationValue));
        reparationValue.setDisable(true);
        impatience = (getValueFromTextField(impatienceValue) == -9.999 ? 20 : getValueFromTextField(impatienceValue));
        impatienceValue.setDisable(true);
        maxSizeOfQueue = (getValueFromTextField(queueCapacityValue) == -9.999 ? 50 : getValueFromTextField(queueCapacityValue));
        queueCapacityValue.setDisable(true);
        maxLimitOfClients = (getValueFromTextField(clientLimitValue) == -9.999 ? 100 : getValueFromTextField(clientLimitValue));
        clientLimitValue.setDisable(true);
        numberOfWindows = (getValueFromTextField(numberOfWindowsValue) == -9.999 ? 6 : getValueFromTextField(numberOfWindowsValue));
        numberOfWindowsValue.setDisable(true);
        numberOfPriorities = Double.parseDouble(prioritiesValue.getValue());
        prioritiesValue.setDisable(true);
        serviceLength = (getValueFromTextField(serviceLengthValue) == -9.999 ? 2 : getValueFromTextField(serviceLengthValue));
        serviceLengthValue.setDisable(true);
        controlArrayList.add(serviceLengthValue);
        controlArrayList.add(serviceValue);
        controlArrayList.add(customerArrivalValue);
        controlArrayList.add(breakdownValue);
        controlArrayList.add(reparationValue);
        controlArrayList.add(impatienceValue);
        controlArrayList.add(queueCapacityValue);
        controlArrayList.add(clientLimitValue);
        controlArrayList.add(numberOfWindowsValue);
        controlArrayList.add(prioritiesValue);
        controlArrayList.add(serviceLengthValue);
        controlArrayList.add(sliderSpeed);
        controlArrayList.add(sliderLength);
        controlArrayList.add(startButton);
    }

    private double getValueFromTextField(TextField textField) {
        double value;
        try {
            value = Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            return -9.999;
        }
        return value;
    }

    public void pauseSimulation() {
        if (SimManager.controlState != SimParameters.SimProcessStatus.PAUSED) {
            simulation.getSimManager().pauseSimulation();
        } else {
            simulation.getSimManager().resumeSimulation();
        }
    }
}
