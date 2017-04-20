package com.nowcoder.controller;

import com.nowcoder.model.User;
import com.nowcoder.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2017/4/20.
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    WendaService wendaService;

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpSession session){
        logger.info("visit index");
        return "index"+wendaService.getMessage(2)+session.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String index(@PathVariable("userId") int userId,
                        @PathVariable("groupId") String groupId,
                        @RequestParam(value = "type",defaultValue = "1") String type,
                        @RequestParam(value = "key",required = false) String key){
        return  String.format("profile page of user %d,group %s,type %s,key %s",userId,groupId,type,key);
    }

    @RequestMapping(path = {"/vm"},method = RequestMethod.GET)
    public String template(Model model){
        model.addAttribute("value1","xxxxxxx");
        List<String> colors = Arrays.asList(new String[]{"RED","GREEN","BLUE"});
        model.addAttribute("colors",colors);
        Map<String,String> map = new HashMap<String,String>();
        for(int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        model.addAttribute("user",new User("Jim"));
        return "home";
    }
    @RequestMapping(path = {"/request"},method = RequestMethod.GET)
    @ResponseBody
    public String request(Model model, HttpServletRequest request,
                          HttpServletResponse response, HttpSession session,
                          @CookieValue("JSESSIONID") String sessionId){
        StringBuilder sb = new StringBuilder();
        sb.append(sessionId+"<br>");
        Enumeration<String> headNames = request.getHeaderNames();
        while(headNames.hasMoreElements()){
            String name = headNames.nextElement();
            sb.append("name:"+request.getHeader(name)+"<br>");
        }
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            sb.append("name:"+cookie.getName()+",value:"+cookie.getValue()+"<br>");
        }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURL()+"<br>");

        response.addHeader("header","nowcoder");
        response.addCookie(new Cookie("userName","nowcoder"));
        return sb.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"},method = RequestMethod.GET)
    public String redirect(@PathVariable("code") int code,HttpSession session){
        session.setAttribute("msg",code);
        return "redirect:/";
    }

    @RequestMapping(path = {"/redirect1/{code}"},method = RequestMethod.GET)
    public RedirectView redirect1(@PathVariable("code") int code, HttpSession session){
        RedirectView redirectView = new RedirectView("/");
        session.setAttribute("msg",code);
        return redirectView;
    }

    @RequestMapping(path = {"/admin"},method = RequestMethod.GET)
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "admin:"+key;
        }
        else throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String getMessage(Exception e){
        return e.getMessage();
    }

}
