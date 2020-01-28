package objects;

import java.util.Collections;
import java.util.LinkedList;

public class CustomerQueue
{

    private LinkedList<Customer> kolejka = new LinkedList<Customer>();

    private int MaxSize;

    public CustomerQueue(int maxSize) {
        this.MaxSize = maxSize;
    }

    public boolean addClient(Customer client) {
        if (kolejka.size() < MaxSize) {
            kolejka.add(client);
            return true;
        } else {
            return false;
        }
    }

    public Customer getAndRemovePriorityFirst()
    {
        Collections.sort(kolejka);
        Customer pasazer = kolejka.getFirst();
        kolejka.removeFirst();
        return pasazer;
    }

    public int getSize() {
        return kolejka.size();
    }

    public void remove(Customer customer) {
        kolejka.remove(customer);
    }

    public Customer at(int index) {
        return kolejka.get(index);
    }

}