package com.digit.test.service;

import com.digit.test.model.Message;
import com.digit.test.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void save(Message message) {
        if (message.getText().equals("")) {
            message.setState(Message.State.EMPTY);
        } else {
            message.setState(Message.State.NORMAL);
        }
        messageRepository.save(message);
    }

    @Override
    public Message getLastMessage() {
        Optional<Message> messageOptional = messageRepository.getLastMessage();
        if (messageOptional.isPresent()) {
            return messageOptional.get();
        } else {
            throw new IllegalStateException("No message");
        }
    }
}
