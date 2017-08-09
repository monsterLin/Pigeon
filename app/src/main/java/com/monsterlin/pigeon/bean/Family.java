package com.monsterlin.pigeon.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/7/11
 * @desc : 家庭
 */
public class Family extends BmobObject{

    //一个家庭含有一个创建者和多个成员，属于一对多的关系

    private String familyName ; //家庭名
    private User familyCreator ; //创建者
    private BmobFile familyIcon ; //家庭头像

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public User getFamilyCreator() {
        return familyCreator;
    }

    public void setFamilyCreator(User familyCreator) {
        this.familyCreator = familyCreator;
    }

    public BmobFile getFamilyIcon() {
        return familyIcon;
    }

    public void setFamilyIcon(BmobFile familyIcon) {
        this.familyIcon = familyIcon;
    }
}
