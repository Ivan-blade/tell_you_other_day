package com.Ivan.web.utils;

import com.Ivan.web.bean.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendQQMailUtil {

    @Autowired
    private JavaMailSender jms;

    public void send(String receiver){
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        Email email = new Email();
        //发送者
        email.setSender("2734319366@qq.com");
        mainMessage.setFrom(email.getSender());
        //接收者
        email.setReceiver(receiver);
        mainMessage.setTo(email.getReceiver());

        //发送的标题
        email.setTitle("日记猫邮箱验证通知");
        mainMessage.setSubject(email.getTitle());
        //发送的内容
        email.setText("欢迎使用日记猫，您此次的注册验证码为：3273");
        mainMessage.setText(email.getText());
        jms.send(mainMessage);
    }

}