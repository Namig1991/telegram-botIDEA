package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notifications;
import pro.sky.telegrambot.notificationinterface.NotificationService;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationsServiceImpl implements NotificationService {

    Logger logger = LoggerFactory.getLogger(NotificationsServiceImpl.class);

    private final NotificationTaskRepository notificationTaskRepository;


    public NotificationsServiceImpl(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Override
    public void addNotification(Notifications notifications) {
        logger.info("Был вызван метод addNotification");
        notificationTaskRepository.save(notifications);
    }

    @Override
    public List<Notifications> findNotificationsForSend() {
        logger.info("Был вызван метод findNotificationsForSend");
        return notificationTaskRepository.findNotificationsByDateTime(LocalDateTime.now().
                truncatedTo(ChronoUnit.MINUTES));
    }

    @Override
    public void deleteNotification(Notifications notifications) {
        logger.info("Был вызван метод deleteNotification");
        notificationTaskRepository.delete(notifications);
    }

    @Override
    public List<Notifications> getAllNotifications(){
        logger.info("Был вызван метод getAllNotifications");
        return notificationTaskRepository.findAll();
    }


}
