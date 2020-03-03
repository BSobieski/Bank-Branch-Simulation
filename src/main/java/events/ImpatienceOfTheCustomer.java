package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;
import objects.Customer;

public class ImpatienceOfTheCustomer extends BasicSimEvent<Bank, Customer> {
    private Customer customer;

    ImpatienceOfTheCustomer(Bank entity, double delay, Customer customer) throws SimControlException {
        super(entity, delay, customer);
        this.customer = customer;
    }

    @Override
    protected void stateChange() throws SimControlException {
        Bank bank = getSimObj();

        if (customer.isIfCustomerCameOut()) {
            return;
        }
        bank.setImpatientLoss(bank.getImpatientLoss() + 1);
        if (customer.isInWindow()) {
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :$$$: Customer nr " + customer.getId() + " got impatient during service in window nr " + customer.getNumberOfWindow() + ", current impatient lost: " + bank.getImpatientLoss());
            bank.getWindowsTab()[customer.getNumberOfWindow()].setAvaliable(true);
            customer.setIfCustomerCameOut(true);
            bank.getLeavingTheWindowTab()[customer.getNumberOfWindow()].terminate();
            bank.getNumberOfCustomersInBankBranch().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerTechnicalQueue().getSize());
        } else {
            bank.getCustomerQueue().remove(customer);
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :$$$: Customer nr " + customer.getId() + " got impatient during waiting in queue, current impatient lost: " + bank.getImpatientLoss());
            bank.getNumberOfCustomersInBankBranch().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerTechnicalQueue().getSize());
            bank.getNumberOfCustomersInQueue().setValue(bank.getCustomerQueue().getSize());
        }


    }

    @Override
    protected void onTermination() {

    }

    @Override
    protected void onInterruption() {

    }
}
