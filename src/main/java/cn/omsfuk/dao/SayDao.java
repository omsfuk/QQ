package cn.omsfuk.dao;

import cn.omsfuk.domain.Say;
import cn.omsfuk.entity.Node;
import cn.omsfuk.util.DbUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class SayDao {


    public List<Say> getSaysByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Say> says = new LinkedList<Say>();
        try {
            conn = DbUtil.getConn();
            String sql = "select * from says where username = ?)";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, username);
            rs = DbUtil.executeQuery(pstmt);
            Say say = null;
            while(rs.next()) {
                say = new Say();
                say.setUuid(rs.getString("uuid"));
                say.setUsername(rs.getString("username"));
                say.setPuuid(rs.getString("puuid"));
                say.setContent(rs.getString("content"));
                says.add(say);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(conn);
            DbUtil.close(pstmt);
        }

        return says;
    }

    public String addSay(Say say) {
        String uuid = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();
            String sql = "select uuid() as uuid";
            pstmt = DbUtil.preparedStatement(conn, sql);
            rs = DbUtil.executeQuery(pstmt);
            rs.next();
            uuid = rs.getString("uuid");

            DbUtil.close(rs);
            DbUtil.close(pstmt);

            sql = "insert says(uuid, puuid, date, username, content) values(?, ?, now(), ?, ?)";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, uuid);
            pstmt.setString(2, say.getPuuid());
            pstmt.setString(3, say.getUsername());
            pstmt.setString(4, say.getContent());
            DbUtil.update(pstmt);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

        return uuid;
    }

    public List<Say> getSaysByPuuid(String puuid) {
        List<Say> says = new LinkedList<Say>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();
            String sql = null;
            if(puuid != null) {
                sql = "select * from says where puuid = ? order by date asc";
                pstmt = DbUtil.preparedStatement(conn, sql);
                pstmt.setString(1, puuid);
            } else {
                sql = "select * from says where puuid is null order by date asc";
                pstmt = DbUtil.preparedStatement(conn, sql);
            }

            rs = DbUtil.executeQuery(pstmt);

            while(rs.next()) {
                Say say = new Say();

                say.setUuid(rs.getString("uuid"));
                say.setPuuid(rs.getString("puuid"));
                say.setUsername(rs.getString("username"));
                say.setContent(rs.getString("content"));

                says.add(say);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

        return says;
    }

    public Say getSayByUuid(String uuid) {
        Say say = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();
            String sql = "select * from says where uuid = ? order by date asc";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, uuid);
            rs = DbUtil.executeQuery(pstmt);
            if(rs.next()) {
                say = new Say();
                say.setUuid(uuid);
                say.setPuuid(rs.getString("puuid"));
                say.setUsername(rs.getString("username"));
                say.setContent(rs.getString("content"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

        return say;
    }

    public int getGreatNumBySayid(String sayid) {
        int count = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();
            String sql = "select count(*) as count from greats where sayid = ?";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, sayid);
            rs = DbUtil.executeQuery(pstmt);

            rs.next();
            count = rs.getInt("count");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

        return count;
    }

    public void deleteSayByUuids(List<String> uuids) {
        GreatDao greatDao = new GreatDao();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);

            // 使用buidler而不是buffer，反正线程安全
            StringBuilder sb = new StringBuilder("delete from greats where sayid in ( ");
            for (int i = 0; i < uuids.size() - 1; i++) {
                sb.append("?, ");
            }
            sb.append("? )");
            String sql = sb.toString();
            pstmt = DbUtil.preparedStatement(conn, sql);
            for (int i = 0; i < uuids.size(); i++) {
                pstmt.setString(i + 1, uuids.get(i));
            }

            DbUtil.update(pstmt);


            sb = new StringBuilder("delete from says where uuid in ( ");
            for (int i = 0; i < uuids.size() - 1; i++) {
                sb.append("?, ");
            }
            sb.append("? )");
            sql = sb.toString();
            pstmt = DbUtil.preparedStatement(conn, sql);
            for (int i = 0; i < uuids.size(); i++) {
                pstmt.setString(i + 1, uuids.get(i));
            }

            DbUtil.update(pstmt);
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                if(conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

    }

    public Node getSaysByRootid(String rootid, String userid) {
        Node node = new Node();
        Say say = new Say();
        say.setUuid(rootid);
        node.setSay(say);
        deal(node, userid);

        return node;
    }

    public boolean isGreat(String sayid, String userid) {

        boolean hasGreat = false;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConn();
            String sql = "select count(*) as count from greats where sayid = ? and userid = ?";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, sayid);
            pstmt.setString(2, userid);
            rs = DbUtil.executeQuery(pstmt);

            rs.next();
            int count = rs.getInt("count");
            if(count == 1) {
                hasGreat = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

        return hasGreat;
    }

    private void deal(Node node, String userid) {

        List<Say> says = getSaysByPuuid(node.getSay().getUuid());

        if(says.size() == 0) {
            return ;
        }

        List<Node> nodes = new LinkedList<Node>();

        for(Say say : says) {
            Node nod = new Node();
            say.setGreatNum(getGreatNumBySayid(say.getUuid()));
            say.setHasGreat(isGreat(say.getUuid(), userid));
            nod.setSay(say);
            deal(nod, userid);
            nodes.add(nod);
        }
        node.setChild(nodes);
    }

}
