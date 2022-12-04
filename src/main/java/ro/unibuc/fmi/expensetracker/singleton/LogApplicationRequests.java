package ro.unibuc.fmi.expensetracker.singleton;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

@Slf4j
public class LogApplicationRequests {
    private static LogApplicationRequests logApplicationRequests = null;
    private PrintWriter printWriter;

    private LogApplicationRequests() {
        try {
            String fileName = "logDateRequests.txt";
            FileWriter fileWriter = new FileWriter(fileName);
            printWriter = new PrintWriter(fileWriter, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized LogApplicationRequests getInstance() {
        if (logApplicationRequests == null)
            logApplicationRequests = new LogApplicationRequests();
        return logApplicationRequests;
    }

    public void logTripCreation() {
        log.info("Logging successful");
        printWriter.println("A request to create a Trip was initiated on date: " + Instant.now());
    }

    public void logUserCreation() {
        log.info("Logging successful");
        printWriter.println("A request to create an User was initiated on date: " + Instant.now());
    }

    public void logExpenseCreation() {
        log.info("Logging successful");
        printWriter.println("A request to create an Expense was initiated on date: " + Instant.now());
    }

}