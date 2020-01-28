package wydarzenia;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import obiekty.Bank;
import obiekty.Klient;

public class AwariaOkienka extends BasicSimEvent<Bank, Object> {

    int idOkienka;

    public AwariaOkienka(Bank entity, int idOkienka, double delay) throws SimControlException {
        super(entity, delay);
        this.idOkienka = idOkienka;
    }

    @Override
    protected void stateChange() throws SimControlException {
        Bank bank = getSimObj();
        bank.appendTextToLogs(String.format("%.5f",simTime()) + " :###: Awaria okienka nr " + idOkienka);
        if (bank.getOpuszczeniaOkienka()[idOkienka] != null) {
            bank.getOpuszczeniaOkienka()[idOkienka].onInterruption();
            if(!bank.getOkienka()[idOkienka].isWolne()) {
                Klient klient = bank.getOpuszczeniaOkienka()[idOkienka].getKlient();
                bank.getOpuszczeniaOkienka()[idOkienka].terminate();
                klient.setPriorytet(0);
                klient.setInOkienko(false);
                bank.getKolejkaTechniczna().addClient(klient);
                bank.appendTextToLogs(String.format("%.5f",simTime()) + " :###: klient " + klient.getId() + " przekierowany do kolejki technicznej" );
                klient.setStartCzekania(simTime());
            }
        }
        bank.getOkienka()[idOkienka].setWolne(false);
        bank.getOkienka()[idOkienka].setAwaria(true);
        double dt = bank.getSimGenerator().exponential(bank.getOtoczenie().naprawa);
        new NaprawaOkienka(bank, idOkienka, dt);
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}