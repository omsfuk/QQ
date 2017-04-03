package cn.omsfuk.servlet;

import cn.omsfuk.domain.Message;
import cn.omsfuk.util.XssUtil;
import com.google.gson.Gson;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2017/3/31.
 */

@ServerEndpoint("/websocket")
public class ChatServlet {

    private static Gson gson = new Gson();

    // TODO 线程不安全。。。。。
    private static HashMap<String, Session> clients = new HashMap<String, Session>();

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            message = XssUtil.filter(message);
            Gson gson = new Gson();
            Message msg = gson.fromJson(message, Message.class);
            if (msg.from.length() <= 20 && !clients.containsKey(msg.from)) {
                clients.put(msg.from, session);
            } else {
                if (clients.containsKey(msg.to)) {
                    clients.get(msg.to).getBasicRemote().sendText(message);
                } else {
                    session.getBasicRemote().sendText(gson.toJson(new Message(msg.to, msg.from, "对方已下线")));
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 当一个新用户连接时所调用的方法
     * 该方法可能包含一个javax.websocket.Session可选参数
     * 如果有这个参数，容器将会把当前发送消息客户端的连接Session注入进去
     */
    @OnOpen
    public void onOpen(Session session) {

    }
    /** 当一个用户断开连接时所调用的方法*/
    @OnClose
    public void onClose(Session session) {
        Set<String> users = clients.keySet();
        Iterator<String> it = users.iterator();
        while(it.hasNext()) {
            String user = it.next();
            if(clients.get(user) == session) {
                clients.remove(user);
                break;
            }
        }
    }
}
