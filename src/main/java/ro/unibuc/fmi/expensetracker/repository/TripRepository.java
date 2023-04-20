package ro.unibuc.fmi.expensetracker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.expensetracker.model.Trip;

import java.util.List;


@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> getTripByInitiatedByUserId(Long initiatedUserId);

}
