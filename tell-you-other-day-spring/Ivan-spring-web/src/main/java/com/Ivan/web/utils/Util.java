package com.Ivan.web.utils;


import com.Ivan.web.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;


public class Util {
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
