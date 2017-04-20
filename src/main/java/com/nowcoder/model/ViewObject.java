package com.nowcoder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/20.
 */
public class ViewObject {
    Map<String,Object> objs = new HashMap<String,Object>();

    public void put(String key,Object value){
        objs.put(key,value);
    }

    public Object get(String key){
        return objs.get(key);
    }
}
