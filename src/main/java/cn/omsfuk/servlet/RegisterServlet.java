package cn.omsfuk.servlet;

import cn.omsfuk.dao.UserDao;
import cn.omsfuk.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/30.
 */
public class RegisterServlet extends HttpServlet{

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rightVerifyCode = (String) req.getSession().getAttribute("verCode");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");
        String verifyCode = req.getParameter("verifyCode");
        int age = Integer.MIN_VALUE;
        if(req.getParameter("age") != null) {
            age = Integer.parseInt(req.getParameter("age"));
        }

        PrintWriter writer = resp.getWriter();

        if(username == null) {
            RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/register.jsp");
            view.forward(req, resp);
            return ;
        }

        if(verifyCode == null) {
            if(userDao.getUserByUsername(username) != null) {
                writer.write("用户名已存在");
                return;
            } else {
                writer.write("success");
                return;
            }
        } else {

            if(password == null || gender == null || age == Integer.MIN_VALUE || verifyCode == null) {

                RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/register.jsp");
                view.forward(req, resp);
                return ;
            }

            if((!verifyCode.toLowerCase().equals(rightVerifyCode))) {
                writer.write("验证码错误");
                return ;
            }
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setGender(gender);
        user.setAge(age);

        userDao.addUser(user);

        String realPath = req.getServletContext().getRealPath("/");
        File file = new File(realPath + "/users/" + username + "/");

        if(!file.mkdirs()) {
            System.out.println(username + "创建用户文件夹失败");
        }

        writer.write("success");
    }

}
