package cn.omsfuk.domain;

/**
 * Created by Administrator on 2017/3/31.
 */
public class Friend {

    private String uuid;

    /**
     * 0 表示等待验证
     */
    private int state;

    private String user1;

    private String user2;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }
}
