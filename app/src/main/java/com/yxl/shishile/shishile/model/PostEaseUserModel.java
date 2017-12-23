package com.yxl.shishile.shishile.model;

/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class PostEaseUserModel extends BaseModel {

    /**
     * data : {"uid":1,"username":"yk4663018524","password":"c27814001ccd1a259474eea327c5068e",
     * "type":"1","imei":"xxxxxxxxxx","hx_password":"youke123","createtime":1514010108,
     * "updatetime":1514010108,"logintime":0,"type_text":"Type 1","logintime_text":"1970-01-01
     * 08:00:00"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uid : 1
         * username : yk4663018524
         * password : c27814001ccd1a259474eea327c5068e
         * type : 1
         * imei : xxxxxxxxxx
         * hx_password : youke123
         * createtime : 1514010108
         * updatetime : 1514010108
         * logintime : 0
         * type_text : Type 1
         * logintime_text : 1970-01-01 08:00:00
         */

        private int uid;
        private String username;
        private String password;
        private String type;
        private String imei;
        private String hx_password;
        private int createtime;
        private int updatetime;
        private int logintime;
        private String type_text;
        private String logintime_text;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getHx_password() {
            return hx_password;
        }

        public void setHx_password(String hx_password) {
            this.hx_password = hx_password;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }

        public int getLogintime() {
            return logintime;
        }

        public void setLogintime(int logintime) {
            this.logintime = logintime;
        }

        public String getType_text() {
            return type_text;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public String getLogintime_text() {
            return logintime_text;
        }

        public void setLogintime_text(String logintime_text) {
            this.logintime_text = logintime_text;
        }
    }
}
