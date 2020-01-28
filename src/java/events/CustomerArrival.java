package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;
import objects.Customer;
import objects.Window;

public class CustomerArrival extends BasicSimEvent<Bank, Object>
{
    Customer customer;

    public CustomerArrival(Bank entity, Customer customer, double delay) throws SimControlException
    {
        super(entity, delay);
        this.customer = customer;
    }

    protected void stateChange() throws SimControlException
    {
        Bank bank = getSimObj();
        boolean staryKlient = false;
        if (customer == null)
        {
            customer = new Customer();
            new ImpatienceOfTheCustomer(bank, bank.getSimGenerator().exponential(bank.getEnvironment().niecierpliwosc), customer);
            customer.setPriorytet(Math.abs(bank.getSimGenerator().nextInt()) % bank.getEnvironment().ilosc_priorytetow + 1);
        } else
        {
            staryKlient = true;
        }

        bank.setIlosc_klientow(bank.getIlosc_klientow() + 1);
        if (bank.getIlosc_klientow() == bank.getMax_klientow())
        {
           // bank.appendTextToLogs(String.format("%.5f", simTime()) + ":: -----Konczenie przybywania klientow - osiagnieto limit-----");
        }


        boolean wszedl = bank.getCustomerQueue().addClient(customer);


        if (!wszedl)
        {
            bank.setStrata(bank.getStrata() + 1);
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :$$$: Przybyl klient nr " + customer.getId()
                    + " brak miejsca w kolejce - strata naliczona. Aktualna strata: " + bank.getStrata());

            customer.setWyszedl(true);
        } else
        {
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :: Przybyl klient nr " + customer.getId() + " z priorytetem " + customer.getPriorytet()
                    + " - klient dodany do kolejki. Aktualna wielkosc kolejki: " + bank.getCustomerQueue().getSize());
            bank.getIloscWKolejce().setValue(bank.getCustomerQueue().getSize());
            bank.getIloscWOddziale().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerQueueTechniczna().getSize());
            customer.setStartCzekania(simTime());
        }


        for (Window window : bank.getOkienka())
        {
            if (window.getCustomerQueue().getSize() == 1 && window.isWolne())
            {
                ApproachToTheWindow approachToTheWindow = new ApproachToTheWindow(bank, window, 0);
                approachToTheWindow.onTermination();
                break;
            }
        }


        if (!staryKlient)
        {
            if (bank.getMax_klientow() > bank.getIlosc_klientow())
            {
                double dt = bank.getSimGenerator().exponential(bank.getEnvironment().przyjscieKlienta);
                new CustomerArrival(bank, null, dt);
            }
        }
    }

    protected void onTermination() throws SimControlException
    {
    }

    protected void onInterruption() throws SimControlException
    {
    }
}
