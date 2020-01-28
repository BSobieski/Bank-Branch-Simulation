package obiekty;

public class Okienko {

    boolean wolne;
    boolean awaria;
    Kolejka kolejka;
    Kolejka kolejkaTechniczna;
    int id;
    int strata;

    public Okienko(int id, Kolejka kolejka, Kolejka kolejkaTechniczna) {
        this.id = id;
        this.kolejka = kolejka;
        this.kolejkaTechniczna = kolejkaTechniczna;
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

    public Kolejka getKolejka()
    {
        return kolejka;
    }

    public void setKolejka(Kolejka kolejka)
    {
        this.kolejka = kolejka;
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

    public Kolejka getKolejkaTechniczna() {
        return kolejkaTechniczna;
    }

    public boolean isAwaria() {
        return awaria;
    }

    public void setAwaria(boolean awaria) {
        this.awaria = awaria;
    }
}
