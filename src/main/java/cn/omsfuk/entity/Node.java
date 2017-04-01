package cn.omsfuk.entity;

import cn.omsfuk.domain.Say;
import com.sun.deploy.security.BlacklistedCerts;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Node {

    private Say say;

    private List<Node> child;

    public Say getSay() {
        return say;
    }

    public void setSay(Say say) {
        this.say = say;
    }

    public List<Node> getChild() {
        return child;
    }

    public void setChild(List<Node> child) {
        this.child = child;
    }
}
