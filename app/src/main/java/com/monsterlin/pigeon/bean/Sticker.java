package com.monsterlin.pigeon.bean;

import cn.bmob.v3.BmobObject;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/8
 * @desc : 亲情贴
 */
public class Sticker extends BmobObject {
    private String content ;
    private Family family ; //所属家庭
    private User user ;  //作者

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
