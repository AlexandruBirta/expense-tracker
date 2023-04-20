package ro.unibuc.fmi.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.fmi.expensetracker.model.Expense;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByInsertedDateIsAfterAndInsertedDateBefore(LocalDateTime start, LocalDateTime end);

    List<Expense> findAllByTripTripId(Long tripId);


}