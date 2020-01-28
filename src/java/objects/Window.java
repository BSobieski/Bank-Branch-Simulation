package objects;

public class Window
{

    boolean wolne;
    boolean awaria;
    CustomerQueue customerQueue;
    CustomerQueue customerQueueTechniczna;
    int id;
    int strata;

    public Window(int id, CustomerQueue customerQueue, CustomerQueue customerQueueTechniczna) {
        this.id = id;
        this.customerQueue = customerQueue;
        this.customerQueueTechniczna = customerQueueTechniczna;
        wolne = true;
        awaria = false;
    }

    public boolean isWolne()
    {
        return wolne;
    }

    public void setWolne(boolean wolne)
    {
        this.wolne = wolne;
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

    public int getStrata()
    {
        return strata;
    }

    public void setStrata(int strata)
    {
        this.strata = strata;
    }

    public CustomerQueue getCustomerQueueTechniczna() {
        return customerQueueTechniczna;
    }

    public boolean isAwaria() {
        return awaria;
    }

    public void setAwaria(boolean awaria) {
        this.awaria = awaria;
    }
}
