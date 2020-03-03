package events;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import objects.Bank;
import objects.Customer;
import objects.Window;

public class ApproachToTheWindow extends BasicSimEvent<Bank, Object>
{

    private Window window;

    ApproachToTheWindow(Bank entity, Window window, double delay) throws SimControlException
    {
        super(entity, delay);
        this.window = window;
    }

    @Override
    protected void stateChange() throws SimControlException
    {
        window.setAvaliable(false);
        Bank bank = getSimObj();
        Customer customer;

        if (window.getCustomerTechnicalQueue().getSize() > 0)
        {
            customer = window.getCustomerTechnicalQueue().getAndRemovePriorityFirst();
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :###: Technical Queue - Customer nr " + customer.getId() + " came to the window nr " + window.getId() + " actual size of technical queue : " + bank.getCustomerTechnicalQueue().getSize());
            bank.getWaitingTimeInQueue().setValue(simTime() - customer.getWaitingTimeStart());
            customer.setServingTimeStart(simTime());
        } else if (window.getCustomerQueue().getSize() > 0)
        {
            customer = window.getCustomerQueue().getAndRemovePriorityFirst();
            bank.appendTextToLogs(String.format("%.5f", simTime()) + " :: Customer nr " + customer.getId() + " came to the window nr " + window.getId() + " actual size of queue :" + bank.getCustomerQueue().getSize());
            bank.getNumberOfCustomersInQueue().setValue(bank.getCustomerQueue().getSize());
            bank.getWaitingTimeInQueue().setValue(simTime() - customer.getWaitingTimeStart());
            customer.setServingTimeStart(simTime());
        } else
        {
            return;
        }
        customer.setNumberOfWindow(window.getId());
        customer.setInWindow(true);
        double delay = bank.getSimGenerator().exponential(bank.getEnvironment().servingTime);


        LeavingTheWindow leavingTheWindow = new LeavingTheWindow(bank, customer, window, delay);
        bank.getLeavingTheWindowTab()[window.getId()] = leavingTheWindow;
    }

    @Override
    protected void onTermination() {


    }

    @Override
    protected void onInterruption() {

    }
}
