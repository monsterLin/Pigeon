package com.monsterlin.pigeon.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/10
 * @desc : 聊天实体类
 */
public class ChatMessage implements Serializable {
    private String name;  //姓名
    private String msg;  //消息
    private Type type; //类型 ：主要用于区分发送者与接受者
    private Date date; //时间

    public enum Type
    {
        INCOMING, OUTCOMING
    }

    public ChatMessage() {

    }

    public ChatMessage(String msg, Type type, Date date) {
        super();
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
