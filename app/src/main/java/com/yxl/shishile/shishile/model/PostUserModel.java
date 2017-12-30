package com.yxl.shishile.shishile.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class PostUserModel extends BaseModel {

    /**
     * data : {"uid":689,"isDelete":0,"enable":1,"parentId":0,"parents":"","admin":0,
     * "username":"client","password":"2ff285246d9c95d3ffac32cc8f9cddbb","coinPassword":"",
     * "type":0,"nickname":"client","name":"client","regIP":"","regTime":1514424548,
     * "updateTime":"0000-00-00 00:00:00","avatar":"/img/avatar/default01.png","grade":1,
     * "score":0,"scoreTotal":0,"coin":0,"fcoin":0,"fanDian":13,"fanDianBdw":0,"sb":0,"care":"",
     * "qq":"","conCommStatus":0,"lossCommStatus":0,"xuni":null,"deposit":0,"depositStatus":0,
     * "free":0,"group":0,"link_id":0,"testuser":0,"regFrom":1,
     * "token":"2a9e37d72dc34a016666975485518e89ff7ff59a"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * uid : 689
         * isDelete : 0
         * enable : 1
         * parentId : 0
         * parents :
         * admin : 0
         * username : client
         * password : 2ff285246d9c95d3ffac32cc8f9cddbb
         * coinPassword :
         * type : 0
         * nickname : client
         * name : client
         * regIP :
         * regTime : 1514424548
         * updateTime : 0000-00-00 00:00:00
         * avatar : /img/avatar/default01.png
         * grade : 1
         * score : 0
         * scoreTotal : 0
         * coin : 0
         * fcoin : 0
         * fanDian : 13
         * fanDianBdw : 0
         * sb : 0
         * care :
         * qq :
         * conCommStatus : 0
         * lossCommStatus : 0
         * xuni : null
         * deposit : 0
         * depositStatus : 0
         * free : 0
         * group : 0
         * link_id : 0
         * testuser : 0
         * regFrom : 1
         * token : 2a9e37d72dc34a016666975485518e89ff7ff59a
         */

        private int uid;
        private int isDelete;
        private int enable;
        private int parentId;
        private String parents;
        private int admin;
        private String username;
        private String password;
        private String coinPassword;
        private int type;
        private String nickname;
        private String name;
        private String regIP;
        private int regTime;
        private String updateTime;
        private String avatar;
        private int grade;
        private int score;
        private int scoreTotal;
        private float coin;
        private float fcoin;
        private float fanDian;
        private float fanDianBdw;
        private int sb;
        private String care;
        private String qq;
        private int conCommStatus;
        private int lossCommStatus;
        private Object xuni;
        private float deposit;
        private int depositStatus;
        private int free;
        private int group;
        private int link_id;
        private int testuser;
        private int regFrom;
        private String token;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getParents() {
            return parents;
        }

        public void setParents(String parents) {
            this.parents = parents;
        }

        public int getAdmin() {
            return admin;
        }

        public void setAdmin(int admin) {
            this.admin = admin;
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

        public String getCoinPassword() {
            return coinPassword;
        }

        public void setCoinPassword(String coinPassword) {
            this.coinPassword = coinPassword;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegIP() {
            return regIP;
        }

        public void setRegIP(String regIP) {
            this.regIP = regIP;
        }

        public int getRegTime() {
            return regTime;
        }

        public void setRegTime(int regTime) {
            this.regTime = regTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScoreTotal() {
            return scoreTotal;
        }

        public void setScoreTotal(int scoreTotal) {
            this.scoreTotal = scoreTotal;
        }

        public float getCoin() {
            return coin;
        }

        public void setCoin(float coin) {
            this.coin = coin;
        }

        public float getFcoin() {
            return fcoin;
        }

        public void setFcoin(float fcoin) {
            this.fcoin = fcoin;
        }

        public float getFanDian() {
            return fanDian;
        }

        public void setFanDian(float fanDian) {
            this.fanDian = fanDian;
        }

        public float getFanDianBdw() {
            return fanDianBdw;
        }

        public void setFanDianBdw(float fanDianBdw) {
            this.fanDianBdw = fanDianBdw;
        }

        public int getSb() {
            return sb;
        }

        public void setSb(int sb) {
            this.sb = sb;
        }

        public String getCare() {
            return care;
        }

        public void setCare(String care) {
            this.care = care;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public int getConCommStatus() {
            return conCommStatus;
        }

        public void setConCommStatus(int conCommStatus) {
            this.conCommStatus = conCommStatus;
        }

        public int getLossCommStatus() {
            return lossCommStatus;
        }

        public void setLossCommStatus(int lossCommStatus) {
            this.lossCommStatus = lossCommStatus;
        }

        public Object getXuni() {
            return xuni;
        }

        public void setXuni(Object xuni) {
            this.xuni = xuni;
        }

        public float getDeposit() {
            return deposit;
        }

        public void setDeposit(int deposit) {
            this.deposit = deposit;
        }

        public int getDepositStatus() {
            return depositStatus;
        }

        public void setDepositStatus(int depositStatus) {
            this.depositStatus = depositStatus;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }

        public int getLink_id() {
            return link_id;
        }

        public void setLink_id(int link_id) {
            this.link_id = link_id;
        }

        public int getTestuser() {
            return testuser;
        }

        public void setTestuser(int testuser) {
            this.testuser = testuser;
        }

        public int getRegFrom() {
            return regFrom;
        }

        public void setRegFrom(int regFrom) {
            this.regFrom = regFrom;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
