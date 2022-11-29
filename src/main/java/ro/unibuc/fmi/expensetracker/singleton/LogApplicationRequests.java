package ro.unibuc.fmi.expensetracker.singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class LogApplicationRequests {
    private PrintWriter printWriter;

    private static LogApplicationRequests logApplicationRequests = null;

    private LogApplicationRequests() {
        try {
            String fileName = "logDateRequests.txt";
            FileWriter fileWriter = new FileWriter(fileName);
            printWriter = new PrintWriter(fileWriter, true);
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
        printWriter.println("A request to create a Trip was initiated on date: " + Instant.now());
    }

    public void logUserCreation() {
        System.out.println("Logging successful");
        printWriter.println("A request to create a User was initiated on date: " + Instant.now());
    }

}