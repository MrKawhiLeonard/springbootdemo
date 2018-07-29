package com.mindcontrol.springbootdemo;

import com.mindcontrol.springbootdemo.dao.QuestionDao;
import com.mindcontrol.springbootdemo.dao.UserDao;
import com.mindcontrol.springbootdemo.model.Question;
import com.mindcontrol.springbootdemo.model.User;
import com.mindcontrol.springbootdemo.util.WendaUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDao userDao;
    @Autowired
    QuestionDao questionDao;

    @Test
    public void initDatabase() {
        Random random = new Random();

        for(int i=0;i < 11;++i){
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
            user.setName("user"+i);
            user.setSalt(UUID.randomUUID().toString().substring(0,5));
            user.setPassword(WendaUtil.MD5("123" + user.getSalt()));
            userDao.addUser(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*i);
            question.setCreatedDate(date);
            question.setTitle(String.format("Title{%d}",i));
            question.setUserId(i+1);
            question.setContent(String.format("Content{%d}",i));

            questionDao.addQuestion(question);
        }

        User user = userDao.selectById(2);
        System.out.println(user.getName());
        //userDao.deleteById(1);

        //System.out.println(questionDao.selectLatestQuestions(0,0,5));
    }

}
