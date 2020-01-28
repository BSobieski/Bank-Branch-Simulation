package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;
import objects.Customer;

public class WindowBreakdown extends BasicSimEvent<Bank, Object> {

    int idOkienka;

    public WindowBreakdown(Bank entity, int idOkienka, double delay) throws SimControlException {
        super(entity, delay);
        this.idOkienka = idOkienka;
    }

    @Override
    protected void stateChange() throws SimControlException {
        Bank bank = getSimObj();
        bank.appendTextToLogs(String.format("%.5f",simTime()) + " :###: Awaria okienka nr " + idOkienka);
        if (bank.getLeavingTheWindowTab()[idOkienka] != null) {
            bank.getLeavingTheWindowTab()[idOkienka].onInterruption();
            if(!bank.getWindowsTab()[idOkienka].isAvaliable()) {
                Customer customer = bank.getLeavingTheWindowTab()[idOkienka].getCustomer();
                bank.getLeavingTheWindowTab()[idOkienka].terminate();
                customer.setPriority(0);
                customer.setInWindow(false);
                bank.getCustomerTechnicalQueue().addClient(customer);
                bank.appendTextToLogs(String.format("%.5f",simTime()) + " :###: klient " + customer.getId() + " przekierowany do kolejki technicznej" );
                customer.setWaitingTimeStart(simTime());
            }
        }
        bank.getWindowsTab()[idOkienka].setAvaliable(false);
        bank.getWindowsTab()[idOkienka].setBroken(true);
        double dt = bank.getSimGenerator().exponential(bank.getEnvironment().reparationTimeDelay);
        new WindowReparation(bank, idOkienka, dt);
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}