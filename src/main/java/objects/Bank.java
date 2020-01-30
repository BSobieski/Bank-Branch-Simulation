package objects;

import dissimlab.monitors.MonitoredVar;
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimManager;
import events.LeavingTheWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Bank extends BasicSimObj
{

    private SimGenerator simGenerator = new SimGenerator();
    private Window[] windowsTab;
    private int lossOfCustomers;
    private Environment environment;
    private int maxLimitOfCustomers;
    private int currentNumberOfCustomers;
    private int impatientLoss = 0;
    private CustomerQueue customerQueue;
    private CustomerQueue customerTechnicalQueue;
    private LeavingTheWindow[] leavingTheWindowTab;
    private SimManager simManager;
    private PrintWriter printWriter;
    private MonitoredVar numberOfCustomersInQueue;
    private MonitoredVar numberOfCustomersInBankBranch;
    private MonitoredVar waitingTimeInQueue;
    private MonitoredVar servingTime;
    private static final String FILE_PATH = "src/main/resources/log.txt";


    public Bank(Environment environment, SimManager simManager)
    {
        this.simManager = simManager;
        this.environment = environment;
        leavingTheWindowTab = new LeavingTheWindow[environment.numberOfWindows];
        this.maxLimitOfCustomers = environment.maxLimitOfCustomers;
        currentNumberOfCustomers = 0;
        windowsTab = new Window[environment.numberOfWindows];
        customerQueue = new CustomerQueue(environment.queueMaxSize);
        customerTechnicalQueue = new CustomerQueue(environment.queueMaxSize);
        for (int i = 0; i < windowsTab.length; i++)
        {
            windowsTab[i] = new Window(i, customerQueue, customerTechnicalQueue);
        }
        numberOfCustomersInQueue = new MonitoredVar(0, simManager);
        numberOfCustomersInBankBranch = new MonitoredVar(0, simManager);
        waitingTimeInQueue = new MonitoredVar(0, simManager);
        servingTime = new MonitoredVar(0, simManager);
        createNewFile();
        createNewWritter();
    }


    public SimGenerator getSimGenerator()
    {
        return simGenerator;
    }

    public Window[] getWindowsTab()
    {
        return windowsTab;
    }

    public int getLossOfCustomers()
    {
        return lossOfCustomers;
    }

    public void setLossOfCustomers(int lossOfCustomers)
    {
        this.lossOfCustomers = lossOfCustomers;
    }

    public Environment getEnvironment()
    {
        return environment;
    }

    public int getMaxLimitOfCustomers()
    {
        return maxLimitOfCustomers;
    }

    public int getCurrentNumberOfCustomers()
    {
        return currentNumberOfCustomers;
    }

    public void setCurrentNumberOfCustomers(int currentNumberOfCustomers)
    {
        this.currentNumberOfCustomers = currentNumberOfCustomers;
    }

    public int getImpatientLoss()
    {
        return impatientLoss;
    }

    public void setImpatientLoss(int impatientLoss)
    {
        this.impatientLoss = impatientLoss;
    }

    public CustomerQueue getCustomerQueue()
    {
        return customerQueue;
    }

    public LeavingTheWindow[] getLeavingTheWindowTab()
    {
        return leavingTheWindowTab;
    }

    public CustomerQueue getCustomerTechnicalQueue()
    {
        return customerTechnicalQueue;
    }

    public void appendTextToLogs(String message)
    {
        printWriter.write(message + "\n");
        printWriter.flush();
        System.out.println(message);
    }

    public SimManager getSimManager()
    {
        return simManager;
    }

    private void createNewFile()
    {
        File file = new File(FILE_PATH);
        if (file.isFile())
        {
            System.out.println("File is already existing");
        } else
        {
            try
            {
                boolean b = file.createNewFile();
            } catch (IOException e)
            {
                System.out.println("Couldn't create file");
            }
        }
    }

    private void createNewWritter()
    {
        printWriter = null;
        try
        {
            printWriter = new PrintWriter(FILE_PATH);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    public MonitoredVar getNumberOfCustomersInQueue()
    {
        return numberOfCustomersInQueue;
    }

    public MonitoredVar getNumberOfCustomersInBankBranch()
    {
        return numberOfCustomersInBankBranch;
    }

    public MonitoredVar getWaitingTimeInQueue()
    {
        return waitingTimeInQueue;
    }

    public MonitoredVar getServingTime()
    {
        return servingTime;
    }

    public int getKlienciWOkienkach()
    {
        int customersInWindows = 0;
        for (Window i : windowsTab)
        {
            if (!i.isAvaliable())
                customersInWindows++;
        }
        return customersInWindows;
    }


}
