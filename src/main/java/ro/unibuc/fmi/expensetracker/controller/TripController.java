package ro.unibuc.fmi.expensetracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.expensetracker.api.TripApi;
import ro.unibuc.fmi.expensetracker.dto.TripDTO;
import ro.unibuc.fmi.expensetracker.model.Expense;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.service.ExpenseService;
import ro.unibuc.fmi.expensetracker.service.TripService;
import ro.unibuc.fmi.expensetracker.singleton.LogApplicationRequests;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
public class TripController implements TripApi {

    private final TripService tripService;
    private final ExpenseService expenseService;

    @Override
    public Trip createTrip(@RequestBody Trip trip) {
        LogApplicationRequests.getInstance().logTripCreation();
        return tripService.createTrip(trip);
    }

    @Override
    public Expense createTripExpense(@PathVariable Long tripId, @RequestBody Expense expense, @RequestParam List<Long> userId) {
        LogApplicationRequests.getInstance().logExpenseCreation();
        return expenseService.createExpense(tripId, expense, userId);
    }

    @Override
    public Trip getTripById(@PathVariable Long tripId) {
        return tripService.getTripById(tripId);
    }

    @Override
    public TripDTO addMembersToTrip(@PathVariable Long tripId, @RequestParam List<Long> userId) {
        LogApplicationRequests.getInstance().logTripCreation();
        return tripService.addMembersToTrip(tripId, userId);
    }

    @Override
    public void deleteTrip(@PathVariable Long tripId) {
        tripService.deleteTrip(tripId);
    }

    @Override
    public BigDecimal getTripTotalSum(@PathVariable Long tripId) {
        return tripService.getTripTotalSum(tripId);
    }

}