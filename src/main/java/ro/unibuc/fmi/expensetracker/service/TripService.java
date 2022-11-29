package ro.unibuc.fmi.expensetracker.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.repository.TripRepository;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip getTripById(Long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isPresent()) {
            return optionalTrip.get();
        } else {
            throw new RuntimeException("Trip not found!");
        }
    }
}
