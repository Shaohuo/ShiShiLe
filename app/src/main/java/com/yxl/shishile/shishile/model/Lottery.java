package com.yxl.shishile.shishile.model;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Lottery {

    public  String number;

    public  String time;

    public String opentime;

    public  String data;

    @Override
    public String toString() {
        return "Lottery{" +
                "number=" + number +
                ", time=" + time +
                ", opentime=" + opentime +
                ", data='" + data + '\'' +
                '}';
    }
}
