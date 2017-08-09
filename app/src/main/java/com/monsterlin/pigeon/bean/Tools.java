package com.monsterlin.pigeon.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author : monsterLin
 * @version : 1.0
 * @email : monster941025@gmail.com
 * @github : https://github.com/monsterLin
 * @time : 2017/8/5
 * @desc : 发现板块
 */
public class Tools extends BmobObject{
    private BmobFile toolsIcon ;
    private String toolsTitle ;
    private String toolsUrl ;


    public BmobFile getToolsIcon() {
        return toolsIcon;
    }

    public void setToolsIcon(BmobFile toolsIcon) {
        this.toolsIcon = toolsIcon;
    }

    public String getToolsTitle() {
        return toolsTitle;
    }

    public void setToolsTitle(String toolsTitle) {
        this.toolsTitle = toolsTitle;
    }

    public String getToolsUrl() {
        return toolsUrl;
    }

    public void setToolsUrl(String toolsUrl) {
        this.toolsUrl = toolsUrl;
    }
}
