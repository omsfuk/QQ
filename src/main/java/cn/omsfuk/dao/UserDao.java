package cn.omsfuk.dao;

import cn.omsfuk.domain.User;
import cn.omsfuk.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/30.
 */
public class UserDao {

    public User getUserByUsername(String username) {
        User user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "select * from users where username = ?";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, username);
            rs = DbUtil.executeQuery(pstmt);
            if(rs.next()) {
                user = new User();
                user.setUuid(rs.getString("uuid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setAge(rs.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
        return user;
    }

    public void addUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "insert into users(uuid, username, password, gender, age) values(UUID(), ?, ?, ?, ?)";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getGender());
            pstmt.setInt(4, user.getAge());
            DbUtil.update(pstmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
    }

    public void updateUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "update users set password = ?, gender = ?, age = ? where username = ?";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getGender());
            pstmt.setInt(3, user.getAge());
            pstmt.setString(4,user.getUsername());

            DbUtil.update(pstmt);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
    }



}
