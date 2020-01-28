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
            new ImpatienceOfTheCustomer(bank, bank.getSimGenerator().exponential(bank.getEnvironment().impatienceTimeDelay), customer);
            customer.setPriority(Math.abs(bank.getSimGenerator().nextInt()) % bank.getEnvironment().numberOfPriorities + 1);
        } else
        {
            staryKlient = true;
        }

        bank.setCurrentNumberOfCustomers(bank.getCurrentNumberOfCustomers() + 1);
        if (bank.getCurrentNumberOfCustomers() == bank.getMaxLimitOfCustomers())
        {
           // bank.appendTextToLogs(String.format("%.5f", simTime()) + ":: -----Konczenie przybywania klientow - osiagnieto limit-----");
        }


        boolean wszedl = bank.getCustomerQueue().addClient(customer);


        if (!wszedl)
        {
            bank.setLossOfCustomers(bank.getLossOfCustomers() + 1);
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :$$$: Przybyl klient nr " + customer.getId()
                    + " brak miejsca w kolejce - strata naliczona. Aktualna strata: " + bank.getLossOfCustomers());

            customer.setIfCustomerCameOut(true);
        } else
        {
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :: Przybyl klient nr " + customer.getId() + " z priorytetem " + customer.getPriority()
                    + " - klient dodany do kolejki. Aktualna wielkosc kolejki: " + bank.getCustomerQueue().getSize());
            bank.getNumberOfCustomersInQueue().setValue(bank.getCustomerQueue().getSize());
            bank.getNumberOfCustomersInBankBranch().setValue(bank.getKlienciWOkienkach() + bank.getCustomerQueue().getSize() + bank.getCustomerTechnicalQueue().getSize());
            customer.setWaitingTimeStart(simTime());
        }


        for (Window window : bank.getWindowsTab())
        {
            if (window.getCustomerQueue().getSize() == 1 && window.isAvaliable())
            {
                ApproachToTheWindow approachToTheWindow = new ApproachToTheWindow(bank, window, 0);
                approachToTheWindow.onTermination();
                break;
            }
        }


        if (!staryKlient)
        {
            if (bank.getMaxLimitOfCustomers() > bank.getCurrentNumberOfCustomers())
            {
                double dt = bank.getSimGenerator().exponential(bank.getEnvironment().customerArrivalTimeDelay);
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
