package cn.omsfuk.servlet;

import cn.omsfuk.dao.UserDao;
import cn.omsfuk.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;

/**
 * Created by Administrator on 2017/3/30.
 */
public class LoginServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 默认转发
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(username == null && password == null) {
            RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/login.jsp");
            view.forward(req, resp);
            return ;
        }

        PrintWriter out = resp.getWriter();
        User user = userDao.getUserByUsername(username);

        // 只提交用户名，判断用户是否存在
        if(username != null && password == null) {
            if(user != null) {
                out.write("success");
                return ;
            }
        }

        // 用户登录，添加session和cookie
        if(user != null) {
            if(user.getPassword().equals(password)) {

                req.setAttribute("user", user);

                Cookie cookieUsername = new Cookie("username",user.getUsername());
                Cookie cookieUserid = new Cookie("userid",user.getUuid());

                resp.addCookie(cookieUsername);
                resp.addCookie(cookieUserid);

                HttpSession session = req.getSession();
                user.setPassword("");
                session.setAttribute("user", user);

                out.write("success");
                return ;
            } else {
                out.write("failure");
                return ;
            }
        }

    }
}
