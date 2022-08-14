package pro.sky.telegrambot.notificationinterface;

import pro.sky.telegrambot.model.Notifications;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    void addNotification(Notifications notifications);

    List<Notifications> findNotificationsForSend();

    void deleteNotification(Notifications notifications);

    List<Notifications> getAllNotifications();
}
