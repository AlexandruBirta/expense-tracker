package ro.unibuc.fmi.expensetracker.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.expensetracker.dto.TripDTO;
import ro.unibuc.fmi.expensetracker.exception.ApiException;
import ro.unibuc.fmi.expensetracker.exception.ExceptionStatus;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.model.User;
import ro.unibuc.fmi.expensetracker.repository.TripRepository;
import ro.unibuc.fmi.expensetracker.repository.UserRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return tripRepository.findById(tripId).orElseThrow(
                () -> new ApiException(ExceptionStatus.TRIP_NOT_FOUND, String.valueOf(tripId)));
    }

    public TripDTO addMembersToTrip(Long tripId, List<Long> userIds) {

        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new ApiException(ExceptionStatus.TRIP_NOT_FOUND, String.valueOf(tripId)));

        for (Long id : userIds) {

            User user = userRepository.findById(id).orElseThrow(
                    () -> new ApiException(ExceptionStatus.USER_NOT_FOUND, String.valueOf(id)));

            user.addTrip(trip);
            userRepository.save(user);

            trip.getUsers().add(user);
            tripRepository.save(trip);
        }

        return new TripDTO(trip);

    }

    public void deleteTrip(Long tripId) {

        if (!tripRepository.existsById(tripId)) {
            throw new ApiException(ExceptionStatus.TRIP_NOT_FOUND, String.valueOf(tripId));
        }

        tripRepository.deleteById(tripId);
        log.info("Deleted trip with id '" + tripId + "'");

    }

    public BigDecimal getTripTotalSum(Long tripId) {
        return tripRepository.findById(tripId).orElseThrow(
                () -> new ApiException(ExceptionStatus.TRIP_NOT_FOUND, String.valueOf(tripId))).getExpenseTotalSum();
    }

}