package com.mindcontrol.springbootdemo.controller;

import com.mindcontrol.springbootdemo.aspect.LogAspect;
import com.mindcontrol.springbootdemo.model.User;
import com.mindcontrol.springbootdemo.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//@Controller
public class IndexController {
    @Autowired
    WendaService wendaService;

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);


    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession httpSession) {
        logger.info("VISIT HOME");
        return wendaService.getMessage(2) + " Hello " + httpSession.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", required = false) String key) {
        return String.format("Profile Page of %d,type:%d,key:%s", userId, type, key);
    }

    @RequestMapping(path = {"/ftl"})
    public String template(Model model) {
        model.addAttribute("value1", "lr");
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 5; ++i) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        model.addAttribute("maps", map);
        model.addAttribute("user", new User("lr"));
        return "home";
    }

    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String requestTest(Model model,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession httpSession,
                              @CookieValue("JSESSIONID") String sessionId) {
            StringBuilder sb = new StringBuilder();
            sb.append("CookieValue : " + sessionId + "<br>");
            Enumeration<String> headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements()){
                String name = headerNames.nextElement();
                sb.append(name + " : " + request.getHeader(name) + "<br>");
            }
            if(request.getCookies() != null){
                for(Cookie cookie : request.getCookies()){
                    sb.append("Cookie : " + cookie.getName() + " Value : " + cookie.getValue() + "<br>");
                }
            }
            sb.append(request.getMethod() + "<br>");
            sb.append(request.getQueryString() + "<br>");
            sb.append(request.getPathInfo() + "<br>");
            sb.append(request.getRequestURI() + "<br>");

            response.addHeader("nowcoderId","hello");
            response.addCookie(new Cookie("username","lr"));

            return  sb.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg","jump from redirect");
        RedirectView red = new RedirectView("/",true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path = {"/admin"})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error : " + e.getMessage();
    }
}
