package cn.omsfuk.dao;

import cn.omsfuk.domain.Friend;
import cn.omsfuk.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
public class FriendDao {

    public void addFriend(Friend friend) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {

            conn = DbUtil.getConn();
            conn.setAutoCommit(false);
            String sql = "insert into friends(uuid, state, user1, user2) values(uuid(), 0, (select uuid from users where username = ?), (select uuid from users where username = ?))";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, friend.getUser1());
            pstmt.setString(2, friend.getUser2());
            DbUtil.update(pstmt);

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
            try {
                if(conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
    }

    public void upgradeFriend(Friend friend) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);
            String sql = "update friends set state = 1 where user1 = (select uuid from users where username = ?) and user2 = (select uuid from users where username = ?)";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(2, friend.getUser1());
            pstmt.setString(1, friend.getUser2());
            DbUtil.update(pstmt);
            pstmt.close();

            sql = "insert into friends(uuid, state, user1, user2) values(uuid(), 1, (select uuid from users where username = ?), (select uuid from users where username = ?))";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, friend.getUser1());
            pstmt.setString(2, friend.getUser2());
            DbUtil.update(pstmt);
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
            try {
                if(conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
    }

    public void deleteFriend(Friend friend) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DbUtil.getConn();
            conn.setAutoCommit(false);
            String sql = "delete from friends where user1 = (select uuid from users where username = ?) and user2 = (select uuid from users where username = ?)";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, friend.getUser1());
            pstmt.setString(2, friend.getUser2());
            DbUtil.update(pstmt);

            pstmt.setString(2, friend.getUser1());
            pstmt.setString(1, friend.getUser2());
            DbUtil.update(pstmt);
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
            try {
                if(conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }
    }

    public List<String> getFriends(String userid) {

        List<String> friends = new LinkedList<String>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "select username from users where uuid in (select user2 from friends where user1 = ? and state = 1)";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, userid);
            rs = DbUtil.executeQuery(pstmt);

            while(rs.next()) {
                friends.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

        return friends;
    }

    public List<String> getIngFriends(String userid) {

        List<String> friends = new LinkedList<String>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "select username from users where uuid in (select user1 from friends where user2 = ? and state = 0)";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, userid);
            rs = DbUtil.executeQuery(pstmt);

            while(rs.next()) {
                friends.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

        return friends;
    }

    public boolean isFriend(Friend friend) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "select uuid from friends where " +
                    "(user1 = (select uuid from users where username = ?) and user2 = (select uuid from users where username = ?)) or " +
                    "(user2 = (select uuid from users where username = ?) and user1 = (select uuid from users where username = ?))";
            pstmt = DbUtil.preparedStatement(conn, sql);
            pstmt.setString(1, friend.getUser1());
            pstmt.setString(2, friend.getUser2());
            pstmt.setString(3, friend.getUser1());
            pstmt.setString(4, friend.getUser2());
            rs = DbUtil.executeQuery(pstmt);

            if(rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs);
            DbUtil.close(pstmt);
            DbUtil.close(conn);
        }

        return false;
    }

}
