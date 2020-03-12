package com.Ivan.web.service;

import com.Ivan.web.bean.Article;
import com.Ivan.web.mapper.ArticleMapper;
import com.Ivan.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    public int addNewArticle(Article article) {
        //处理文章摘要(用于展示文章部分内容)
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            //直接截取
            String stripHtml = article.getMdContent();
            article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if (article.getId() == -1) {
            //添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            article.setEditTime(timestamp);
            //设置当前用户
            article.setUid(Util.getCurrentUser().getId());
            int i = articleMapper.addNewArticle(article);
            return i;
        } else {
            //更新
            article.setEditTime(new Timestamp(System.currentTimeMillis()));
            int i = articleMapper.updateArticle(article);
            return i;
        }
    }

    public Article getArticleByDate(String date,Integer state) {
        Long uid = Util.getCurrentUser().getId();
        Article article = articleMapper.getArticleByDate(date,uid,state);
        return article;
    }

    public List<Article> getArticleByUs(Long uid,Integer state) {
        List<Article> articles = new ArrayList<>();
        Long tempId = Util.getCurrentUser().getId();
        if (uid == tempId) {
            articles = articleMapper.getArticleByUs(uid,state);
        } else if (uid != tempId) {
            articles = articleMapper.getArticleByUsOther(tempId,state);
        }
        return articles;
    }

    public List<String> getAllPublishDate(Long id) {
        List<String> allPublishDate = articleMapper.getAllPublishDate(id);
        return allPublishDate;
    }

}
