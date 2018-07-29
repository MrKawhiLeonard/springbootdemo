package com.mindcontrol.springbootdemo.async;

import java.util.HashMap;
import java.util.Map;

//事件发生的现场
public class EventModel {
    private EventType type;
    private int actorId;    //触发者
    private int entityType;
    private int entityId;
    private int entityOwnerId;

    private Map<String,String> exts = new HashMap<>();

    public EventModel(){

    }

    public EventModel(EventType eventType){
        this.type = eventType;
    }

    public EventModel setExt(String key,String value){
        exts.put(key, value);
        return this;
    }

    public String getExt(String key){
        return exts.get(key);
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
        //为了方便使用，可以连续调用，例如：XX.setType().setXX().setXX()
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
