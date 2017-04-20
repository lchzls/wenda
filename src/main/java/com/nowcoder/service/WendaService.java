package com.nowcoder.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/20.
 */
@Service
public class WendaService {

    public String getMessage(int userId){
        return "Hello message:"+String.valueOf(userId);
    }
}
