package cn.omsfuk.domain;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Say {

    private String uuid;

    private String puuid;

    private String username;

    private String content;

    private boolean hasGreat;

    private int greatNum;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGreatNum() {
        return greatNum;
    }

    public void setGreatNum(int greatNum) {
        this.greatNum = greatNum;
    }

    public boolean isHasGreat() {
        return hasGreat;
    }

    public void setHasGreat(boolean hasGreat) {
        this.hasGreat = hasGreat;
    }
}
