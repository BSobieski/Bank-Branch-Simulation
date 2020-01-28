package wydarzenia;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import obiekty.Bank;
import obiekty.Klient;
import obiekty.Okienko;

public class PrzybycieKlienta extends BasicSimEvent<Bank, Object>
{
    Klient klient;

    public PrzybycieKlienta(Bank entity, Klient klient, double delay) throws SimControlException
    {
        super(entity, delay);
        this.klient = klient;
    }

    protected void stateChange() throws SimControlException
    {
        Bank bank = getSimObj();
        boolean staryKlient = false;
        if (klient == null)
        {
            klient = new Klient();
            new ZnieciepliwienieKlienta(bank, bank.getSimGenerator().exponential(bank.getOtoczenie().niecierpliwosc), klient);
            klient.setPriorytet(Math.abs(bank.getSimGenerator().nextInt()) % bank.getOtoczenie().ilosc_priorytetow + 1);
        } else
        {
            staryKlient = true;
        }

        bank.setIlosc_klientow(bank.getIlosc_klientow() + 1);
        if (bank.getIlosc_klientow() == bank.getMax_klientow())
        {
           // bank.appendTextToLogs(String.format("%.5f", simTime()) + ":: -----Konczenie przybywania klientow - osiagnieto limit-----");
        }


        boolean wszedl = bank.getKolejka().addClient(klient);


        if (!wszedl)
        {
            bank.setStrata(bank.getStrata() + 1);
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :$$$: Przybyl klient nr " + klient.getId()
                    + " brak miejsca w kolejce - strata naliczona. Aktualna strata: " + bank.getStrata());

            klient.setWyszedl(true);
        } else
        {
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :: Przybyl klient nr " + klient.getId() + " z priorytetem " + klient.getPriorytet()
                    + " - klient dodany do kolejki. Aktualna wielkosc kolejki: " + bank.getKolejka().getSize());
            bank.getIloscWKolejce().setValue(bank.getKolejka().getSize());
            bank.getIloscWOddziale().setValue(bank.getKlienciWOkienkach() + bank.getKolejka().getSize() + bank.getKolejkaTechniczna().getSize());
            klient.setStartCzekania(simTime());
        }


        for (Okienko okienko : bank.getOkienka())
        {
            if (okienko.getKolejka().getSize() == 1 && okienko.isWolne())
            {
                PodejscieDoOkienka podejscieDoOkienka = new PodejscieDoOkienka(bank, okienko, 0);
                podejscieDoOkienka.onTermination();
                break;
            }
        }


        if (!staryKlient)
        {
            if (bank.getMax_klientow() > bank.getIlosc_klientow())
            {
                double dt = bank.getSimGenerator().exponential(bank.getOtoczenie().przyjscieKlienta);
                new PrzybycieKlienta(bank, null, dt);
            }
        }
    }

    protected void onTermination() throws SimControlException
    {
    }

    protected void onInterruption() throws SimControlException
    {
    }
}
