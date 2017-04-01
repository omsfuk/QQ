package cn.omsfuk.servlet;

import cn.omsfuk.dao.SayDao;
import cn.omsfuk.dao.UserDao;
import cn.omsfuk.domain.Say;
import cn.omsfuk.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/30.
 */
public class WriteServlet extends HttpServlet {

    private SayDao sayDao = new SayDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String content = req.getParameter("content");
        String puuid = req.getParameter("id");
        if(content == null) {
            RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/write.jsp");
            view.forward(req, resp);
            return ;
        }


        Say say = new Say();
        User user = (User) req.getSession().getAttribute("user");
        say.setUsername(user.getUsername());
        say.setContent(content);
        say.setPuuid(puuid);

        String uuid = sayDao.addSay(say);

        PrintWriter writer = resp.getWriter();
        if(uuid == null) {
            uuid = "failure";
        }

        writer.write(uuid);
    }
}
