package objects;

import dissimlab.monitors.MonitoredVar;
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimManager;
import events.LeavingTheWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Bank extends BasicSimObj {

    SimGenerator simGenerator = new SimGenerator();
    Window[] okienka;
    int strata;
    Environment environment;
    int max_klientow;
    int ilosc_klientow;
    int strataZniecierpliwienia = 0;
    CustomerQueue customerQueue;
    CustomerQueue customerQueueTechniczna;
    LeavingTheWindow opuszczeniaOkienka[];
    SimManager simManager;
    int logState;
    PrintWriter printWriter;
    MonitoredVar iloscWKolejce;
    MonitoredVar iloscWOddziale;
    MonitoredVar czasOczekiwaniaWKolejce;
    MonitoredVar czasObslugi;



    public Bank(Environment environment, SimManager simManager) {
        this.simManager = simManager;
        logState = 0;
        this.environment = environment;
        opuszczeniaOkienka = new LeavingTheWindow[environment.ilosc_okienek];
        this.max_klientow = environment.max_klientow;
        ilosc_klientow = 0;
        okienka = new Window[environment.ilosc_okienek];
        customerQueue = new CustomerQueue(environment.maxKolejka);
        customerQueueTechniczna = new CustomerQueue(environment.maxKolejka);
        for (int i = 0; i < okienka.length; i++) {
            okienka[i] = new Window(i, customerQueue, customerQueueTechniczna);
        }
        iloscWKolejce = new MonitoredVar(0,simManager);
        iloscWOddziale = new MonitoredVar(0,simManager);
        czasOczekiwaniaWKolejce = new MonitoredVar(0,simManager);
        czasObslugi = new MonitoredVar(0,simManager);
        createNewFile();
        createNewWritter();
    }


    public SimGenerator getSimGenerator()
    {
        return simGenerator;
    }

    public void setSimGenerator(SimGenerator simGenerator)
    {
        this.simGenerator = simGenerator;
    }

    public Window[] getOkienka()
    {
        return okienka;
    }

    public void setOkienka(Window[] okienka)
    {
        this.okienka = okienka;
    }

    public int getStrata()
    {
        return strata;
    }

    public void setStrata(int strata)
    {
        this.strata = strata;
    }

    public Environment getEnvironment()
    {
        return environment;
    }

    public void setEnvironment(Environment environment)
    {
        this.environment = environment;
    }

    public int getMax_klientow()
    {
        return max_klientow;
    }

    public void setMax_klientow(int max_klientow)
    {
        this.max_klientow = max_klientow;
    }

    public int getIlosc_klientow()
    {
        return ilosc_klientow;
    }

    public void setIlosc_klientow(int ilosc_klientow)
    {
        this.ilosc_klientow = ilosc_klientow;
    }

    public int getStrataZniecierpliwienia()
    {
        return strataZniecierpliwienia;
    }

    public void setStrataZniecierpliwienia(int strataZniecierpliwienia)
    {
        this.strataZniecierpliwienia = strataZniecierpliwienia;
    }

    public CustomerQueue getCustomerQueue()
    {
        return customerQueue;
    }

    public void setCustomerQueue(CustomerQueue customerQueue)
    {
        this.customerQueue = customerQueue;
    }

    public LeavingTheWindow[] getOpuszczeniaOkienka()
    {
        return opuszczeniaOkienka;
    }

    public void setOpuszczeniaOkienka(LeavingTheWindow[] opuszczeniaOkienka)
    {
        this.opuszczeniaOkienka = opuszczeniaOkienka;
    }

    public CustomerQueue getCustomerQueueTechniczna() {
        return customerQueueTechniczna;
    }

    public void appendTextToLogs(String message) {
        printWriter.write(message + "\n");
        printWriter.flush();
        System.out.println(message);
    }

    public SimManager getSimManager() {
        return simManager;
    }

    private void createNewFile()
    {
        File plik = new File("src/main/resources/log.txt");
        if (plik.isFile()) {
            System.out.println("File is already existing");
        } else {
            try {
                boolean b = plik.createNewFile();
            } catch (IOException e) {
                System.out.println("Couldn't create file");
            }
        }
    }

    private void createNewWritter()
    {
        printWriter = null;
        try {
            printWriter = new PrintWriter("src/resources/log.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public MonitoredVar getIloscWKolejce()
    {
        return iloscWKolejce;
    }

    public MonitoredVar getIloscWOddziale()
    {
        return iloscWOddziale;
    }

    public MonitoredVar getCzasOczekiwaniaWKolejce()
    {
        return czasOczekiwaniaWKolejce;
    }

    public MonitoredVar getCzasObslugi()
    {
        return czasObslugi;
    }

    public int getKlienciWOkienkach()
    {
        int klienciWOkienkach = 0;
        for(Window i : okienka)
        {
            if (!i.isWolne())
                klienciWOkienkach++;
        }
        return klienciWOkienkach;
    }
}
