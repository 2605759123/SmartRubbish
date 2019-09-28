package com.bzu.gxs.smartrubbish.utils;

/**
 * Created by mcz on 2018/1/8.
 */

public class Config {

    public static String SELFCERTPATH="config/outgoing.CertwithKey.pkcs12";
    public static String TRUSTCAPATH="config/ca.bks";
    public static String SELFCERTPWD="IoM@1234";
    public static String TRUSTCAPWD="123456";

    public static String all_url="";
    public static String push_url="http://123.206.131.251:83/";

    //手机格式验证
    public static final String phoneFormat = "^((14[0-9])|(13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";



}
