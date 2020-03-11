package com.Ivan.web.mapper;

import com.Ivan.web.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;


import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ArticleMapper {
    int addNewArticle(Article article);

    int updateArticle(Article article);

    List<String> getAllPublishDate(Long id);

    List<Article> getArticleByUs(@Param("uid") Long uid, @Param("state") Integer state);

    Article getArticleByDate(@Param("date") String date,@Param("uid") Long uid,@Param("state") Integer state);

}

