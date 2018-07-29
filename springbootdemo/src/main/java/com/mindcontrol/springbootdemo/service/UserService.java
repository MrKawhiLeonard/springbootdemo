package com.mindcontrol.springbootdemo.service;

import com.mindcontrol.springbootdemo.dao.LoginTicketDAO;
import com.mindcontrol.springbootdemo.dao.UserDao;
import com.mindcontrol.springbootdemo.model.LoginTicket;
import com.mindcontrol.springbootdemo.model.User;
import com.mindcontrol.springbootdemo.util.WendaUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public User getUser(int id){
        return userDao.selectById(id);
    }

    public Map<String,String> register(String username, String password){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isEmptyOrWhitespaceOnly(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDao.selectByName(username);
        if(user != null){
            map.put("msg","用户名已经被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }

    public Map<String ,String> login(String username,String password){
        Map<String ,String> map = new HashMap<>();
        if(StringUtils.isEmptyOrWhitespaceOnly(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmptyOrWhitespaceOnly(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDao.selectByName(username);
        if(user == null){
            map.put("msg","用户名不存在");
            return map;
        }

        if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }

    public void logout(String ticket){
        loginTicketDAO.updateStatus(ticket,1);
    }

    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600*24*100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public User selectByName(String name){
        return userDao.selectByName(name);
    }
}
