package designPatterd.adapter;

public class PassportForThirdAdapter extends SignInService implements IPassportForThird {
    @Override
    public ResultMsg loginForTel(String telephone, String code) {
        return null;
    }

    @Override
    public ResultMsg loginForWechat(String id) {
        return null;
    }

    @Override
    public ResultMsg loginForResist(String userName, String passWord) {
        super.regist(userName, passWord);
        return super.login(userName, passWord);
    }


}