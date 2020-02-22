package com.IvanBlade.security.controller;


import com.IvanBlade.Bean.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomLoginController {
    @RequestMapping("/login/page")
    public RespBean loginPage() {
        return new RespBean(false,"您还未登录，请登录");
    }
}
