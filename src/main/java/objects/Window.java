package objects;

public class Window
{

    boolean isAvaliable;
    boolean isBroken;
    CustomerQueue customerQueue;
    CustomerQueue customerTechnicalQueue;
    int id;

    public Window(int id, CustomerQueue customerQueue, CustomerQueue customerTechnicalQueue) {
        this.id = id;
        this.customerQueue = customerQueue;
        this.customerTechnicalQueue = customerTechnicalQueue;
        isAvaliable = true;
        isBroken = false;
    }

    public boolean isAvaliable()
    {
        return isAvaliable;
    }

    public void setAvaliable(boolean avaliable)
    {
        this.isAvaliable = avaliable;
    }

    public CustomerQueue getCustomerQueue()
    {
        return customerQueue;
    }

    public void setCustomerQueue(CustomerQueue customerQueue)
    {
        this.customerQueue = customerQueue;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public CustomerQueue getCustomerTechnicalQueue() {
        return customerTechnicalQueue;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        this.isBroken = broken;
    }
}
