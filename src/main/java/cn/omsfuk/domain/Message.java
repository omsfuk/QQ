package cn.omsfuk.domain;

/**
 * Created by Administrator on 2017/4/1.
 */
public class Message {

    public String from;

    public String to;

    public String cont;

    public Message(String from, String to, String cont) {
        this.from = from;
        this.to = to;
        this.cont = cont;
    }
}
