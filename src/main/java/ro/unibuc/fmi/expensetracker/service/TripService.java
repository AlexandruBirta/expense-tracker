package ro.unibuc.fmi.expensetracker.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.unibuc.fmi.expensetracker.dto.TripDTO;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.model.User;
import ro.unibuc.fmi.expensetracker.repository.TripRepository;
import ro.unibuc.fmi.expensetracker.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class TripService {

    private final TripRepository tripRepository;

    private final UserRepository userRepository;

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

    public TripDTO addMembersToTrip(Long tripId, List<Long> userIds) {
        Optional<Trip> tripFromDb = tripRepository.findById(tripId);
        if (tripFromDb.isEmpty()) {
            throw new RuntimeException("Trip not found!");
        }
        Set<User> users = new HashSet<>();
        for (Long id : userIds) {
            Optional<User> user = userRepository.findById(id);
            user.get().addTrip(tripFromDb.get());
            userRepository.save(user.get());
            user.ifPresent(users::add);
        }
        tripFromDb.get().setUsers(users);

        tripRepository.save(tripFromDb.get());

        return new TripDTO(tripFromDb.get());
    }
}
