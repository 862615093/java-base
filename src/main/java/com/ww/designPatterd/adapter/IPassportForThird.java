package com.ww.designPatterd.adapter;

public interface IPassportForThird {
    ResultMsg loginForTel(String telephone, String code);

    ResultMsg loginForWechat(String id);

    ResultMsg loginForResist(String userName, String passWord);
}