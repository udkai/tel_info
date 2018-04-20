package com.balance.customer.dao;

import com.balance.customer.model.*;
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
    public int countSection(String resources,String create_at) {
        String sql="select count(*) from(SELECT user_id,user_name,resources,allot_at,gn,min(id) id_min,max(id) id_max FROM(SELECT user_id,user_name,resources,allot_at,id - rownum gn,id FROM(SELECT @ROW :=@ROW + 1 AS rownum,id,user_id,user_name,resources,allot_atFROM(SELECT(SELECT @ROW := 0),id,user_id,user_name ,resources,allot_at FROM t_customer_info where resources=? and create_at=str_to_date(?,'%Y-%m-%d %H:%i:%s') ORDER BY resources,user_id,allot_at,id )a)b)c GROUP BY user_id,gn ) d";
        int count = jdbcTemplate.queryForObject(sql,new Object[]{resources,create_at}, Integer.class);
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
    public int deleteResourceSection(String resources, String create_at) {
        String sql = "DELETE  from  t_resources_section  where resources=?  ";
        return jdbcTemplate.update(sql, resources);
    }
    public int deleteResourceSectionById(int id) {
        String sql = "DELETE  from  t_resources_section  where id=? ";
        return jdbcTemplate.update(sql, id);
    }
    public List<UserSection> listAllAllot(String resources){
        String sql="select * from t_resources_section t where t.resources=?  ORDER BY t.user_id,t.id asc";
        List<UserSection> list=jdbcTemplate.query(sql,new Object[]{resources},new BeanPropertyRowMapper<>(UserSection.class));
        return list;
    }

    public List<Section>listSection(String resources,String create_at){
        String sql="SELECT user_id,user_name,resources,allot_at,gn,min(id) id_min,max(id) id_max " +
                "FROM(SELECT user_id,user_name,resources,allot_at,id - rownum gn,id FROM(SELECT @ROW :=@ROW + 1 AS rownum,id,user_id,user_name,resources,allot_at FROM(SELECT(SELECT @ROW := 0),id,user_id,user_name ,resources,allot_at FROM t_customer_info where resources=? and create_at=str_to_date(?,'%Y-%m-%d %H:%i:%s') ORDER BY resources,user_id,allot_at,id )a)b)c GROUP BY user_id,gn order by resources,user_id,allot_at,id_min";
        return jdbcTemplate.query(sql,new Object[]{resources,create_at},BeanPropertyRowMapper.newInstance(Section.class));
    }

    public int updateRelieves(String resources,Integer user_id,String allot_at,String id_start, String id_end) {
        String sql = "UPDATE t_customer_info SET remark='',remark_status=0, user_name='', user_id=null ,status=0,customer_status=0  WHERE archive_status=0 and resources=? and user_id=? and allot_at=STR_TO_DATE(?,'%Y-%m-%d %H:%i:%s') and id>=? and id<=?";
        int count= jdbcTemplate.update(sql,resources,user_id,allot_at,id_start,id_end);
        return count;
    }
    public int deleteUserSection(String id_section_start,String id_section_end){
        String sql="delete from t_user_section where  id_section_start= ? and id_section_end=? ";
        return jdbcTemplate.update(sql,new Object[]{id_section_start,id_section_end});
    }
    public List<ResourcesIds>listIdByResources(String resources){
        String sql="select id from t_customer_info where resources=? order by id asc";
        return jdbcTemplate.query(sql,new Object[]{resources},BeanPropertyRowMapper.newInstance(ResourcesIds.class));
    }
}
