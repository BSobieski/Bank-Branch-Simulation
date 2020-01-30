package objects;

import java.util.concurrent.atomic.AtomicLong;

public class Customer implements Comparable<Customer>
{
    private static AtomicLong idCounter = new AtomicLong();
    private long id;
    private boolean ifCustomerCameOut = false;
    private int priority;
    private boolean isInWindow;
    private int numberOfWindow;
    private double waitingTimeStart;
    private double servingTimeStart;

    public Customer()
    {
        this.id = idCounter.getAndIncrement();
        isInWindow = false;
    }

    public long getId()
    {
        return id;
    }

    public boolean isIfCustomerCameOut()
    {
        return ifCustomerCameOut;
    }

    public void setIfCustomerCameOut(boolean ifCustomerCameOut)
    {
        this.ifCustomerCameOut = ifCustomerCameOut;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public boolean isInWindow()
    {
        return isInWindow;
    }

    public void setInWindow(boolean inWindow)
    {
        this.isInWindow = inWindow;
    }

    public int getNumberOfWindow()
    {
        return numberOfWindow;
    }

    public void setNumberOfWindow(int numberOfWindow)
    {
        this.numberOfWindow = numberOfWindow;
    }

    @Override
    public int compareTo(Customer o)
    {
        return this.getPriority().compareTo(o.getPriority());
    }

    public double getWaitingTimeStart()
    {
        return waitingTimeStart;
    }

    public void setWaitingTimeStart(double waitingTimeStart)
    {
        this.waitingTimeStart = waitingTimeStart;
    }

    public double getServingTimeStart()
    {
        return servingTimeStart;
    }

    public void setServingTimeStart(double servingTimeStart)
    {
        this.servingTimeStart = servingTimeStart;
    }


}
