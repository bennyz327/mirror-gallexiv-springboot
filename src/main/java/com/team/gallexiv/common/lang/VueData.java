package com.team.gallexiv.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class VueData implements Serializable {

    //包裝返回結果
    //預設success的code是200
    //預設error的code是400

    private Integer code;
    private String msg;
    private Object data;

    public static VueData ok(Object data) {
        VueData result = new VueData();
        result.setCode(200);
        result.setMsg(Const.DEFAULT_SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static VueData ok() {
        VueData result = new VueData();
        result.setCode(200);
        result.setMsg(Const.DEFAULT_SUCCESS_MSG);
        return result;
    }

    public static VueData ok(String msg) {
        VueData result = new VueData();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    public static VueData ok(String msg,Object data) {
        VueData result = new VueData();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static VueData error(String msg) {
        VueData result = new VueData();
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }

    public static VueData error(Integer code, String msg) {
        VueData result = new VueData();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
