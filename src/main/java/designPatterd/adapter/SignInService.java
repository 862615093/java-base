package designPatterd.adapter;

public class SignInService {
    public ResultMsg regist(String userName, String passWord) {
        return new ResultMsg(200, "注册成功", new Member());
    }

    public ResultMsg login(String userName, String passWord) {
        return new ResultMsg(200, "老系统登录", new Member(userName, passWord, null, null));
    }
}