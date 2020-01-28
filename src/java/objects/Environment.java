package objects;

public class Environment
{

    public double obslugaOkienka;
    public double przyjscieKlienta;
    public double awaria;
    public double naprawa;
    public double niecierpliwosc;
    public double dlugowsObslugi;
    public int maxKolejka;
    public int max_klientow;
    public int ilosc_okienek;
    public int ilosc_priorytetow;


    public Environment(double obslugaOkienka, double przyjscieKlienta, double awaria, double naprawa, double niecierpliwosc, int maxKolejka,
                       int max_klientow, int ilosc_okienek, int ilosc_priorytetow, int dlugowsObslugi)
    {
        this.obslugaOkienka = obslugaOkienka;
        this.przyjscieKlienta = przyjscieKlienta;
        this.awaria = awaria;
        this.naprawa = naprawa;
        this.niecierpliwosc = niecierpliwosc;
        this.maxKolejka = maxKolejka;
        this.max_klientow = max_klientow;
        this.ilosc_okienek = ilosc_okienek;
        this.ilosc_priorytetow = ilosc_priorytetow;
        this.dlugowsObslugi = dlugowsObslugi;
    }

}
