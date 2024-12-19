package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /*1: Our API should be able to process new User registrations. */ //COMPLETED
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Account rAccount = accountService.createAccount(account);
        if(rAccount == null){
            return ResponseEntity.status(400).body(null);
        }
        if(rAccount.getAccountId() == null){
            return ResponseEntity.status(409).body(null);
        }
        return ResponseEntity.status(200).body(rAccount);
    }

    /*2: Our API should be able to process User logins. */ //COMPLETED
    @PostMapping("login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account rAccount = accountService.loginAccount(account);
        if (rAccount != null){
            return ResponseEntity.status(200).body(rAccount);
        }

        return ResponseEntity.status(401).body(null);
    }

    /*3: Our API should be able to process the creation of new messages. */
    @PostMapping("/messages")
    public ResponseEntity<Message> insertMessage(@RequestBody Message message){
        Message rMessage = messageService.createMessage(message);
        if(rMessage == null){
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(200).body(rMessage);
    }

    /*4: Our API should be able to retrieve all messages. */ //COMPLETED
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    /*5: Our API should be able to retrieve a message by its ID. */ //COMPLETED
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }
    
    /*6: Our API should be able to delete a message identified by a message ID. */ //COMPLETED
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){
        return ResponseEntity.status(200).body(messageService.deleteMessageById(messageId));
    }

    /*7: Our API should be able to update a message text identified by a message ID. */ //COMPLETED
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@RequestBody Message updatedMessage, @PathVariable Integer messageId){
        int response = messageService.updateMessageById(messageId, updatedMessage);
        if (response == 0){return ResponseEntity.status(400).body(null);}
        return ResponseEntity.status(200).body(response);
        
    }

    /*8: Our API should be able to retrieve all messages written by a particular user. */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccount(@PathVariable Integer accountId){
        return ResponseEntity.status(200).body(messageService.getAllByUserId(accountId));
    }

}
