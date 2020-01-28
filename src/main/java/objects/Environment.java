package objects;

public class Environment
{

    public double windowServingTimeDelay;
    public double customerArrivalTimeDelay;
    public double breakdownTimeDelay;
    public double reparationTimeDelay;
    public double impatienceTimeDelay;
    public double servingTime;
    public int queueMaxSize;
    public int maxLimitOfCustomers;
    public int numberOfWindows;
    public int numberOfPriorities;


    public Environment(double windowServingTimeDelay, double customerArrivalTimeDelay, double breakdownTimeDelay, double reparationTimeDelay, double mpatienceTimeDelay, int queueMaxSize,
                       int maxLimitOfCustomers, int numberOfWindows, int numberOfPriorities, int servingTime)
    {
        this.windowServingTimeDelay = windowServingTimeDelay;
        this.customerArrivalTimeDelay = customerArrivalTimeDelay;
        this.breakdownTimeDelay = breakdownTimeDelay;
        this.reparationTimeDelay = reparationTimeDelay;
        this.impatienceTimeDelay = mpatienceTimeDelay;
        this.queueMaxSize = queueMaxSize;
        this.maxLimitOfCustomers = maxLimitOfCustomers;
        this.numberOfWindows = numberOfWindows;
        this.numberOfPriorities = numberOfPriorities;
        this.servingTime = servingTime;
    }

    public double getWindowServingTimeDelay()
    {
        return windowServingTimeDelay;
    }

    public void setWindowServingTimeDelay(double windowServingTimeDelay)
    {
        this.windowServingTimeDelay = windowServingTimeDelay;
    }

    public double getCustomerArrivalTimeDelay()
    {
        return customerArrivalTimeDelay;
    }

    public void setCustomerArrivalTimeDelay(double customerArrivalTimeDelay)
    {
        this.customerArrivalTimeDelay = customerArrivalTimeDelay;
    }

    public double getBreakdownTimeDelay()
    {
        return breakdownTimeDelay;
    }

    public void setBreakdownTimeDelay(double breakdownTimeDelay)
    {
        this.breakdownTimeDelay = breakdownTimeDelay;
    }

    public double getReparationTimeDelay()
    {
        return reparationTimeDelay;
    }

    public void setReparationTimeDelay(double reparationTimeDelay)
    {
        this.reparationTimeDelay = reparationTimeDelay;
    }

    public double getImpatienceTimeDelay()
    {
        return impatienceTimeDelay;
    }

    public void setImpatienceTimeDelay(double impatienceTimeDelay)
    {
        this.impatienceTimeDelay = impatienceTimeDelay;
    }

    public double getServingTime()
    {
        return servingTime;
    }

    public void setServingTime(double servingTime)
    {
        this.servingTime = servingTime;
    }

    public int getQueueMaxSize()
    {
        return queueMaxSize;
    }

    public void setQueueMaxSize(int queueMaxSize)
    {
        this.queueMaxSize = queueMaxSize;
    }

    public int getMaxLimitOfCustomers()
    {
        return maxLimitOfCustomers;
    }

    public void setMaxLimitOfCustomers(int maxLimitOfCustomers)
    {
        this.maxLimitOfCustomers = maxLimitOfCustomers;
    }

    public int getNumberOfWindows()
    {
        return numberOfWindows;
    }

    public void setNumberOfWindows(int numberOfWindows)
    {
        this.numberOfWindows = numberOfWindows;
    }

    public int getNumberOfPriorities()
    {
        return numberOfPriorities;
    }

    public void setNumberOfPriorities(int numberOfPriorities)
    {
        this.numberOfPriorities = numberOfPriorities;
    }
}
