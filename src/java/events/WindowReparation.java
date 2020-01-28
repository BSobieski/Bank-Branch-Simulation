package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;

public class WindowReparation extends BasicSimEvent<Bank, Object> {


    int okienko;

    public WindowReparation(Bank entity, int okienko, double delay) throws SimControlException {
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

        if (bank.getOkienka()[okienko].getCustomerQueue().getSize() > 0 && bank.getOkienka()[okienko].isWolne()) {
            new ApproachToTheWindow(bank, bank.getOkienka()[okienko], 0);
        }

        if (bank.getMax_klientow() > bank.getIlosc_klientow()) {
            double delay = bank.getSimGenerator().exponential(bank.getEnvironment().awaria);
            new WindowBreakdown(bank, okienko, delay);
        }
    }


    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

}
