package com.mindcontrol.springbootdemo.async;

import java.util.List;

//专门处理这些事件
public interface EventHandler {
    void doHandle(EventModel model);

    //记录关注的事件,注册自己
    List<EventType> getSupportEventTypes();
}
