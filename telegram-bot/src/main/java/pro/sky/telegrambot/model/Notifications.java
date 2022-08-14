package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String notificationText;
    private LocalDateTime dateTime;
    public Notifications() {
    }

    public Notifications(Long id, Long chatId, String notificationText, LocalDateTime dateTime) {
        this.id = id;
        this.chatId = chatId;
        this.notificationText = notificationText;
        this.dateTime = dateTime;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationMessage) {
        this.notificationText = notificationMessage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notifications that = (Notifications) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(notificationText, that.notificationText) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, notificationText, dateTime);
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", notificationMessage='" + notificationText + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
