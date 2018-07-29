package com.mindcontrol.springbootdemo.controller;

import com.mindcontrol.springbootdemo.model.HostHolder;
import com.mindcontrol.springbootdemo.model.Message;
import com.mindcontrol.springbootdemo.model.User;
import com.mindcontrol.springbootdemo.model.ViewObject;
import com.mindcontrol.springbootdemo.service.MessageService;
import com.mindcontrol.springbootdemo.service.UserService;
import com.mindcontrol.springbootdemo.util.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @RequestMapping(path = {"/msg/addMessage"},method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){

        try {
            if(hostHolder.getUser() == null){
                return WendaUtil.getJSONString(999,"未登录");
            }

            User user = userService.selectByName(toName);
            if(user == null){
                return WendaUtil.getJSONString(1,"用户不存在");
            }

            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            messageService.addMessage(message);
            return WendaUtil.getJSONString(0);

        } catch (Exception e){
            logger.error("发送消息失败" + e.getMessage());
            return WendaUtil.getJSONString(1,"发信失败");
        }
    }

    @RequestMapping(path = {"/msg/list"},method = {RequestMethod.GET})
    public String conversationDetail(Model model){
        if(hostHolder.getUser() == null){
            return "redirect:/reglogin";
        }

        int localUserId = hostHolder.getUser().getId();
        List<ViewObject> conversations = new ArrayList<>();
        List<Message> conversationList = messageService.getConversationList(localUserId,0,10);
        for(Message msg : conversationList){
            ViewObject vo = new ViewObject();
            vo.set("message",msg);
            int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
            User user = userService.getUser(targetId);
            vo.set("user",user);
            vo.set("unread",messageService.getConversationUnreadCount(localUserId,msg.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations",conversations);

        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"},method = RequestMethod.GET)
    public String conversationDetail(Model model,
                                     @Param("conversationId") String conversationId){
        try{
            List<ViewObject> messages = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationDetail(conversationId,0,10);
            for(Message msg : conversationList){
                ViewObject vo = new ViewObject();
                vo.set("message",msg);
                vo.set("user",userService.getUser(msg.getFromId()));
                messages.add(vo);

                try{
                    messageService.updateHasRead(msg.getToId(),conversationId);

                }catch (Exception e){
                    logger.error("更改读状态失败" + e.getMessage());
                }
            }
            model.addAttribute("messages",messages);
        }catch (Exception e){
            logger.error("获取详情失败" + e.getMessage());
        }

        return "letterDetail";
    }
}
