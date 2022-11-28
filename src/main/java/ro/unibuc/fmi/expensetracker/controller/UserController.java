package ro.unibuc.fmi.expensetracker.controller;

import ro.unibuc.fmi.expensetracker.api.UserApi;
import ro.unibuc.fmi.expensetracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ro.unibuc.fmi.expensetracker.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController implements UserApi {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser(@RequestBody @Valid User user) {
        userService.createUser(user);
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

}