package com.monsterlin.pigeon.bean;

import java.io.Serializable;

/**
 * @author : danry
 * @version : 1.0
 * @email : cdanry@163.com
 * @github : https://github.com/Danry-sky
 * @time : 2017/7/18
 * @desc : 单个Item类型
 */
public class ItemModel implements Serializable {

    public static final int CHAT_LEFT = 1001;
    public static final int CHAT_RIGHT = 1002;
    public int type;
    public Object object;

    public ItemModel(int type, Object object) {
        this.type = type;
        this.object = object;
    }
}
