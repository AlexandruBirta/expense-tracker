package ro.unibuc.fmi.expensetracker.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.expensetracker.dto.UserDTO;
import ro.unibuc.fmi.expensetracker.exception.ApiException;
import ro.unibuc.fmi.expensetracker.exception.ExceptionStatus;
import ro.unibuc.fmi.expensetracker.model.Expense;
import ro.unibuc.fmi.expensetracker.model.Trip;
import ro.unibuc.fmi.expensetracker.model.User;
import ro.unibuc.fmi.expensetracker.repository.ExpenseRepository;
import ro.unibuc.fmi.expensetracker.repository.TripRepository;
import ro.unibuc.fmi.expensetracker.repository.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Base64;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final TripRepository tripRepository;

    public UserDTO createUser(User user) {

        List<User> repoUsers = userRepository.findAll();

        for (User repoUser : repoUsers) {
            if (repoUser.getEmail().equals(user.getEmail())) {
                throw new ApiException(ExceptionStatus.USER_ALREADY_EXISTS, repoUser.getEmail());
            }
        }

        String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());

        user.setPassword(encodedPassword);

        log.info("Created " + user);

        return new UserDTO(userRepository.save(user));

    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ApiException(ExceptionStatus.USER_NOT_FOUND, String.valueOf(userId)));
    }

    public void deleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ApiException(ExceptionStatus.USER_NOT_FOUND, String.valueOf(userId));
        }

        userRepository.deleteById(userId);
        log.info("Deleted user with id '" + userId + "'");

    }

    public void updateEmail(Long userId, String email) {

        User userToUpdate = userRepository.findById(userId).orElseThrow(
                () -> new ApiException(ExceptionStatus.USER_NOT_FOUND, String.valueOf(userId)));

        userToUpdate.setEmail(email);
        log.info("Updated email to '" + email + "' for user with id '" + userId + "'");

    }

    public BigDecimal getUserAmountToPay(Long userId, Long expenseId) {

        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new ApiException(ExceptionStatus.EXPENSE_NOT_FOUND, String.valueOf(expenseId)));

        if (!userRepository.existsById(userId)) {
            throw new ApiException(ExceptionStatus.USER_NOT_FOUND, String.valueOf(userId));
        }

        if (!Expense.ExpenseType.GROUP.equals(expense.getExpenseType())) {
            throw new ApiException(ExceptionStatus.INVALID_EXPENSE_TYPE_FOR_PAYMENT_CALCULATION, expense.getExpenseType().name());
        }

        if (isUserParticipatingInExpense(userId, expense)) {
            return expense.getAmountPaid().divide(BigDecimal.valueOf(expense.getUsers().size()), RoundingMode.HALF_DOWN);
        } else {
            throw new ApiException(ExceptionStatus.EXPENSE_USER_NOT_FOUND, String.valueOf(userId), String.valueOf(expenseId));
        }

    }

    private boolean isUserParticipatingInExpense(Long userId, Expense expense) {
        return expense.getUsers().stream().anyMatch(expenseUser -> expenseUser.getUserId().equals(userId));
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new ApiException(ExceptionStatus.USER_NOT_FOUND, String.valueOf(email)));
    }

    public List<Trip> getUserTrips(String email) {
        User user = getUserByEmail(email);
        return tripRepository.getTripByInitiatedByUserId(user.getUserId());
    }

}