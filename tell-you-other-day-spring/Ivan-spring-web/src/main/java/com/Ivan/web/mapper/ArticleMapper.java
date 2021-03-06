package com.Ivan.web.mapper;

import com.Ivan.web.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    int addNewArticle(Article article);

    int updateArticle(Article article);

    List<String> getAllPublishDate(Long id);

    List<Article> getArticleByUs(@Param("uid") Long uid, @Param("state") Integer state);

    List<Article> getArticleByUsOther(@Param("uid") Long uid,@Param("tempId") Long tempId, @Param("state") Integer state);

    Article getArticleByDate(@Param("date") String date,@Param("uid") Long uid,@Param("state") Integer state);

}

