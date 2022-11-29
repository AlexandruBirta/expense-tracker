package ro.unibuc.fmi.expensetracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.service.TripService;
import ro.unibuc.fmi.expensetracker.singleton.LogApplicationRequests;

@RestController
@RequestMapping("/v1/trips")
@AllArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    Trip create(@RequestBody Trip trip) {
        LogApplicationRequests.getInstance().logTripCreation();
        return tripService.createTrip(trip);
    }

    @GetMapping("/{tripId}")
    public Trip getTripById(@PathVariable Long tripId) {
        return tripService.getTripById(tripId);
    }
}
