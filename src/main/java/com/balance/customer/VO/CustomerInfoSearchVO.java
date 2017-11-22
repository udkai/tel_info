package com.balance.customer.VO;

/**
 * Created by dsy on 2017/4/19.
 * 客户信息表  查询VO
 */
public class CustomerInfoSearchVO {

    private String name;  //姓名
    private String mobile;//电话号码
    private Integer status;//分配状态0--未分配1--已分配
    private Integer user_id;//业务员编号
    private String user_name;//业务员姓名
    private String customer_id_start;//客户编号
    private String customer_id_end;//客户编号
    private String resources;//信息来源
    private Integer customer_status;//客户状态

    @Override
    public String toString() {
        return "CustomerInfoSearchVO{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", customer_id_start='" + customer_id_start + '\'' +
                ", customer_id_end='" + customer_id_end + '\'' +
                ", resources='" + resources + '\'' +
                ", customer_status=" + customer_status +
                '}';
    }

    public String getResources() {
        return resources;
    }
    public String getResourcesParam(){
        return "%"+resources+"%";
    }
    public void setResources(String resources) {
        this.resources = resources;
    }

    public Integer getCustomer_status() {

        return customer_status;
    }

    public void setCustomer_status(Integer customer_status) {
        this.customer_status = customer_status;
    }

    public String getCustomer_id_start() {
        return customer_id_start;
    }

    public void setCustomer_id_start(String customer_id_start) {
        this.customer_id_start = customer_id_start;
    }

    public String getCustomer_id_end() {
        return customer_id_end;
    }

    public void setCustomer_id_end(String customer_id_end) {
        this.customer_id_end = customer_id_end;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getNameParam() {
        return "%" + name + "%";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
