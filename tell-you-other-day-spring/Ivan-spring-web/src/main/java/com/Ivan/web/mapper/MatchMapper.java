package com.Ivan.web.mapper;


import com.Ivan.web.bean.Match;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MatchMapper {

    List<Match> getMatchInfo(Long uid);

    int pullMatch(@Param("email") String email,@Param("userId") Long userId,@Param("userEmail") String userEmail);

    int updateMatch(@Param("id") Long id,@Param("status") int status);
}
