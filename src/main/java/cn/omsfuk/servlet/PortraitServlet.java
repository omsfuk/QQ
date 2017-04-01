package cn.omsfuk.servlet;

import cn.omsfuk.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by Administrator on 2017/3/31.
 */

@MultipartConfig
public class PortraitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getContentType() == null) {
            RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/modpor.jsp");
            view.forward(req, resp);
            return;
        }

        Part part = req.getPart("file");
        String username = ((User) req.getSession().getAttribute("user")).getUsername();
        resp.setContentType("text/html;charset=utf-8");
        String savePath = req.getServletContext().getRealPath("/users/" + username);


        String fileName = "portrait.png";

        part.write(savePath + File.separator + fileName);

        RequestDispatcher view = req.getRequestDispatcher("WEB-INF/jsp/success.jsp");
        view.forward(req, resp);
    }
}
