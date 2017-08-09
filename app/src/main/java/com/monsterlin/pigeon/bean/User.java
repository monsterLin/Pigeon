package com.monsterlin.pigeon.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/10
 * @desc : 用户实体类
 */
public class User extends BmobUser {
    //默认含有的字段为；username ， password , email , sessionToken , mobilePhoneNumber

    private String nick ; //昵称
    private int age ; //用户年龄
    private int type ; // 0 : 子女 & 1 ：父母
    private Boolean isCreate ;  //创建家庭
    private Boolean isJoin ;  //加入家庭
    private Family family ;  //加入的家庭
    private BmobFile userPhoto ; //用户头像
    private String location ; // 地理位置

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(Boolean create) {
        isCreate = create;
    }

    public Boolean getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Boolean join) {
        isJoin = join;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public BmobFile getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(BmobFile userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
