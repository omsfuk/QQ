package cn.omsfuk.servlet;

import cn.omsfuk.dao.FriendDao;
import cn.omsfuk.domain.User;
import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class IndexServlet extends HttpServlet {

    private FriendDao friendDao = new FriendDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userid = ((User) req.getSession().getAttribute("user")).getUuid();
        List<String> friends = friendDao.getFriends(userid);
        List<String> ingFriends = friendDao.getIngFriends(userid);

        req.setAttribute("friends", friends);
        req.setAttribute("ingFriends", ingFriends);
        RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/index.jsp");
        view.forward(req, resp);
    }
}
