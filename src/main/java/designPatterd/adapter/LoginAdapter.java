package designPatterd.adapter;

public interface LoginAdapter {

    boolean support(Object adapter);

    ResultMsg login(String id, Object adapter);
}