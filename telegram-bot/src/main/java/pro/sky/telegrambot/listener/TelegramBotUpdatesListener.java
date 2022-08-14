package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notifications;
import pro.sky.telegrambot.notificationinterface.NotificationService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private static final Pattern NOTIFICATION_TASK_PATTERN = Pattern.compile("([0-9.:\\s]{16})(\\s)([\\W+]+)");

    private final TelegramBot telegramBot;

    private final NotificationService notificationService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, NotificationService notificationService) {
        this.telegramBot = telegramBot;
        this.notificationService = notificationService;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Long chatId = update.message().chat().id();
            String messageText = update.message().text();
            if (messageText.equals("/start")) {
                SendMessage message1 = new SendMessage(chatId, "Привет! Я напомню тебе о важных делах." +
                        "\n Напиши дату, время и то, что нужно напомнить!" +
                        "\n На пример 31.12.2022 18:00 Купить майонез для салатов!!!");
                SendResponse response = telegramBot.execute(message1);
                System.out.println(response.isOk());
                System.out.println(response.errorCode());
            } else {
                Pattern pattern = Pattern.compile(NOTIFICATION_TASK_PATTERN.pattern());
                Matcher matcher = pattern.matcher(messageText);
                if (matcher.matches()) {
                    String date = matcher.group(1);
                    String item = matcher.group(3);
                    LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.
                            ofPattern("dd.MM.yyyy HH:mm"));
                    Notifications notifications = new Notifications();
                    notifications.setChatId(chatId);
                    notifications.setNotificationText(item);
                    notifications.setDateTime(dateTime);
                    notificationService.addNotification(notifications);
                    SendMessage message2 = new SendMessage(chatId, "Готово!");
                    SendResponse response = telegramBot.execute(message2);
                    System.out.println(response.isOk());
                    System.out.println(response.errorCode());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void sendNotification() {
        List<Notifications> notificationTasks = notificationService.findNotificationsForSend();
        notificationTasks.forEach(notification_task -> {
            telegramBot.execute(new SendMessage(notification_task.getChatId(),
                    notification_task.getDateTime().toString() + " " +
                            notification_task.getNotificationText()));
        });
        notificationTasks.forEach(notificationService::deleteNotification);
    }


}
