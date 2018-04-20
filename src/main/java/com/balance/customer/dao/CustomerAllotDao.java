package com.balance.customer.dao;

import com.balance.customer.VO.CustomerAllotSearchVO;
import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.ResourcesIds;
import com.balance.customer.model.User;
import com.balance.customer.model.UserSection;
import com.balance.util.page.PageUtil;
import com.balance.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by liukai on 2017/10/18.
 */
@Repository
public class CustomerAllotDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserSection>listUserSection(){
        String sql="select * from t_user_section";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(UserSection.class));
    }
    public List<UserSection>listResourceSection(){
        String sql="select * from t_resources_section";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(UserSection.class));
    }

    /**
     * 查询来源号段表
     * @return
     */
    public List<UserSection>listResourcesSection(){
        String sql="select * from t_resources_section";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(UserSection.class));
    }

    /**
     * 查询用户信息表
     * @param customer_id_start
     * @param customer_id_end
     * @return
     */
    public List<ResourcesIds>listResourcesSectionByID(String customer_id_start, String customer_id_end ){
        String sql="select resources,id from t_customer_info where archive_status=0 and id>=? and id<=? order BY resources,id asc";
        return jdbcTemplate.query(sql,new Object[]{customer_id_start,customer_id_end},BeanPropertyRowMapper.newInstance(ResourcesIds.class));
    }
    public List<ResourcesIds>listUserSectionByID(String customer_id_start, String customer_id_end ){
        String sql="select id from t_customer_info where archive_status=0 and id>=? and id<=? order BY id asc";
        return jdbcTemplate.query(sql,new Object[]{customer_id_start,customer_id_end},BeanPropertyRowMapper.newInstance(ResourcesIds.class));
    }
    /**
     * 查询
     *
     * @param customerAllotSearchVO 查询条件
     * @return 客户信息列表
     */
    public List<CustomerInfo> search(CustomerAllotSearchVO customerAllotSearchVO, int pageIndex, int pageSize) {
        String sql = "select id,name,mobile,remark,status,archive_status,resources,user_id,user_name,allot_at,operate_at from t_customer_info where 1=1  ";
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
        if (customerAllotSearchVO.getCustomer_status() != null) {
            sql += " and customer_status =:customer_status";
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
    public int saveAllot(String customer_id_start, String customer_id_end,int user_id, String username,String allot_by,Date allot_at) {
        String sql = "UPDATE t_customer_info SET user_name=?, user_id= ? ,allot_by=?,allot_at=?, status=1  WHERE archive_status=0 and id  >=? and id<=?";
        return jdbcTemplate.update(sql, new Object[]{username, user_id,allot_by,allot_at,customer_id_start,customer_id_end});
    }

    /**
     * 保存业务员号段表（t_user_section）信息
     * @param userSection
     * @return
     */
    public int saveUserSection(UserSection userSection){
        String sql="insert into t_user_section(user_id,user_name,id_section_start,id_section_end)values(:user_id,:user_name,:id_section_start,:id_section_end)";
    SqlParameterSource params = new BeanPropertySqlParameterSource(userSection);
    NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    int count=namedJdbc.update(sql,params);
    return count;
}
    /**
     * 保存业务员号段表（t_user_section）信息
     * @param userSection
     * @return
     */
    public int saveResourceSection(UserSection userSection){
        String sql="insert into t_resources_section(resources,allot_at,user_id,user_name,id_section_start,id_section_end)values(:resources,:allot_at,:user_id,:user_name,:id_section_start,:id_section_end)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(userSection);
        NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        int count=namedJdbc.update(sql,params);
        return count;
    }
    /**
     * 删除业务员号段表信息
     * @param id
     * @return
     */
    public int deleteUserSectionById(Integer id){
        String sql="delete from t_user_section where id=?";
        return jdbcTemplate.update(sql,new Object[]{id});
}
    /**
     * 删除业务员号段表信息
     * @param id
     * @return
     */
    public int deleteResourcesSectionById(Integer id){
        String sql="delete from t_resources_section where id=?";
        return jdbcTemplate.update(sql,new Object[]{id});
    }
    /**
     *取消分发
     * @param customer_id_start
     * @param customer_id_end
     * @param realname
     * @return
     */
    public int saveCancel(String customer_id_start, String customer_id_end,Integer user_id, String realname) {
        String sql = "UPDATE t_customer_info SET user_name='', user_id=null ,status=0 ,allot_at=null,allot_by=null,remark=null,remark_status=0,customer_status=0 WHERE archive_status=0 ";
        sql+=createCancelSql(customer_id_start,customer_id_end,user_id);
        return jdbcTemplate.update(sql);
    }

    /**
     * 取消分配sql
     * @param customer_id_start
     * @param customer_id_end
     * @param user_id
     * @return
     */
    public String createCancelSql(String customer_id_start, String customer_id_end,Integer user_id){
        String sql="";
        if(StringUtil.isNotNullOrEmpty(customer_id_start)){
            sql+=" and id>="+customer_id_start;
        }
        if(StringUtil.isNotNullOrEmpty(customer_id_end)){
            sql+=" and id<="+customer_id_end;
        }
        if(user_id!=null){
            sql+=" and user_id="+user_id;
        }
        return sql;
    }
}
