package cn.omsfuk.util;

/**
 * Created by Administrator on 2017/4/2.
 */
public class XssUtil {
    public static String filter(String s) {
        return s.replace("<", "&lt;").replace(">", "&gt;");
    }
}
