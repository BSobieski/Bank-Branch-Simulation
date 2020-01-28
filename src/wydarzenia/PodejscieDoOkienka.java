package wydarzenia;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import obiekty.Bank;
import obiekty.Klient;
import obiekty.Okienko;

public class PodejscieDoOkienka extends BasicSimEvent<Bank, Object>
{

    private Okienko okienko;

    public PodejscieDoOkienka(Bank entity, Okienko okienko, double delay) throws SimControlException
    {
        super(entity, delay);
        this.okienko = okienko;
    }

    @Override
    protected void stateChange() throws SimControlException
    {
        okienko.setWolne(false);
        Bank bank = getSimObj();
        Klient klient = null;

        if (okienko.getKolejkaTechniczna().getSize() > 0)
        {
            klient = okienko.getKolejkaTechniczna().getAndRemovePriorityFirst();
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :###: Kolejka techniczna - Klient nr " + klient.getId() + " wszedł do okienka nr " + okienko.getId() + " aktualna wielkosc kolejki technicznej : " + bank.getKolejkaTechniczna().getSize());
            bank.getCzasOczekiwaniaWKolejce().setValue(simTime() - klient.getStartCzekania());
            klient.setStartObslugi(simTime());
        } else if (okienko.getKolejka().getSize() > 0)
        {
            klient = okienko.getKolejka().getAndRemovePriorityFirst();
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :: Klient nr " + klient.getId() + " wszedł do okienka nr " + okienko.getId() + " aktualna wielkosc kolejki :" + bank.getKolejka().getSize());
            bank.getIloscWKolejce().setValue(bank.getKolejka().getSize());
            bank.getCzasOczekiwaniaWKolejce().setValue(simTime() - klient.getStartCzekania());
            klient.setStartObslugi(simTime());
        } else
        {
            return;
        }
        klient.setNrOkienka(okienko.getId());
        klient.setInOkienko(true);
        double delay = bank.getSimGenerator().exponential(bank.getOtoczenie().dlugowsObslugi);


        OpuszczenieOkienka opuszczenieOkienka = new OpuszczenieOkienka(bank, klient, okienko, delay);
        bank.getOpuszczeniaOkienka()[okienko.getId()] = opuszczenieOkienka;
    }

    @Override
    protected void onTermination() throws SimControlException
    {


    }

    @Override
    protected void onInterruption() throws SimControlException
    {

    }
}
