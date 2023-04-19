package ro.unibuc.fmi.expensetracker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.expensetracker.api.UserApi;
import ro.unibuc.fmi.expensetracker.dto.ExpenseDTO;
import ro.unibuc.fmi.expensetracker.dto.UserDTO;
import ro.unibuc.fmi.expensetracker.model.Expense;
import ro.unibuc.fmi.expensetracker.model.User;
import ro.unibuc.fmi.expensetracker.repository.ExpenseRepository;
import ro.unibuc.fmi.expensetracker.service.UserService;
import ro.unibuc.fmi.expensetracker.singleton.LogApplicationRequests;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController implements UserApi {

    private final UserService userService;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public UserController(UserService userService, ExpenseRepository expenseRepository) {
        this.userService = userService;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public UserDTO createUser(@RequestBody @Valid User user) {
        LogApplicationRequests.getInstance().logUserCreation();
        return userService.createUser(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    @Override
    public void updateEmail(Long userId, String email) {
        userService.updateEmail(userId, email);
    }

    @Override
    public List<ExpenseDTO> getExpensesReport(LocalDateTime start, LocalDateTime end) {
        List<Expense> allExpenses;
        if (start != null && end != null) {
            allExpenses = expenseRepository.findAllByInsertedDateIsAfterAndInsertedDateBefore(start, end);
        } else {
            allExpenses = expenseRepository.findAll();
        }

        return allExpenses
                .stream()
                .map(ExpenseDTO::new)
                .toList();
    }

    @Override
    public BigDecimal getUserAmountToPay(Long userId, Long expenseId) {
        return userService.getUserAmountToPay(userId, expenseId);
    }

}