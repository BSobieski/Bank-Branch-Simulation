package wydarzenia;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import obiekty.Bank;
import obiekty.Klient;
import obiekty.Okienko;

public class OpuszczenieOkienka extends BasicSimEvent<Bank, Object> {
    private Klient klient;
    private Okienko okienko;
    private Bank bank;

    public OpuszczenieOkienka(Bank entity, Klient klient, Okienko okienko, double delay) throws SimControlException {
        super(entity, delay);
        this.klient = klient;
        this.okienko = okienko;
    }

    @Override
    protected void stateChange() throws SimControlException {
        bank = getSimObj();
        bank.appendTextToLogs(String.format("%.5f",simTime()) + " :: Klient " + klient.getId() + " opuszcza okienko " + okienko.getId());
        bank.getCzasObslugi().setValue(simTime() - klient.getStartObslugi());

        klient.setInOkienko(false);

        if (Math.abs(bank.getSimGenerator().nextInt()) % 10 == 0) {
            bank.appendTextToLogs(String.format("%.5f",simTime()) + " :|||: Klient " + klient.getId() + " powraca do kolejki");
            new PrzybycieKlienta(bank, klient, 0);
        }
        else
        {
            bank.getIloscWOddziale().setValue(bank.getKlienciWOkienkach() + bank.getKolejka().getSize() + bank.getKolejkaTechniczna().getSize());
            klient.setWyszedl(true);
        }
        okienko.setWolne(true);

        if (okienko.getKolejka().getSize() > 0 || okienko.getKolejkaTechniczna().getSize() > 0) {
            new PodejscieDoOkienka(bank, okienko, 0);
        }
    }

    @Override
    protected void onTermination() throws SimControlException {
        bank = getSimObj();


        if (okienko.getKolejka().getSize() > 0 && okienko.isWolne()) {
            new PodejscieDoOkienka(bank, okienko, 0);
        }

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

    public Klient getKlient() {
        return klient;
    }
}
