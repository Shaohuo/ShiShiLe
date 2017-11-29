package com.yxl.shishile.shishile.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/25 0025.
 */

public class historyBean {
    private List<MenuitemBean> menuitem;

    public List<MenuitemBean> getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(List<MenuitemBean> menuitem) {
        this.menuitem = menuitem;
    }

    public static class MenuitemBean {

        private String number;

        private String data;

        private String time;

        public void setNumber(String number){
            this.number = number;
        }
        public String getNumber(){
            return this.number;
        }
        public void setData(String data){
            this.data = data;
        }
        public String getData(){
            return this.data;
        }
        public void setTime(String time){
            this.time = time;
        }
        public String getTime(){
            return this.time;
        }
    }
}
