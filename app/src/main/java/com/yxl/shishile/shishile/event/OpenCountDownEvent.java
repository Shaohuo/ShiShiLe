package com.yxl.shishile.shishile.event;

import com.yxl.shishile.shishile.model.Lottery;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class OpenCountDownEvent {
    private Lottery mLottery;

    public Lottery getLottery()
    {
        return mLottery;
    }

    public void setLottery(Lottery lottery) {
        this.mLottery = lottery;
    }
}
