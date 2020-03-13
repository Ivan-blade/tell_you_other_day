package com.Ivan.web.service;


import com.Ivan.web.bean.Match;
import com.Ivan.web.bean.RespBean;
import com.Ivan.web.bean.User;
import com.Ivan.web.mapper.MatchMapper;
import com.Ivan.web.mapper.UserMapper;
import com.Ivan.web.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MatchService {

    @Autowired
    MatchMapper matchMapper;

    @Autowired
    UserMapper userMapper;

    public List<Match> getMatchInfo() {
        Long uid = Util.getCurrentUser().getId();
        List<Match> matchInfo = matchMapper.getMatchInfo(uid);
        return  matchInfo;
    }

    public int pullMatch (String email) {
        Long userId = Util.getCurrentUser().getId();
        String userEmail = Util.getCurrentUser().getEmail();
        int i = matchMapper.pullMatch(email,userId,userEmail);
        return i;
    }

    public int statusChange (int status, Long userId, Long otherId,Long id) {
        int i = 0;
        if(status == 1){
            userMapper.updateUserMatch(userId,otherId);
            userMapper.updateUserMatch(otherId,userId);
            i = matchMapper.updateMatch(id,status);
        } else if (status == 2) {
            i = matchMapper.updateMatch(id,status);
        }
        return i;
    }
}
