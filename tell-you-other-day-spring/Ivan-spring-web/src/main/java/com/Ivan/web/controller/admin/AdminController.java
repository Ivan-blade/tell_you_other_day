package com.Ivan.web.controller.admin;

import com.Ivan.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ArticleService articleService;


}

