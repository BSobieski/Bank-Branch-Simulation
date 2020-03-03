package objects;

import java.util.Collections;
import java.util.LinkedList;

public class CustomerQueue
{

    private LinkedList<Customer> customerLinkedList = new LinkedList<>();

    private int queueMaxSize;

    CustomerQueue(int queueMaxSize)
    {
        this.queueMaxSize = queueMaxSize;
    }

    public boolean addClient(Customer client)
    {
        if (customerLinkedList.size() < queueMaxSize)
        {
            customerLinkedList.add(client);
            return true;
        } else
        {
            return false;
        }
    }

    public Customer getAndRemovePriorityFirst()
    {
        Collections.sort(customerLinkedList);
        Customer customer = customerLinkedList.getFirst();
        customerLinkedList.removeFirst();
        return customer;
    }

    public int getSize()
    {
        return customerLinkedList.size();
    }

    public void remove(Customer customer)
    {
        customerLinkedList.remove(customer);
    }

    public Customer at(int index)
    {
        return customerLinkedList.get(index);
    }
}