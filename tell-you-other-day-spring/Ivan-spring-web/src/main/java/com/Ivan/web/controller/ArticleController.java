package com.Ivan.web.controller;

import com.Ivan.web.bean.Article;
import com.Ivan.web.bean.RespBean;
import com.Ivan.web.service.ArticleService;
import com.Ivan.web.utils.Util;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sang on 2017/12/20.
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    ArticleService articleService;

    /**
     * 增加或者修改文章
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewArticle(Article article) {
        int result = articleService.addNewArticle(article);
        if (result == 1) {
            return new RespBean("success", article.getId() + "");
        } else {
            return new RespBean("error", article.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
    }
    /**
     * 根据日期和文章类型获取目前登录用户的文章
     */
    @RequestMapping(value = "/{date}/{state}",method = RequestMethod.GET)
    public Article getArticleByDate(@PathVariable String date,@PathVariable Integer state) {
        return articleService.getArticleByDate(date,state);
    }

    /**
     * 根据用户id和文章类型获取文章
     */
    @RequestMapping(value = "/view/{uid}/{state}",method = RequestMethod.GET)
    public List<Article> getArticleByUs(@PathVariable Long uid,@PathVariable Integer state) {
        return articleService.getArticleByUs(uid,state);
    }

    /**
     * 获取一个用户所有文章的发布日期
     * @param id
     * @return
     */
    @RequestMapping(value = "/allPublishDate/{id}",method = RequestMethod.GET)
    public List<String> getAllPublishDate(@PathVariable Long id) {
        return articleService.getAllPublishDate(id);
    }


}

