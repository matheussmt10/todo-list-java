package com.mathcode.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeira-msg")
public class firstcontroller {
    @GetMapping("/")
    public String primeiraMsg() {
        return "ok";
    }
}
