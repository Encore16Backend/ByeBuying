package com.encore.byebuying.domain.common.service;

import com.encore.byebuying.domain.common.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Slf4j
public class MessageService {
    private final SimpMessagingTemplate messagingTemplate;

    public void notify(Message message) {
        messagingTemplate.convertAndSendToUser(
                message.getUsername(),
                "/queue/notify",
                message.getContent()
        );
    }
}
