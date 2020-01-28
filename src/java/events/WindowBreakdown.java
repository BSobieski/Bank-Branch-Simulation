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
        if (bank.getOpuszczeniaOkienka()[idOkienka] != null) {
            bank.getOpuszczeniaOkienka()[idOkienka].onInterruption();
            if(!bank.getOkienka()[idOkienka].isWolne()) {
                Customer customer = bank.getOpuszczeniaOkienka()[idOkienka].getCustomer();
                bank.getOpuszczeniaOkienka()[idOkienka].terminate();
                customer.setPriorytet(0);
                customer.setInOkienko(false);
                bank.getCustomerQueueTechniczna().addClient(customer);
                bank.appendTextToLogs(String.format("%.5f",simTime()) + " :###: klient " + customer.getId() + " przekierowany do kolejki technicznej" );
                customer.setStartCzekania(simTime());
            }
        }
        bank.getOkienka()[idOkienka].setWolne(false);
        bank.getOkienka()[idOkienka].setAwaria(true);
        double dt = bank.getSimGenerator().exponential(bank.getEnvironment().naprawa);
        new WindowReparation(bank, idOkienka, dt);
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}