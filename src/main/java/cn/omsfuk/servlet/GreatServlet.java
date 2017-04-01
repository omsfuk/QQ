package cn.omsfuk.servlet;

import cn.omsfuk.dao.GreatDao;
import cn.omsfuk.dao.SayDao;
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
public class GreatServlet extends HttpServlet {

    private SayDao sayDao = new SayDao();

    private GreatDao greatDao = new GreatDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String sayid = req.getParameter("id");
        if(sayid == null) {
            writer.write("failure");
            return ;
        }
        User user = (User) req.getSession().getAttribute("user");
        boolean isGreat = sayDao.isGreat(sayid, user.getUuid());

        if(isGreat) {
            greatDao.deleteGreat(sayid, user.getUuid());
        } else {
            greatDao.addGreat(sayid, user.getUuid());
        }

        writer.write("success");
    }
}
