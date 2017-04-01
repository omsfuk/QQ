package cn.omsfuk.servlet;

import cn.omsfuk.dao.FriendDao;
import cn.omsfuk.dao.SayDao;
import cn.omsfuk.domain.Say;
import cn.omsfuk.domain.User;
import cn.omsfuk.entity.Node;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class CommentServlet extends HttpServlet {

    private FriendDao friendDao = new FriendDao();

    private SayDao sayDao = new SayDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userid = ((User) req.getSession().getAttribute("user")).getUuid();
        Node node = sayDao.getSaysByRootid(null, userid);
        req.setAttribute("node", node);

        RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/comment.jsp");
        view.forward(req, resp);
    }

}
