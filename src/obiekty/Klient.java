package obiekty;

import java.util.concurrent.atomic.AtomicLong;

public class Klient implements Comparable<Klient>{
    private static AtomicLong idCounter = new AtomicLong();
    long id;
    boolean wyszedl = false;
    int priorytet;
    boolean inOkienko;
    int nrOkienka;
    double startCzekania;
    double startObslugi;

    public Klient() {
        this.id = idCounter.getAndIncrement();
        inOkienko = false;
    }

    public static AtomicLong getIdCounter()
    {
        return idCounter;
    }

    public static void setIdCounter(AtomicLong idCounter)
    {
        Klient.idCounter = idCounter;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public boolean isWyszedl()
    {
        return wyszedl;
    }

    public void setWyszedl(boolean wyszedl)
    {
        this.wyszedl = wyszedl;
    }

    public Integer getPriorytet()
    {
        return priorytet;
    }

    public void setPriorytet(int priorytet)
    {
        this.priorytet = priorytet;
    }

    public boolean isInOkienko()
    {
        return inOkienko;
    }

    public void setInOkienko(boolean inOkienko)
    {
        this.inOkienko = inOkienko;
    }

    public int getNrOkienka()
    {
        return nrOkienka;
    }

    public void setNrOkienka(int nrOkienka)
    {
        this.nrOkienka = nrOkienka;
    }

    @Override
    public int compareTo(Klient o)
    {
        return this.getPriorytet().compareTo(o.getPriorytet());
    }

    public double getStartCzekania()
    {
        return startCzekania;
    }

    public void setStartCzekania(double startCzekania)
    {
        this.startCzekania = startCzekania;
    }

    public double getStartObslugi()
    {
        return startObslugi;
    }

    public void setStartObslugi(double startObslugi)
    {
        this.startObslugi = startObslugi;
    }
}
