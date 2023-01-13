package ro.unibuc.fmi.expensetracker.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.unibuc.fmi.expensetracker.model.Notification;
import ro.unibuc.fmi.expensetracker.model.User;
import ro.unibuc.fmi.expensetracker.repository.NotificationRepository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class NotificationService {

    private static final String MESSAGE_TEMPLATE = "For the trip with id %d you have to pay %s";
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public void createNotification(Long userId, Long tripId, Long expenseId) {

        User user = userService.getUserById(userId);

        BigDecimal amountToPay = userService.getUserAmountToPay(userId, expenseId);
        String notificationMessage = String.format(MESSAGE_TEMPLATE, tripId, amountToPay.toString());

        notificationRepository.save(
                Notification.builder()
                        .message(notificationMessage)
                        .user(user)
                        .build()
        );

    }

    public List<Notification> findAllByUserId(Long userId) {
        return notificationRepository.findAllByUserUserId(userId);
    }

}