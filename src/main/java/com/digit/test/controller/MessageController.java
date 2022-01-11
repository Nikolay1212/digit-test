package com.digit.test.controller;

import com.digit.test.model.Message;
import com.digit.test.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MessageService messageService;

    @GetMapping("/message/last")
    private Message getLastMessage() {
        Thread thread = new Thread(task);
        Message message = messageService.getLastMessage();
        if (message.getText().equals("")) {
            logger.warn("Last message is empty");
            thread.start();
            return messageService.getLastMessage();
        }
        logger.info("Get last message");
        return messageService.getLastMessage();
    }
    Runnable task = () -> {
        ((ConfigurableApplicationContext) context).close();
    };



    @PostMapping("/message")
    private void createMessage(@RequestBody Message message) {
        if (!message.getText().equals("")) {
            logger.warn("Create new message");
        } else {
            message.setState(Message.State.NORMAL);
            logger.warn("Create empty message");
        }
        messageService.save(message);
    }
}
