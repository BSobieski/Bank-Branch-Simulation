package obiekty;

import dissimlab.monitors.MonitoredVar;
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimManager;
import wydarzenia.OpuszczenieOkienka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Bank extends BasicSimObj {

    SimGenerator simGenerator = new SimGenerator();
    Okienko[] okienka;
    int strata;
    Otoczenie otoczenie;
    int max_klientow;
    int ilosc_klientow;
    int strataZniecierpliwienia = 0;
    Kolejka kolejka;
    Kolejka kolejkaTechniczna;
    OpuszczenieOkienka opuszczeniaOkienka[];
    SimManager simManager;
    int logState;
    PrintWriter printWriter;
    MonitoredVar iloscWKolejce;
    MonitoredVar iloscWOddziale;
    MonitoredVar czasOczekiwaniaWKolejce;
    MonitoredVar czasObslugi;



    public Bank(Otoczenie otoczenie, SimManager simManager) {
        this.simManager = simManager;
        logState = 0;
        this.otoczenie = otoczenie;
        opuszczeniaOkienka = new OpuszczenieOkienka[otoczenie.ilosc_okienek];
        this.max_klientow = otoczenie.max_klientow;
        ilosc_klientow = 0;
        okienka = new Okienko[otoczenie.ilosc_okienek];
        kolejka = new Kolejka(otoczenie.maxKolejka);
        kolejkaTechniczna = new Kolejka(otoczenie.maxKolejka);
        for (int i = 0; i < okienka.length; i++) {
            okienka[i] = new Okienko(i, kolejka, kolejkaTechniczna);
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

    public Okienko[] getOkienka()
    {
        return okienka;
    }

    public void setOkienka(Okienko[] okienka)
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

    public Otoczenie getOtoczenie()
    {
        return otoczenie;
    }

    public void setOtoczenie(Otoczenie otoczenie)
    {
        this.otoczenie = otoczenie;
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

    public Kolejka getKolejka()
    {
        return kolejka;
    }

    public void setKolejka(Kolejka kolejka)
    {
        this.kolejka = kolejka;
    }

    public OpuszczenieOkienka[] getOpuszczeniaOkienka()
    {
        return opuszczeniaOkienka;
    }

    public void setOpuszczeniaOkienka(OpuszczenieOkienka[] opuszczeniaOkienka)
    {
        this.opuszczeniaOkienka = opuszczeniaOkienka;
    }

    public Kolejka getKolejkaTechniczna() {
        return kolejkaTechniczna;
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
            //  System.out.println("plik istnieje");
        } else {
            try {
                boolean b = plik.createNewFile();
            } catch (IOException e) {
                //  System.out.println("Nie mo?na utworzy? pliku");
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
        for(Okienko i : okienka)
        {
            if (!i.isWolne())
                klienciWOkienkach++;
        }
        return klienciWOkienkach;
    }
}
