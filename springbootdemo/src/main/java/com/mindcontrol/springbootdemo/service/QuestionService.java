package com.mindcontrol.springbootdemo.service;

import com.mindcontrol.springbootdemo.dao.QuestionDao;
import com.mindcontrol.springbootdemo.dao.UserDao;
import com.mindcontrol.springbootdemo.model.Question;
import com.mindcontrol.springbootdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    SensitiveService sensitiveService;

    public List<Question> getLatestQuestions(int userId, int offset, int limit){
        return questionDao.selectLatestQuestions(userId,offset,limit);
    }

    public int addQuestion(Question question){
        //将script等进行转义
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        //敏感词过滤
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));

        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public Question selectById(int id){
        return questionDao.selectById(id);
    }

    public int updateCommentCount(int id,int count){
        return questionDao.updateCommentCount(id,count);
    }
}
