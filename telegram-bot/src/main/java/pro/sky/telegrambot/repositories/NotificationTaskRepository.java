package pro.sky.telegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Notifications;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findNotificationsByDateTime(LocalDateTime localDateTime);
    List<Notifications> findAll();
}
