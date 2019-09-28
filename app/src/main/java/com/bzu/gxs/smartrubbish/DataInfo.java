package com.bzu.gxs.smartrubbish;

import java.io.Serializable;

/**
 * Created by ning
 */

public class DataInfo implements Serializable{
    private String xiugaitime ;
    private String laiyuan;
    private String qingkuang;

    public String getXiugaitime() {
        return xiugaitime;
    }

    public String getLaiyuan() {
        return laiyuan;
    }

    public String getQingkuang() {
        return qingkuang;
    }

    public void setXiugaitime(String xiugaitime) {
        this.xiugaitime = xiugaitime;
    }

    public void setLaiyuan(String laiyuan) {
        this.laiyuan = laiyuan;
    }

    public void setQingkuang(String qingkuang) {
        this.qingkuang = qingkuang;
    }

    public DataInfo(String xiugaitime, String laiyuan, String qingkuang) {
        this.xiugaitime = xiugaitime;
        this.laiyuan = laiyuan;
        this.qingkuang = qingkuang;
    }

    public DataInfo() {
    }
}
