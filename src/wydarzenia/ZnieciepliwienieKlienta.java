package wydarzenia;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import obiekty.Bank;
import obiekty.Klient;

public class ZnieciepliwienieKlienta extends BasicSimEvent<Bank, Klient> {
    Klient klient;

    public ZnieciepliwienieKlienta(Bank entity, double delay, Klient klient) throws SimControlException {
        super(entity, delay, klient);
        this.klient = klient;
    }

    @Override
    protected void stateChange() throws SimControlException {
        Bank bank = getSimObj();

        if (klient.isWyszedl()) {
            return;
        }
        bank.setStrataZniecierpliwienia(bank.getStrataZniecierpliwienia() + 1);
        if (klient.isInOkienko()) {
            bank.appendTextToLogs(String.format("%.5f",simTime()) + " :$$$: klient " + klient.getId() + " zniecierpliwil sie bedac w okienku " + klient.getNrOkienka() + ", obecna strata znicierpliwienia: " + bank.getStrataZniecierpliwienia());
            bank.getOkienka()[klient.getNrOkienka()].setWolne(true);
            klient.setWyszedl(true);
            bank.getOpuszczeniaOkienka()[klient.getNrOkienka()].terminate();
            bank.getIloscWOddziale().setValue(bank.getKlienciWOkienkach() + bank.getKolejka().getSize() + bank.getKolejkaTechniczna().getSize());
        } else {
            bank.getKolejka().remove(klient);
            bank.appendTextToLogs(String.format("%.5f",simTime()) +" :$$$: klient " + klient.getId() + " zniecierpliwil sie bedac w kolejce, obecna strata znicierpliwienia: " + bank.getStrataZniecierpliwienia());
            bank.getIloscWOddziale().setValue(bank.getKlienciWOkienkach() + bank.getKolejka().getSize() + bank.getKolejkaTechniczna().getSize());
            bank.getIloscWKolejce().setValue(bank.getKolejka().getSize());
        }


    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}
