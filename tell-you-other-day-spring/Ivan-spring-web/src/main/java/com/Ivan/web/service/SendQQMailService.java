package com.Ivan.web.service;

import com.Ivan.web.utils.SendQQMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendQQMailService {

    @Autowired
    private SendQQMailUtil sendQQMailUtil;

    public void send(String receiver){
        sendQQMailUtil.send(receiver);
    }

}