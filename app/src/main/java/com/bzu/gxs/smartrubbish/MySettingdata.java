package com.bzu.gxs.smartrubbish;

public class MySettingdata {
    private String name;
    private int imageId;
    private String switch1;//开关（闹钟）
    public MySettingdata(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getSwitch1() {
        return switch1;
    }
}
