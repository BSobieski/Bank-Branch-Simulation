package obiekty;

import java.util.Collections;
import java.util.LinkedList;

public class Kolejka {

    private LinkedList<Klient> kolejka = new LinkedList<Klient>();

    private int MaxSize;

    public Kolejka(int maxSize) {
        this.MaxSize = maxSize;
    }

    public boolean addClient(Klient client) {
        if (kolejka.size() < MaxSize) {
            kolejka.add(client);
            return true;
        } else {
            return false;
        }
    }

    public Klient getAndRemovePriorityFirst()
    {
        Collections.sort(kolejka);
        Klient pasazer = kolejka.getFirst();
        kolejka.removeFirst();
        return pasazer;
    }

    public int getSize() {
        return kolejka.size();
    }

    public void remove(Klient klient) {
        kolejka.remove(klient);
    }

    public Klient at(int index) {
        return kolejka.get(index);
    }

}