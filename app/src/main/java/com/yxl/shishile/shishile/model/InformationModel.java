package com.yxl.shishile.shishile.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class InformationModel {

    /**
     * data : [{"id":1,"title":"[小燕子]福彩3D第353期:缩减二十注推荐_网易彩票","type":"1","arttime":1514275056,"content":"  【独胆】:1【双胆】:1，7【六码复式】125789【缩...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 15:57:36","grabtime_text":"2017-12-26 18:35:02"},{"id":2,"title":"[乾坤]353期福彩3D推荐:组选大底号码推荐_网易彩票","type":"1","arttime":1514274996,"content":"  金胆7双胆2，7五码：23467组选：719,720,7...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 15:56:36","grabtime_text":"2017-12-26 18:35:02"},{"id":3,"title":"[老李背着手]3D第353期推荐:六码016789_网易彩票","type":"1","arttime":1514274955,"content":"  毒胆：★8★ ; 重点关注偶数，看好8双胆：★68★ 重...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 15:55:55","grabtime_text":"2017-12-26 18:35:02"},{"id":4,"title":"[旭日]第17353期福彩3D号码分析:胆码056_网易彩票","type":"1","arttime":1514268769,"content":"  福利彩票3D开奖结果第2017352期：8 5 5，大大...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 14:12:49","grabtime_text":"2017-12-26 18:35:02"},{"id":5,"title":"[伊斯林]福彩3D第2017353期预测:个位看好0 3 6_网易彩票","type":"1","arttime":1514259756,"content":"  福彩3D第17352期开奖号为：855，和值18，偶奇奇...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 11:42:36","grabtime_text":"2017-12-26 18:35:02"},{"id":6,"title":"[甲壳虫]福彩3D第2017353期预测:十位看好5 6 7_网易彩票","type":"1","arttime":1514259687,"content":"  福彩3D第17352期开奖结果为：855；奇偶形态：偶奇...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 11:41:27","grabtime_text":"2017-12-26 18:35:02"},{"id":7,"title":"[上善若水]福彩3D17353期:定位024/789/024_网易彩票","type":"1","arttime":1514254398,"content":"  定位：024/789/024杀码：1 8/0 5/5 9...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 10:13:18","grabtime_text":"2017-12-26 18:35:02"},{"id":8,"title":"[瑶冰魄]七乐彩第17152期预测:质区码走冷_网易彩票","type":"1","arttime":1514254068,"content":"  17151期七乐彩开奖结果为02 06 07 15 19...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 10:07:48","grabtime_text":"2017-12-26 18:35:02"},{"id":9,"title":"[潇潇飞雨]3D2017353期预测:和值振幅增大_网易彩票","type":"1","arttime":1514253924,"content":"  福彩3D第2017352期开奖号码855，重码没有露面，...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 10:05:24","grabtime_text":"2017-12-26 18:35:02"},{"id":10,"title":"[玩彩高手]福彩3D17353期预测:看好质区和_网易彩票","type":"1","arttime":1514253870,"content":"  福彩3D第2017352期开奖号为：855，和值为18点...","artfrom":"网易彩票","grabtime":1514284502,"type_text":"Type 1","arttime_text":"2017-12-26 10:04:30","grabtime_text":"2017-12-26 18:35:02"}]
     * code : 200
     * message : 文章列表
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : [小燕子]福彩3D第353期:缩减二十注推荐_网易彩票
         * type : 1
         * arttime : 1514275056
         * content :   【独胆】:1【双胆】:1，7【六码复式】125789【缩...
         * artfrom : 网易彩票
         * grabtime : 1514284502
         * type_text : Type 1
         * arttime_text : 2017-12-26 15:57:36
         * grabtime_text : 2017-12-26 18:35:02
         */

        private int id;
        private String title;
        private String type;
        private int arttime;
        private String content;
        private String artfrom;
        private int grabtime;
        private String type_text;
        private String arttime_text;
        private String grabtime_text;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getArttime() {
            return arttime;
        }

        public void setArttime(int arttime) {
            this.arttime = arttime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getArtfrom() {
            return artfrom;
        }

        public void setArtfrom(String artfrom) {
            this.artfrom = artfrom;
        }

        public int getGrabtime() {
            return grabtime;
        }

        public void setGrabtime(int grabtime) {
            this.grabtime = grabtime;
        }

        public String getType_text() {
            return type_text;
        }

        public void setType_text(String type_text) {
            this.type_text = type_text;
        }

        public String getArttime_text() {
            return arttime_text;
        }

        public void setArttime_text(String arttime_text) {
            this.arttime_text = arttime_text;
        }

        public String getGrabtime_text() {
            return grabtime_text;
        }

        public void setGrabtime_text(String grabtime_text) {
            this.grabtime_text = grabtime_text;
        }
    }
}
