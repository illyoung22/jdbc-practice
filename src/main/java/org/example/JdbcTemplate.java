package org.example;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    public void executeUpdate(User user, String sql, PreparedStatementSetter setter) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectionManager.getConnection();;
            pstmt = conn.prepareStatement(sql);
            setter.setter(pstmt);
//            pstmt.setString(1,user.getUserId());
//            pstmt.setString(2,user.getPassword());
//            pstmt.setString(3,user.getName());
//            pstmt.setString(4,user.getEmail());
            pstmt.executeUpdate();
        }finally {
            if (pstmt != null){
                pstmt.close();
            }

            if (conn != null){
                conn.close();
            }
        }
    }

    public Object executeQuery( String sql, PreparedStatementSetter setter, RowMapper rowMapper) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            //String sql = "SELECT userId, password,name,email FROM USERS WHERE userId =?";
            pstmt = conn.prepareStatement(sql);
            setter.setter(pstmt);
//            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();
            Object user = null;
            if (rs.next()) {
//                user = new User(
//                        rs.getString("userId"),
//                        rs.getString("password"),
//                        rs.getString("name"),
//                        rs.getString("email")
//                );
                return rowMapper.map(rs);
            }
            return user;
        }finally {
            if (rs != null) {
                rs.close();
            }

            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }
}
