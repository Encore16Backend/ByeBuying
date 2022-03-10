package com.encore.byebuying.api;

import com.encore.byebuying.domain.Message;
import com.encore.byebuying.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageResource {
    private final MessageService messageService;

    @PostMapping("/send")
    public String sendNotification(@RequestBody Message message){
        message.setContent("문의사항 답변이 완료되었습니다.");
        messageService.notify(message);
        return "SUCCES";
    }
}
