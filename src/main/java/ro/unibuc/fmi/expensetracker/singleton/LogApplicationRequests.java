package ro.unibuc.fmi.expensetracker.singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class LogApplicationRequests {
    private PrintWriter writer;

    private static LogApplicationRequests logApplicationRequests = null;

    private LogApplicationRequests() {
        try {
            String logFile = "logDateRequests.txt";
            FileWriter fw = new FileWriter(logFile);
            writer = new PrintWriter(fw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized LogApplicationRequests getInstance(){
        if(logApplicationRequests == null)
            logApplicationRequests = new LogApplicationRequests();
        return logApplicationRequests;
    }

    public void logTripCreation() {
        System.out.println("Logging successful");
        writer.println("A request to create a Trip was initiated on date: " + Instant.now());
    }

    public void logUserCreation() {
        System.out.println("Logging successful");
        writer.println("A request to create a User was initiated on date: " + Instant.now());
    }

}