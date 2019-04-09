package com.apimanagement.demo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Map<String, Cookie> addToMap(HttpServletRequest request){

        Map<String, Cookie> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie);
            }
        }
        return map;
    }

    public static Cookie get(HttpServletRequest request,
                             String name){

        Map<String, Cookie> map = addToMap(request);
        if(map.containsKey(name)){
            return map.get(name);
        }
        else{
            return null;
        }
    }
}
