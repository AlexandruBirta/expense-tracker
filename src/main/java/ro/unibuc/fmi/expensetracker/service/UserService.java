package ro.unibuc.fmi.expensetracker.service;

import ro.unibuc.fmi.expensetracker.exception.ApiException;
import ro.unibuc.fmi.expensetracker.exception.ExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.fmi.expensetracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.expensetracker.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUser(User user) {

        List<User> repoUsers = userRepository.findAll();

        for (User repoUser : repoUsers) {
            if (repoUser.getEmail().equals(user.getEmail())) {
                throw new ApiException(ExceptionStatus.USER_ALREADY_EXISTS, repoUser.getEmail());
            }
        }

        userRepository.save(user);
        log.info("Created " + user);

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

    @Transactional
    public void updateEmail(Long userId, String email) {

        User userToUpdate = userRepository.findById(userId).orElseThrow(
                () -> new ApiException(ExceptionStatus.USER_NOT_FOUND, String.valueOf(userId)));

        userToUpdate.setEmail(email);
        log.info("Updated email to '" + email + "' for user with id '" + userId + "'");

    }

}