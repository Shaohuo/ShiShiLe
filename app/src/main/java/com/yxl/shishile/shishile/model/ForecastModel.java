package com.yxl.shishile.shishile.model;

import java.util.List;

/**
 * Created by huangqianwen on 2017/12/18.
 */

public class ForecastModel
{

    /**
     * data : {"result":["4,9,8,5,3","5,4,1","37,30,39,22,43,25,27","5,8,4,7,3","8,3,5","4,3,3","4,8,5,7,8","2,5,4","1,7,8,2,10","8,3,5,2,6,7,10,4,1,9","4,8,7,11,3"]}
     * code : 200
     * message : 全部彩种预测结果
     */

    private DataBean data;
    private int code;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        private List<String> result;

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
    }
}
