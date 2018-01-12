package com.yxl.shishile.shishile.model;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class UploadResultModel extends BaseModel {


    /**
     * msg : 上传成功
     * data : {"url":"/uploads/20180110/06e7fefad3cd3eff44059a1a18633747.gif"}
     * url : http://103.242.1.48:81/admin/general/attachment/add?dialog=1
     * wait : 3
     */

    private String msg;
    private DataBean data;
    private String url;
    private int wait;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public static class DataBean {
        /**
         * url : /uploads/20180110/06e7fefad3cd3eff44059a1a18633747.gif
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "UploadResultModel{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", url='" + url + '\'' +
                ", wait=" + wait +
                '}';
    }
}
