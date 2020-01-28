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
    Bank bank;
    SimManager simManager;
    ArrayList<Control> controlArrayList;

    public Simulation(double obsługaOkienka, double przyjscieKlienta, double awaria, double naprawa, double niecierpliwosc, int maxKolejka,
                      int max_klientow, int ilosc_okienek, int ilosc_priorytetow, int dlugowsObslugi, ArrayList<Control> controlArrayList) {
        this.controlArrayList = controlArrayList;
        simManager = new SimManager();
        simManager.setEndSimTime(100);
        simManager.setSimTimeRatio(2);
        SimManager.simMode = SimParameters.SimMode.ASTRONOMICAL;
        Environment environment = new Environment(obsługaOkienka,przyjscieKlienta,awaria,naprawa,niecierpliwosc,
                maxKolejka,max_klientow,ilosc_okienek,ilosc_priorytetow,dlugowsObslugi);
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
        bank.appendTextToLogs("\t\t---START SYMULACJI---");
        try {
            simManager.startSimulation();
        } catch (SimControlException e) {
            e.printStackTrace();
        }
        unlockAll();
        Controller.symulacjaAktywna = false;
        statistics();
    }

    public Bank getBank() {
        return bank;
    }

    public SimManager getSimManager() {
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
        bank.appendTextToLogs("\n\n\t\t ---PODSUMOWANIE---");
        bank.appendTextToLogs("\nStrata klientow wynikajaca z przepelnienia kolejki");
        bank.appendTextToLogs(String.valueOf(bank.getLossOfCustomers() + 1));
        bank.appendTextToLogs("\nStrata klientow wynikajaca ze zniecierpliwienia");
        bank.appendTextToLogs(String.valueOf(bank.getImpatientLoss()));
        bank.appendTextToLogs("\nOczekiwana graniczna liczbę klientów w oddziale");
        bank.appendTextToLogs(String.valueOf(Statistics.arithmeticMean(bank.getNumberOfCustomersInBankBranch())));
        bank.appendTextToLogs("\nOczekiwana graniczna liczbę klientów w kolejce");
        bank.appendTextToLogs(String.valueOf(Statistics.arithmeticMean(bank.getNumberOfCustomersInQueue())));
        bank.appendTextToLogs("\nOczekiwany graniczny czas oczekiwania klienta w kolejce na rozpoczęcie obsługi");
        bank.appendTextToLogs(String.valueOf(Statistics.arithmeticMean(bank.getWaitingTimeInQueue())));
        bank.appendTextToLogs("\nOczekiwany graniczny czas obsługi klienta");
        bank.appendTextToLogs(String.valueOf(Statistics.arithmeticMean(bank.getServingTime())));
        bank.appendTextToLogs("\nGraniczne prawdopodobieństwo rezygnacji z obsługi przez klienta");
        bank.appendTextToLogs(String.valueOf((double) bank.getImpatientLoss()/bank.getMaxLimitOfCustomers()));
    }
}
