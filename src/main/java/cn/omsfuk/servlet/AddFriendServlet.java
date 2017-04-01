package cn.omsfuk.servlet;

import cn.omsfuk.dao.FriendDao;
import cn.omsfuk.domain.Friend;
import cn.omsfuk.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/31.
 */
public class AddFriendServlet extends HttpServlet {

    private FriendDao friendDao = new FriendDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String friendName = req.getParameter("friendName");
        PrintWriter writer = resp.getWriter();

        if(friendName == null) {
            writer.write("failure");
            return ;
        }

        String username = ((User) req.getSession().getAttribute("user")).getUsername();
        Friend friend = new Friend();
        friend.setUser1(username);
        friend.setUser2(friendName);

        if(friendDao.isFriend(friend)) {
            writer.write("此用户已在您的好友列表中或正在等待验证");
            return ;
        }

        friendDao.addFriend(friend);

        writer.write("success");
    }
}
