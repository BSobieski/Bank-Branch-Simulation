package wydarzenia;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import obiekty.Bank;

public class NaprawaOkienka extends BasicSimEvent<Bank, Object> {


    int okienko;

    public NaprawaOkienka(Bank entity, int okienko, double delay) throws SimControlException {
        super(entity, delay);
        this.okienko = okienko;
    }

    //todo okienko na okienko
    @Override
    protected void stateChange() throws SimControlException {
        Bank bank = getSimObj();
        bank.appendTextToLogs(String.format("%.5f",simTime()) + " :###: Okienko nr " + okienko + " naprawione");
        bank.getOkienka()[okienko].setWolne(true);
        bank.getOkienka()[okienko].setAwaria(false);

        if (bank.getOkienka()[okienko].getKolejka().getSize() > 0 && bank.getOkienka()[okienko].isWolne()) {
            new PodejscieDoOkienka(bank, bank.getOkienka()[okienko], 0);
        }

        if (bank.getMax_klientow() > bank.getIlosc_klientow()) {
            double delay = bank.getSimGenerator().exponential(bank.getOtoczenie().awaria);
            new AwariaOkienka(bank, okienko, delay);
        }
    }


    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

}
