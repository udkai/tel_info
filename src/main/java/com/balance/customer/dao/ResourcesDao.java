package com.balance.customer.dao;

import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.ResourcesInfo;
import com.balance.util.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 来源管理
 * Created by liukai on 2018/3/27.
 */
@Repository
public class ResourcesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询
     *
     * @return 客户信息列表
     */
    public List<ResourcesInfo> list(int pageIndex, int pageSize) {
        String sql = "select t.resources,t.create_at,count(t.create_at) total from t_customer_info t GROUP BY t.resources,t.create_at order by t.create_at desc ";
        sql = PageUtil.createMysqlPageSql(sql, pageIndex, pageSize);
        List<ResourcesInfo> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ResourcesInfo.class));
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    public int count() {
        String sql = "select count(*) from (select t.resources,t.create_at,count(t.create_at) from t_customer_info t GROUP BY t.resources,t.create_at ) r";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    /**
     * 解除该导入时间的，同一来源的名单
     * @param resources
     * @param create_at
     * @return
     */
    public int relieve(String resources, String create_at) {
        String sql = "update t_customer_info t set t.status=0 where t.resources=? and t.create_at=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') and t.archive_status=0";
        return jdbcTemplate.update(sql, resources, create_at);
    }
    public int delete(String resources, String create_at) {
        String sql = "DELETE  from  t_customer_info  where resources=? and create_at=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') ";
        return jdbcTemplate.update(sql, resources, create_at);
    }
    public List<CustomerInfo> listAllAllot(String resources,String create_at){
        String sql="select * from t_customer_info t where t.status=1 and t.resources=? and t.create_at=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s')  ORDER BY t.user_id,t.allot_at,t.id asc";
        List<CustomerInfo> list=jdbcTemplate.query(sql,new Object[]{resources,create_at},new BeanPropertyRowMapper<>(CustomerInfo.class));
        return list;
    }
    public int updateRelieves(String resources,String user_name,String allot_at,String id_start, String id_end) {
        String sql = "UPDATE t_customer_info SET user_name='', user_id=null ,status=0 WHERE archive_status=0 and resources=? and user_name=? and allot_at=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') and id>=? and id<=?";
        int count= jdbcTemplate.update(sql,resources,user_name,allot_at,id_start,id_end);
        return count;
    }
}
