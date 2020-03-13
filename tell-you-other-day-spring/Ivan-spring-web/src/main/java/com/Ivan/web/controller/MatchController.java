package com.Ivan.web.controller;


import com.Ivan.web.bean.Match;
import com.Ivan.web.bean.RespBean;
import com.Ivan.web.bean.User;
import com.Ivan.web.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    MatchService matchService;

    @RequestMapping(value = "/getMatchInfo",method = RequestMethod.GET)
    public List<Match> getInfo() {
        return matchService.getMatchInfo();
    }

    @RequestMapping(value = "/pullMatch",method = RequestMethod.POST)
    public RespBean pullMatch (String email) {
        int result = matchService.pullMatch(email);
        if(result == 1) {
            return new RespBean("success","请求成功");
        } else {
            return new RespBean("error","请求失败");
        }
    }

    @RequestMapping(value = "/statusChange/{status}",method = RequestMethod.POST)
    public int statusChange(@PathVariable int status,Long userId,Long otherId,Long id){
        int i = matchService.statusChange(status,userId,otherId,id);
        return i;
    }
}
