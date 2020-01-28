package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;
import objects.Customer;
import objects.Window;

public class LeavingTheWindow extends BasicSimEvent<Bank, Object> {
    private Customer customer;
    private Window window;
    private Bank bank;

    public LeavingTheWindow(Bank entity, Customer customer, Window window, double delay) throws SimControlException {
        super(entity, delay);
        this.customer = customer;
        this.window = window;
    }

    @Override
    protected void stateChange() throws SimControlException {
        bank = getSimObj();
        bank.appendTextToLogs(String.format("%.5f",simTime()) + " :: Klient " + customer.getId() + " opuszcza okienko " + window.getId());
        bank.getCzasObslugi().setValue(simTime() - customer.getStartObslugi());

        customer.setInOkienko(false);

        if (Math.abs(bank.getSimGenerator().nextInt()) % 10 == 0) {
            bank.appendTextToLogs(String.format("%.5f",simTime()) + " :|||: Klient " + customer.getId() + " powraca do kolejki");
            new CustomerArrival(bank, customer, 0);
        }
        else
        {
            bank.getIloscWOddziale().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerQueueTechniczna().getSize());
            customer.setWyszedl(true);
        }
        window.setWolne(true);

        if (window.getCustomerQueue().getSize() > 0 || window.getCustomerQueueTechniczna().getSize() > 0) {
            new ApproachToTheWindow(bank, window, 0);
        }
    }

    @Override
    protected void onTermination() throws SimControlException {
        bank = getSimObj();


        if (window.getCustomerQueue().getSize() > 0 && window.isWolne()) {
            new ApproachToTheWindow(bank, window, 0);
        }

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

    public Customer getCustomer() {
        return customer;
    }
}
