/**
 * Copyright 2017 bejson.com
 */
package com.monsterlin.pigeon.bean.news;

import java.util.List;

/**
 * Auto-generated: 2017-07-22 18:29:21
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result {

    private String stat;
    private List<Data> data;

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getStat() {
        return stat;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

}