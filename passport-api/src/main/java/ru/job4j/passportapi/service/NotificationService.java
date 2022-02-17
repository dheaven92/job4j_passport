package ru.job4j.passportapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.job4j.passportapi.model.Passport;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final PassportService passportService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.notification-topic}")
    private String notificationTopic;

    @Scheduled(cron = "${schedule.notification-cron}")
    public void sendNotifications() {
        List<Passport> passports = passportService.findAllToBeReplaced();

        passports.forEach(passport -> {
            String fullPassportNumber = passport.getSeries() + " " + passport.getNumber();
            String message = Map.of(fullPassportNumber, "Passport " + fullPassportNumber + " is expiring").toString();

            kafkaTemplate.send(notificationTopic, message).addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onFailure(Throwable ex) {
                    log.error("Could not send message {} to topic {}", message, notificationTopic, ex);
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info("Message {} was sent to topic {}", message, notificationTopic);
                }
            });
        });
    }
}
