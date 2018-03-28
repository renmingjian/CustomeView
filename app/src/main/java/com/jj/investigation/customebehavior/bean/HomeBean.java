package com.jj.investigation.customebehavior.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by ${R.js} on 2018/3/21.
 */

public class HomeBean implements Serializable {

    private List<HomeSubBean> lsit;
    private List<String> topList;

    public List<HomeSubBean> getLsit() {
        return lsit;
    }

    public void setLsit(List<HomeSubBean> lsit) {
        this.lsit = lsit;
    }

    public List<String> getTopList() {
        return topList;
    }

    public void setTopList(List<String> topList) {
        this.topList = topList;
    }

    public class HomeSubBean implements Serializable {
        private String desc;
        private int type;
        private List<String> stringList;


        public List<String> getStringList() {
            return stringList;
        }

        public void setStringList(List<String> stringList) {
            this.stringList = stringList;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

}
