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
        bank.getWindowsTab()[okienko].setAvaliable(true);
        bank.getWindowsTab()[okienko].setBroken(false);

        if (bank.getWindowsTab()[okienko].getCustomerQueue().getSize() > 0 && bank.getWindowsTab()[okienko].isAvaliable()) {
            new ApproachToTheWindow(bank, bank.getWindowsTab()[okienko], 0);
        }

        if (bank.getMaxLimitOfCustomers() > bank.getCurrentNumberOfCustomers()) {
            double delay = bank.getSimGenerator().exponential(bank.getEnvironment().breakdownTimeDelay);
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
