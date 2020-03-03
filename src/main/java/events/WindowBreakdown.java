package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;
import objects.Customer;

public class WindowBreakdown extends BasicSimEvent<Bank, Object> {

    private int idOkienka;

    public WindowBreakdown(Bank entity, int idOkienka, double delay) throws SimControlException {
        super(entity, delay);
        this.idOkienka = idOkienka;
    }

    @Override
    protected void stateChange() throws SimControlException {
        Bank bank = getSimObj();
        bank.appendTextToLogs(String.format("%.5f",simTime()) + " :###: Window nr " + idOkienka + " breakdown");
        if (bank.getLeavingTheWindowTab()[idOkienka] != null) {
            bank.getLeavingTheWindowTab()[idOkienka].onInterruption();
            if(!bank.getWindowsTab()[idOkienka].isAvaliable()) {
                Customer customer = bank.getLeavingTheWindowTab()[idOkienka].getCustomer();
                bank.getLeavingTheWindowTab()[idOkienka].terminate();
                customer.setPriority(0);
                customer.setInWindow(false);
                bank.getCustomerTechnicalQueue().addClient(customer);
                bank.appendTextToLogs(String.format("%.5f",simTime()) + " :###: Customer nr " + customer.getId() + " redirected to technical queue" );
                customer.setWaitingTimeStart(simTime());
            }
        }
        bank.getWindowsTab()[idOkienka].setAvaliable(false);
        bank.getWindowsTab()[idOkienka].setBroken(true);
        double dt = bank.getSimGenerator().exponential(bank.getEnvironment().reparationTimeDelay);
        new WindowReparation(bank, idOkienka, dt);
    }

    @Override
    protected void onTermination() {

    }

    @Override
    protected void onInterruption() {

    }
}