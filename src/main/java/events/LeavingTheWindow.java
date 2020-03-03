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

    LeavingTheWindow(Bank entity, Customer customer, Window window, double delay) throws SimControlException {
        super(entity, delay);
        this.customer = customer;
        this.window = window;
    }

    @Override
    protected void stateChange() throws SimControlException {
        bank = getSimObj();
        bank.appendTextToLogs(String.format("%.5f",simTime()) + " :: Customer nr " + customer.getId() + " is leaving window nr " + window.getId());
        bank.getServingTime().setValue(simTime() - customer.getServingTimeStart());

        customer.setInWindow(false);

        if (Math.abs(bank.getSimGenerator().nextInt()) % 10 == 0) {
            bank.appendTextToLogs(String.format("%.5f",simTime()) + " :|||: Customer nr " + customer.getId() + " returns to queue");
            new CustomerArrival(bank, customer, 0);
        }
        else
        {
            bank.getNumberOfCustomersInBankBranch().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerTechnicalQueue().getSize());
            customer.setIfCustomerCameOut(true);
        }
        window.setAvaliable(true);

        if (window.getCustomerQueue().getSize() > 0 || window.getCustomerTechnicalQueue().getSize() > 0) {
            new ApproachToTheWindow(bank, window, 0);
        }
    }

    @Override
    protected void onTermination() throws SimControlException {
        bank = getSimObj();


        if (window.getCustomerQueue().getSize() > 0 && window.isAvaliable()) {
            new ApproachToTheWindow(bank, window, 0);
        }

    }

    @Override
    protected void onInterruption() {

    }

    Customer getCustomer() {
        return customer;
    }
}
