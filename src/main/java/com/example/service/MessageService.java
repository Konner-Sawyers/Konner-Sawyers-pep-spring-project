package com.example.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int Id){
        Optional<Message> message = messageRepository.findById(Id);
        if (message.isEmpty()){
            return null;
        }
        return message.get();
    }

    public Integer deleteMessageById(int Id){
        Optional<Message> message = messageRepository.findById(Id);
        if (message.isEmpty()){return null;}
        messageRepository.deleteById(Id);
        return 1;
    }

    public Integer updateMessageById(int Id, Message updatedMessage){
        Optional<Message> optionalMessage = messageRepository.findById(Id);
        if(!optionalMessage.isEmpty()){
            Message tMessage = optionalMessage.get();
            if(0 < updatedMessage.getMessageText().length() && updatedMessage.getMessageText().length() <= 255){
                tMessage.setMessageText(updatedMessage.getMessageText());
                messageRepository.save(tMessage);
                return 1;
            }
        }
        return 0;
    }

    public List<Message> getAllByUserId(int id){
        return messageRepository.findDistinctBypostedBy(id);
    }

}
