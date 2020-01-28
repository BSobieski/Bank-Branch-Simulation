package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;
import objects.Customer;

public class ImpatienceOfTheCustomer extends BasicSimEvent<Bank, Customer> {
    Customer customer;

    public ImpatienceOfTheCustomer(Bank entity, double delay, Customer customer) throws SimControlException {
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
            bank.appendTextToLogs(String.format("%.5f",simTime()) + " :$$$: klient " + customer.getId() + " zniecierpliwil sie bedac w okienku " + customer.getNumberOfWindow() + ", obecna strata znicierpliwienia: " + bank.getImpatientLoss());
            bank.getWindowsTab()[customer.getNumberOfWindow()].setAvaliable(true);
            customer.setIfCustomerCameOut(true);
            bank.getLeavingTheWindowTab()[customer.getNumberOfWindow()].terminate();
            bank.getNumberOfCustomersInBankBranch().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerTechnicalQueue().getSize());
        } else {
            bank.getCustomerQueue().remove(customer);
            bank.appendTextToLogs(String.format("%.5f",simTime()) +" :$$$: klient " + customer.getId() + " zniecierpliwil sie bedac w kolejce, obecna strata znicierpliwienia: " + bank.getImpatientLoss());
            bank.getNumberOfCustomersInBankBranch().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerTechnicalQueue().getSize());
            bank.getNumberOfCustomersInQueue().setValue(bank.getCustomerQueue().getSize());
        }


    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}
