package net.raissa.students.web;

import net.raissa.students.models.services.AccountService;

public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


}
