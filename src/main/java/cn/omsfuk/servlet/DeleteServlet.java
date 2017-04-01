package cn.omsfuk.servlet;

import cn.omsfuk.dao.SayDao;
import cn.omsfuk.dao.UserDao;
import cn.omsfuk.domain.Say;
import cn.omsfuk.domain.User;
import cn.omsfuk.entity.Node;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
public class DeleteServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    private SayDao sayDao = new SayDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        PrintWriter writer = resp.getWriter();

        if(id == null) {
            writer.write("failure");
            return ;
        }

        String username = ((User) req.getSession().getAttribute("user")).getUsername();
        Say say = sayDao.getSayByUuid(id);
        if(say != null && say.getUsername().equals(username)) {
            Node node = sayDao.getSaysByRootid(id, say.getUuid());
            List<String> uuids = new LinkedList<String>();
            handle(uuids, node);
            uuids.add(id);
            sayDao.deleteSayByUuids(uuids);
        } else {
            writer.write("failure");
            return ;
        }

        writer.write("success");
    }

    private void handle(List<String> uuids, Node node) {
        if(node.getChild() == null) {
            return ;
        }

        List<Node> nodes = node.getChild();
        for (int i = 0; i < nodes.size(); i++) {
            uuids.add(nodes.get(i).getSay().getUuid());
            handle(uuids, nodes.get(i));
        }
    }
}
