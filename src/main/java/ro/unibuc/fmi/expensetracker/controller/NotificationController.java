package ro.unibuc.fmi.expensetracker.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.fmi.expensetracker.api.NotificationApi;
import ro.unibuc.fmi.expensetracker.model.Notification;
import ro.unibuc.fmi.expensetracker.service.NotificationService;

import java.util.List;

@RestController
@AllArgsConstructor
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    @Override
    public List<Notification> findAllByUserId(Long userId) {
        return notificationService.findAllByUserId(userId);
    }

}