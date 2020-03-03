package objects;

public class Environment
{

    private double windowServingTimeDelay;
    public double customerArrivalTimeDelay;
    public double breakdownTimeDelay;
    public double reparationTimeDelay;
    public double impatienceTimeDelay;
    public double servingTime;
    int queueMaxSize;
    int maxLimitOfCustomers;
    int numberOfWindows;
    public int numberOfPriorities;


    public Environment(double windowServingTimeDelay, double customerArrivalTimeDelay, double breakdownTimeDelay, double reparationTimeDelay, double impatienceTimeDelay, int queueMaxSize,
                       int maxLimitOfCustomers, int numberOfWindows, int numberOfPriorities, int servingTime)
    {
        this.windowServingTimeDelay = windowServingTimeDelay;
        this.customerArrivalTimeDelay = customerArrivalTimeDelay;
        this.breakdownTimeDelay = breakdownTimeDelay;
        this.reparationTimeDelay = reparationTimeDelay;
        this.impatienceTimeDelay = impatienceTimeDelay;
        this.queueMaxSize = queueMaxSize;
        this.maxLimitOfCustomers = maxLimitOfCustomers;
        this.numberOfWindows = numberOfWindows;
        this.numberOfPriorities = numberOfPriorities;
        this.servingTime = servingTime;
    }
}
