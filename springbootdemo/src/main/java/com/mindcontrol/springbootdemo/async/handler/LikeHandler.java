package com.mindcontrol.springbootdemo.async.handler;

import com.mindcontrol.springbootdemo.async.EventHandler;
import com.mindcontrol.springbootdemo.async.EventModel;
import com.mindcontrol.springbootdemo.async.EventType;
import com.mindcontrol.springbootdemo.model.Message;
import com.mindcontrol.springbootdemo.model.User;
import com.mindcontrol.springbootdemo.service.MessageService;
import com.mindcontrol.springbootdemo.service.UserService;
import com.mindcontrol.springbootdemo.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName() + "赞了你的评论，http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
