package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;
import objects.Customer;
import objects.Window;

public class ApproachToTheWindow extends BasicSimEvent<Bank, Object>
{

    private Window window;

    public ApproachToTheWindow(Bank entity, Window window, double delay) throws SimControlException
    {
        super(entity, delay);
        this.window = window;
    }

    @Override
    protected void stateChange() throws SimControlException
    {
        window.setWolne(false);
        Bank bank = getSimObj();
        Customer customer = null;

        if (window.getCustomerQueueTechniczna().getSize() > 0)
        {
            customer = window.getCustomerQueueTechniczna().getAndRemovePriorityFirst();
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :###: Kolejka techniczna - Klient nr " + customer.getId() + " wszedł do okienka nr " + window.getId() + " aktualna wielkosc kolejki technicznej : " + bank.getCustomerQueueTechniczna().getSize());
            bank.getCzasOczekiwaniaWKolejce().setValue(simTime() - customer.getStartCzekania());
            customer.setStartObslugi(simTime());
        } else if (window.getCustomerQueue().getSize() > 0)
        {
            customer = window.getCustomerQueue().getAndRemovePriorityFirst();
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :: Klient nr " + customer.getId() + " wszedł do okienka nr " + window.getId() + " aktualna wielkosc kolejki :" + bank.getCustomerQueue().getSize());
            bank.getIloscWKolejce().setValue(bank.getCustomerQueue().getSize());
            bank.getCzasOczekiwaniaWKolejce().setValue(simTime() - customer.getStartCzekania());
            customer.setStartObslugi(simTime());
        } else
        {
            return;
        }
        customer.setNrOkienka(window.getId());
        customer.setInOkienko(true);
        double delay = bank.getSimGenerator().exponential(bank.getEnvironment().dlugowsObslugi);


        LeavingTheWindow leavingTheWindow = new LeavingTheWindow(bank, customer, window, delay);
        bank.getOpuszczeniaOkienka()[window.getId()] = leavingTheWindow;
    }

    @Override
    protected void onTermination() throws SimControlException
    {


    }

    @Override
    protected void onInterruption() throws SimControlException
    {

    }
}
