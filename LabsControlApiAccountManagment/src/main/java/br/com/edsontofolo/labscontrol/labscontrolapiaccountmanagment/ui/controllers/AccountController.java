package br.com.edsontofolo.labscontrol.labscontrolapiaccountmanagment.ui.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @GetMapping("/status/check")
    public String status() {
        return "Working";
    }
}
