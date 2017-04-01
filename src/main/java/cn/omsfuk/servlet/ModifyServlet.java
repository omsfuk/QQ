package cn.omsfuk.servlet;

import cn.omsfuk.dao.UserDao;
import cn.omsfuk.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/31.
 */
public class ModifyServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String gender = req.getParameter("gender");
        int age = 0;
        if (req.getParameter("age") != null) {
            age = Integer.parseInt(req.getParameter("age"));
        }

        if (password1 == null || password2 == null || gender == null || age == 0 ||
                (!password1.equals(password2))) {
            RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/modify.jsp");
            view.forward(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(((User) req.getSession().getAttribute("user")).getUsername());
        user.setPassword(password1);
        user.setGender(gender);
        user.setAge(age);

        userDao.updateUser(user);

        resp.sendRedirect("index");
    }
}
