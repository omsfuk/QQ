package cn.omsfuk.dao;

import cn.omsfuk.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/30.
 */
public class GreatDao {

    public void addGreat(String sayid, String userid) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtil.getConn();
            String sql = "insert into greats(uuid, sayid, userid) values(UUID(), ?, ?)";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, sayid);
            pstmt.setString(2, userid);
            DbUtil.update(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
    }

    public void deleteGreat(String sayid, String userid) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtil.getConn();
            String sql = "delete from greats where sayid = ? and userid = ?";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, sayid);
            pstmt.setString(2, userid);
            DbUtil.update(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
    }

}
