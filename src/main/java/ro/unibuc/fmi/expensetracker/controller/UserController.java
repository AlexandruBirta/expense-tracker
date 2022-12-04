package ro.unibuc.fmi.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.expensetracker.api.UserApi;
import ro.unibuc.fmi.expensetracker.dto.UserDTO;
import ro.unibuc.fmi.expensetracker.model.User;
import ro.unibuc.fmi.expensetracker.service.UserService;
import ro.unibuc.fmi.expensetracker.singleton.LogApplicationRequests;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    @Override
    public void updateEmail(Long userId, String email) {
        userService.updateEmail(userId, email);
    }

    @Override
    public BigDecimal getUserAmountToPay(Long userId, Long expenseId) {
        return userService.getUserAmountToPay(userId, expenseId);
    }

}