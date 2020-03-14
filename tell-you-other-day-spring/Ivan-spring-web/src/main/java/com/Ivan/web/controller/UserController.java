package com.Ivan.web.controller;

import com.Ivan.web.bean.RespBean;
import com.Ivan.web.bean.User;
import com.Ivan.web.service.UserService;
import com.Ivan.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/currentUserInfo")
    public User  currentUserInfo() { return Util.getCurrentUser();}

    @RequestMapping(value = "/currentMatchInfo",method = RequestMethod.GET)
    public User currentMatchInfo (Long id) {
        User user = userService.getUserById(id);
        return user;
    }

    @RequestMapping("/currentUserName")
    public String currentUserName() {
        return Util.getCurrentUser().getUsername();
    }

    @RequestMapping("/currentUserId")
    public Long currentUserId() {
        return Util.getCurrentUser().getId();
    }

    @RequestMapping("/currentUserEmail")
    public String currentUserEmail() {
        return Util.getCurrentUser().getEmail();
    }

    @RequestMapping("/currentUserface")
    public String currentUserface() { return  Util.getCurrentUser().getUserface();}

    @RequestMapping("/currentMatch")
    public int currentMatch() { return  Util.getCurrentUser().getIsMatch();}

    @RequestMapping("/currentMatchId")
    public Long currentMatchId() { return  Util.getCurrentUser().getMatchId();}

    @RequestMapping("/isAdmin")
    public Boolean isAdmin() {
        List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains("超级管理员")) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    public RespBean updateUserEmail(String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean("success", "开启成功!");
        }
        return new RespBean("error", "开启失败!");
    }
}
