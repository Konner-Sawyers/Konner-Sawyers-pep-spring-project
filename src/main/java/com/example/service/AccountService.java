package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        if(account.getUsername().length() == 0 && account.getPassword().length() < 4){return null;}
        if(accountRepository.findDistinctByUsername(account.getUsername()) != null){return account;}
        Account nAccount = new Account(account.getUsername(), account.getPassword());
        accountRepository.save(nAccount);
        return accountRepository.findDistinctByUsername(nAccount.getUsername());
    }

    public Account loginAccount(Account account){
        Account discovery = accountRepository.findDistinctByUsername(account.getUsername());
        if(discovery == null){return null;}
        if(discovery.getPassword().equals(account.getPassword())){return discovery;}
        return null;
    }

    public boolean doesAccountExistById(int id){
        Optional<Account> discovery = accountRepository.findById(id);
        if(discovery.isEmpty()){return false;}
        return true;

    }

}
