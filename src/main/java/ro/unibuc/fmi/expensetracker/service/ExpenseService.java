package ro.unibuc.fmi.expensetracker.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.expensetracker.exception.ApiException;
import ro.unibuc.fmi.expensetracker.exception.ExceptionStatus;
import ro.unibuc.fmi.expensetracker.model.Expense;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.model.User;
import ro.unibuc.fmi.expensetracker.repository.ExpenseRepository;
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
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public Expense createExpense(Long tripId, Expense expense, List<Long> userIds) {

        Trip trip = tripRepository.findById(tripId).orElseThrow(
                () -> new ApiException(ExceptionStatus.TRIP_NOT_FOUND, String.valueOf(tripId)));

        expense.setTrip(trip);

        Set<User> users = new HashSet<>();

        for (Long id : userIds) {

            User user = userRepository.findById(id).orElseThrow(
                    () -> new ApiException(ExceptionStatus.USER_NOT_FOUND, String.valueOf(id)));

            users.add(user);

        }

        if (users.size() > 1 && Expense.ExpenseType.PERSONAL.equals(expense.getExpenseType())) {
            throw new ApiException(ExceptionStatus.INVALID_PERSONAL_EXPENSE, userIds.toString());
        } else if (users.size() == 1 && Expense.ExpenseType.GROUP.equals(expense.getExpenseType())) {
            throw new ApiException(ExceptionStatus.INVALID_GROUP_EXPENSE, userIds.toString());
        }

        expense.setUsers(users);

        for (User user : users) {
            user.addExpense(expense);
        }

        BigDecimal tripTotalSum = trip.getExpenseTotalSum();

        trip.setExpenseTotalSum(tripTotalSum.add(expense.getAmountPaid()));

        if (Expense.ExpenseType.GROUP.equals(expense.getExpenseType())) {
            //TODO notify users
        }

        return expenseRepository.save(expense);
    }

    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId).orElseThrow(
                () -> new ApiException(ExceptionStatus.EXPENSE_NOT_FOUND, String.valueOf(expenseId)));
    }

    public void deleteExpense(Long expenseId) {

        if (!expenseRepository.existsById(expenseId)) {
            throw new ApiException(ExceptionStatus.EXPENSE_NOT_FOUND, String.valueOf(expenseId));
        }

        expenseRepository.deleteById(expenseId);
        log.info("Deleted expense with id '" + expenseId + "'");

    }

}