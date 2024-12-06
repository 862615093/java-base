package com.ww.designPatterd.adapter;

import lombok.Data;

@Data
public class ResultMsg {
    private Integer code;
    private String msg;
    private Object data;

    public ResultMsg(Integer code, String msg, Object data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}