package com.mindcontrol.springbootdemo.controller;



import com.mindcontrol.springbootdemo.model.Question;
import com.mindcontrol.springbootdemo.model.ViewObject;
import com.mindcontrol.springbootdemo.service.QuestionService;
import com.mindcontrol.springbootdemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/user/{userId}"})
    public String userIndex(Model model, @PathVariable("userId")int userId){
        model.addAttribute("vos",getQuestions(userId,0,10));
        return "index";
    }

    @RequestMapping(path = {"/","/index"})
    public String index(Model model){
        model.addAttribute("vos",getQuestions(0,0,10));
        return "index";
    }

    private List<ViewObject> getQuestions(int userId,int offset,int limit){
        List<Question> questionList = questionService.getLatestQuestions(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for(Question question : questionList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
