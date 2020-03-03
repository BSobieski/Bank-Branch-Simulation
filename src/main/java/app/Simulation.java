package app;

import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;
import javafx.scene.control.Control;
import objects.Bank;
import objects.Environment;
import events.WindowBreakdown;
import events.CustomerArrival;

import java.util.ArrayList;

public class Simulation extends Thread {
    private Bank bank;
    private SimManager simManager;
    private ArrayList<Control> controlArrayList;

    Simulation(double windowServingTimeDelay, double customerArrivalTimeDelay, double breakdownTimeDelay, double reparationTimeDelay, double impatienceTimeDelay, int queueMaxSize,
               int maxLimitOfCustomers, int numberOfWindows, int numberOfPriorities, int servingTime, ArrayList<Control> controlArrayList) {
        this.controlArrayList = controlArrayList;
        simManager = new SimManager();
        simManager.setEndSimTime(100);
        simManager.setSimTimeRatio(2);
        SimManager.simMode = SimParameters.SimMode.ASTRONOMICAL;
        Environment environment = new Environment(windowServingTimeDelay,customerArrivalTimeDelay,breakdownTimeDelay,reparationTimeDelay,impatienceTimeDelay,
                queueMaxSize,maxLimitOfCustomers,numberOfWindows,numberOfPriorities,servingTime);
        bank = new Bank(environment, simManager);

        for(int i = 0; i<bank.getWindowsTab().length; i++){
            try {
                new WindowBreakdown(bank,i,bank.getEnvironment().breakdownTimeDelay +i);
            } catch (SimControlException e) {
                e.printStackTrace();
            }
        }
        try {
            new CustomerArrival(bank,null,0);
        } catch (SimControlException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        bank.appendTextToLogs("\t\t---START OF THE SIMULATION---");
        try {
            simManager.startSimulation();
        } catch (SimControlException e) {
            e.printStackTrace();
        }
        unlockAll();
        Controller.isSimulationActive = false;
        statistics();
    }

    public Bank getBank() {
        return bank;
    }

    SimManager getSimManager() {
        return simManager;
    }

    private void unlockAll()
    {
        for (Control i : controlArrayList)
        {
            i.setDisable(false);
        }
    }

    private void statistics()
    {
        bank.appendTextToLogs("\n\n\t\t ---SUMMARY---");
        bank.appendTextToLogs("\nLoss of customers due to queue overflow");
        bank.appendTextToLogs(String.valueOf(bank.getLossOfCustomers() + 1));
        bank.appendTextToLogs("\nLoss of customers due to impatience");
        bank.appendTextToLogs(String.valueOf(bank.getImpatientLoss()));
        bank.appendTextToLogs("\nExpected limit number of customers in the branch");
        bank.appendTextToLogs(String.valueOf(Statistics.arithmeticMean(bank.getNumberOfCustomersInBankBranch())));
        bank.appendTextToLogs("\nExpected limit number of customers in the queue");
        bank.appendTextToLogs(String.valueOf(Statistics.arithmeticMean(bank.getNumberOfCustomersInQueue())));
        bank.appendTextToLogs("\nExpected limit time of queuing customer waiting to start service");
        bank.appendTextToLogs(String.valueOf(Statistics.arithmeticMean(bank.getWaitingTimeInQueue())));
        bank.appendTextToLogs("\nExpected customer service time limit");
        bank.appendTextToLogs(String.valueOf(Statistics.arithmeticMean(bank.getServingTime())));
        bank.appendTextToLogs("\nLimit probability of resigning from customer service");
        bank.appendTextToLogs(String.valueOf((double) bank.getImpatientLoss()/bank.getMaxLimitOfCustomers()));
        bank.appendTextToLogs("\n\t\t ---END OF LOG---");
    }
}
