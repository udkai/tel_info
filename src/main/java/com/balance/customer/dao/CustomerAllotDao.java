package com.balance.customer.dao;

import com.balance.customer.VO.CustomerAllotSearchVO;
import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.User;
import com.balance.util.page.PageUtil;
import com.balance.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liukai on 2017/10/18.
 */
@Repository
public class CustomerAllotDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询
     *
     * @param customerAllotSearchVO 查询条件
     * @return 客户信息列表
     */
    public List<CustomerInfo> search(CustomerAllotSearchVO customerAllotSearchVO, int pageIndex, int pageSize) {
        String sql = "select * from t_customer_info where 1=1  ";
        sql += createSql(customerAllotSearchVO);
        sql+=" order by status ";
        sql = PageUtil.createMysqlPageSql(sql, pageIndex, pageSize);
        SqlParameterSource params = new BeanPropertySqlParameterSource(customerAllotSearchVO);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedJdbc.query(sql, params, new BeanPropertyRowMapper(CustomerInfo.class));
    }

    /**
     * 统计查询数
     *
     * @param customerAllotSearchVO 查询条件
     * @return 数目
     */
    public int count(CustomerAllotSearchVO customerAllotSearchVO) {
        String sql = "select count(*) from t_customer_info where 1=1 ";
        sql += createSql(customerAllotSearchVO);
        SqlParameterSource params = new BeanPropertySqlParameterSource(customerAllotSearchVO);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedJdbc.queryForObject(sql, params, Integer.class);
    }

    private String createSql(CustomerAllotSearchVO customerAllotSearchVO) {
        String sql = "";
        if (StringUtil.isNotNullOrEmpty(customerAllotSearchVO.getCustomer_id_start())) {
            sql += " and id >= :customer_id_start";
        }
        if (StringUtil.isNotNullOrEmpty(customerAllotSearchVO.getCustomer_id_end())) {
            sql += " and id <= :customer_id_end";
        }
        if (customerAllotSearchVO.getStatus() != null) {
            sql += " and status =:status";
        }
        if(customerAllotSearchVO.getUser_id()!=null){
           sql+=" and user_id=:user_id";
        }
        return sql;
    }

    public List<User> getUser() {
        String sql = "select id,realname from t_sys_user t where t.role_id=2";
        List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        return list;
    }

    /**
     * 分配
     *
     * @param
     * @return 成功1 失败0 ;
     */
    public int saveAllot(String customer_id_start, String customer_id_end,int user_id, String username) {
        String sql = "UPDATE t_customer_info SET user_name=?, user_id= ? ,status=1  WHERE archive_status=0 and id  >=? and id<=?";
        return jdbcTemplate.update(sql, new Object[]{username, user_id,customer_id_start,customer_id_end});
    }

    /**
     *取消分发
     * @param customer_id_start
     * @param customer_id_end
     * @param realname
     * @return
     */
    public int saveCancel(String customer_id_start, String customer_id_end, String realname) {
        String sql = "UPDATE t_customer_info SET user_name='', user_id=null ,status=0,last_modify_by=? WHERE archive_status=0 and  id  >=? and id<=?";
        return jdbcTemplate.update(sql, realname,customer_id_start,customer_id_end);
    }

}
