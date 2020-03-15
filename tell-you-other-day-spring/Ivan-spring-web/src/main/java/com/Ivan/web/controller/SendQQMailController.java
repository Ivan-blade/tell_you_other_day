package com.Ivan.web.controller;

import com.Ivan.web.service.SendQQMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class SendQQMailController {

    @Autowired
    private SendQQMailService sendQQMailService;

    @RequestMapping("/sendMail")
    public String send(String receiver){
        sendQQMailService.send(receiver);
        return "success";
    }

}