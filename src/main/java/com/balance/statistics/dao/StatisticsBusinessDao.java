package com.balance.statistics.dao;

import com.balance.customer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by liukai on 2018/3/20.
 */
@Repository
public class StatisticsBusinessDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询所有业务员
     * @return
     */
    public List<User> findUserList(){
        String sql="select * from t_sys_user t where t.role_id=2";
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user=new User();
                user.setId(rs.getInt("id"));
                user.setRealname(rs.getString("realname"));
                return user;
            }
        });
    }
}
