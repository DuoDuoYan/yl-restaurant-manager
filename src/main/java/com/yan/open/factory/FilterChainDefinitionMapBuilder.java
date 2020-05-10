package com.yan.open.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {
    public LinkedHashMap<String,String> builderFilterChainDefinitionMap(){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("/user/**","anon");
        map.put("/restaurant/login","anon");
        map.put("/restaurant/register","anon");
        map.put("/defaultKaptcha","anon");
        map.put("/restaurant/toLogin","anon");
        map.put("/restaurant/toRegister","anon");
        map.put("/static/css/**","anon");
        map.put("/static/image/**","anon");
        map.put("/static/js/**","anon");
        map.put("/static/bootstrap/**","anon");
        map.put("/restaurant/logout","logout");
        map.put("/**","authc");
        return map;
    }
}
