package com.digit.test.service;

import com.digit.test.model.Message;

public interface MessageService {
    void save(Message message);
    Message getLastMessage();
}
