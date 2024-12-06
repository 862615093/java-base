package com.ww.designPatterd.adapter;

public class LoginForWechatAdapter implements LoginAdapter {

    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForWechatAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return new ResultMsg(200, "新增wechat系统登录", new Member(null, null, null, id));
    }
}