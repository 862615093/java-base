package com.ww.bytebuddyStudy;

import java.util.UUID;

/**
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
public class UserManager {

    public UserManager() {
        System.out.println("UserManager的构造方法");
    }

    public String selectUserName(Long id) {
        return "用户id:"+ id +"的名字:" + UUID.randomUUID().toString();
    }
    public void print() {
        System.out.println(1);
    }
    public int selectAge() {
        return 33;
    }
}
