package designPatterd.adapter;

/**
 * 参考： https://www.cnblogs.com/xcgShare/p/12190642.html
 */
public class TestAdapter {

    //这里使用简单工厂及策略模式
    private static ResultMsg procssLogin(String key, Class<? extends LoginAdapter> clazz) {
        try {
            LoginAdapter adapter = clazz.newInstance();
            if (adapter.support(adapter)) {
                return adapter.login(key, adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ResultMsg msg = procssLogin("tel", LoginForTelAdapter.class);
        ResultMsg msg1 = procssLogin("wechat", LoginForWechatAdapter.class);
        System.out.println(msg);
        System.out.println(msg1);

    }
}
