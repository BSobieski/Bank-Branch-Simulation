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

        if (customer.isWyszedl()) {
            return;
        }
        bank.setStrataZniecierpliwienia(bank.getStrataZniecierpliwienia() + 1);
        if (customer.isInOkienko()) {
            bank.appendTextToLogs(String.format("%.5f",simTime()) + " :$$$: klient " + customer.getId() + " zniecierpliwil sie bedac w okienku " + customer.getNrOkienka() + ", obecna strata znicierpliwienia: " + bank.getStrataZniecierpliwienia());
            bank.getOkienka()[customer.getNrOkienka()].setWolne(true);
            customer.setWyszedl(true);
            bank.getOpuszczeniaOkienka()[customer.getNrOkienka()].terminate();
            bank.getIloscWOddziale().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerQueueTechniczna().getSize());
        } else {
            bank.getCustomerQueue().remove(customer);
            bank.appendTextToLogs(String.format("%.5f",simTime()) +" :$$$: klient " + customer.getId() + " zniecierpliwil sie bedac w kolejce, obecna strata znicierpliwienia: " + bank.getStrataZniecierpliwienia());
            bank.getIloscWOddziale().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerQueueTechniczna().getSize());
            bank.getIloscWKolejce().setValue(bank.getCustomerQueue().getSize());
        }


    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }
}
