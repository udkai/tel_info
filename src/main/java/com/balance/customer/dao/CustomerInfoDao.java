package com.balance.customer.dao;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.balance.customer.VO.CustomerInfoSearchVO;
import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.User;
import com.balance.util.page.PageUtil;
import com.balance.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by dsy on 2017/9/18.
 * 用户信息表  Dao
 */
@Repository
public class CustomerInfoDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<CustomerInfo> listAllAllot(){
		String sql="select * from t_customer_info t where t.status=1 ORDER BY t.user_id,t.id asc";
		List<CustomerInfo> list=jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(CustomerInfo.class));
		return list;
	}
	public int updateRelieve(String id_start, String id_end) {
		String sql = "UPDATE t_customer_info SET user_name='', user_id=null ,status=0 WHERE archive_status=0 and id>="+id_start+" and id<="+id_end;
		return jdbcTemplate.update(sql);
	}
	public int updateAllot(String id_start, String id_end,String userId,String userName,String allot_by,Date allot_at) {
		String sql = "UPDATE t_customer_info SET user_name=?, user_id=? ,allot_by=?,allot_at=? WHERE archive_status=0 and id>="+id_start+" and id<="+id_end;
		return jdbcTemplate.update(sql,userName,userId,allot_by,allot_at);
	}
	/**
	 * 查询
	 *
	 * @param customerInfoSearchVO 查询条件
	 * @return 客户信息列表
	 */
	public List<CustomerInfo> search(CustomerInfoSearchVO customerInfoSearchVO, int pageIndex, int pageSize) {
		String sql = "select * from t_customer_info ";
		sql += createSql(customerInfoSearchVO);
		sql = PageUtil.createMysqlPageSql(sql, pageIndex, pageSize);
		SqlParameterSource params = new BeanPropertySqlParameterSource(customerInfoSearchVO);
		NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
		return namedJdbc.query(sql, params, new BeanPropertyRowMapper(CustomerInfo.class));
	}
	public List<User>findUserList(){
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
	/**
	 * 导出
	 *
	 * @param customerInfoSearchVO 查询条件
	 * @return 客户信息列表
	 */
	public List<CustomerInfo> export(CustomerInfoSearchVO customerInfoSearchVO) {
		String sql = "select * from t_customer_info ";
		sql += createSql(customerInfoSearchVO);
		SqlParameterSource params = new BeanPropertySqlParameterSource(customerInfoSearchVO);
		NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
		return namedJdbc.query(sql, params, new BeanPropertyRowMapper(CustomerInfo.class));
	}

	/**
	 * 统计查询数
	 *
	 * @param customerInfoSearchVO 查询条件 姓名
	 * @return 数目
	 */
	public int count(CustomerInfoSearchVO customerInfoSearchVO) {
		String sql = "select count(*) from t_customer_info ";
		sql += createSql(customerInfoSearchVO);
		SqlParameterSource params = new BeanPropertySqlParameterSource(customerInfoSearchVO);
		NamedParameterJdbcTemplate namedJdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
		return namedJdbc.queryForObject(sql, params, Integer.class);
	}

	private String createSql(CustomerInfoSearchVO customerInfoSearchVO) {
		String sql = " where 1=1";
		if (StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getName())) {
			sql += " and name like :nameParam";
		}
		if(StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getMobile())){
			sql += " and mobile =:mobile";
		}
		if(customerInfoSearchVO.getStatus()!=null){
			sql += " and status =:status";
		}
		if(customerInfoSearchVO.getUser_id()!=null){
			sql +=" and user_id=:user_id";
		}
		if (StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getCustomer_id_start())) {
			sql += " and id >= :customer_id_start";
		}
		if (StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getCustomer_id_end())) {
			sql += " and id <= :customer_id_end";
		}
		if(StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getResources())){
			sql += " and resources like :resourcesParam";
		}
		if(customerInfoSearchVO.getCustomer_status()!=null){
			sql += " and customer_status = :customer_status";
		}
		if(StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getOperate_at_start())){
			sql+=" and operate_at>=str_to_date(:operate_at_start,'%Y-%m-%d')";
		}
		if(StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getOperate_at_end())){
			sql+=" and operate_at<=str_to_date(:operate_at_end,'%Y-%m-%d')";
		}
		if(StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getAllot_at_start())){
			sql+=" and allot_at>=str_to_date(:allot_at_start,'%Y-%m-%d')";
		}
		if(StringUtil.isNotNullOrEmpty(customerInfoSearchVO.getAllot_at_end())){
			sql+=" and allot_at<=str_to_date(:allot_at_end,'%Y-%m-%d')";
		}
		return sql;
	}

	/**
	 * 新增
	 *
	 * @param customerInfo 客户信息
	 * @return 成功1  失败0
	 */
	public int add(CustomerInfo customerInfo) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "INSERT INTO t_customer_info(id,name,mobile, remark,remark_status,user_name,user_id,resources,status,create_by, create_at)  "
				+ " VALUES(:id,:name,:mobile, :remark,:remark_status,:user_name,:user_id,:resources,:status,:create_by, now()) ";
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(customerInfo);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 批量更新
	 * @param customerInfo
	 */
	public void batchAdd(CustomerInfo customerInfo) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		String sql = "INSERT INTO t_customer_info(id,name,mobile, remark,remark_status,user_name,user_id,resources,status,customer_status,create_by, create_at)  "
				+ " VALUES(:id,:name,:mobile, :remark,:remark_status,:user_name,:user_id,:resources,:status,0,:create_by, :create_at) ";
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(new Object[]{customerInfo});
		int[]num= namedParameterJdbcTemplate.batchUpdate(sql, params);
	}
	/**
	 * 修改
	 *
	 * @param customerInfo 客户信息
	 * @return 成功1 失败0
	 */
	public int update(CustomerInfo customerInfo) {
		String sql = "UPDATE t_customer_info SET name=:name,address=:address, tel=:tel,belong_to=:belong_to  WHERE id=:id";
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(customerInfo);
		return namedParameterJdbcTemplate.update(sql, paramSource);
	}

	/**
	 * 客户归档
	 * @param customer_id
	 * @return
	 */
	public int update(String customer_id){
		String sql = "UPDATE t_customer_info SET archive_status=1 ,archive_time=now() where id in ("+customer_id+")";
		return jdbcTemplate.update(sql);
	}

	public int delete(String customer_id_start,String customer_id_end) {
		String sql = "DELETE  FROM t_customer_info WHERE id>=? and id <=? ";
		return jdbcTemplate.update(sql,new Object[]{customer_id_start,customer_id_end});
	}
	public CustomerInfo getById(int id){
		String sql="select * from t_customer_info WHERE id=?";
		List<CustomerInfo> list = jdbcTemplate.query(sql, new Object[] { id }, BeanPropertyRowMapper.newInstance(CustomerInfo.class));
		return list.size() > 0 ? list.get(0) : null;

	}
	public CustomerInfo getByMobile(String mobile){
		String sql="select * from t_customer_info WHERE mobile=?";
		List<CustomerInfo> list = jdbcTemplate.query(sql, new Object[] { mobile }, BeanPropertyRowMapper.newInstance(CustomerInfo.class));
		return list.size() > 0 ? list.get(0) : null;

	}
}
