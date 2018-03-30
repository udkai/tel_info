package com.balance.customer.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liukai on 2018/3/25.
 */
public class UserSection {
    private String user_name;//用户名
    private String id_section;//号码段  如：0000002-0000032
    private List<Integer> id;//名单id数组
    private Date allot_at;//分配时间
    private String resources;//名单来源

    public Date getAllot_at() {
        return allot_at;
    }

    public void setAllot_at(Date allot_at) {
        this.allot_at = allot_at;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getId_section() {
        return id_section;
    }

    public void setId_section(String id_section) {
        this.id_section = id_section;
    }

    @Override
    public String toString() {
        return "UserSection{" +
                "user_name='" + user_name + '\'' +
                ", id_section='" + id_section + '\'' +
                ", id=" + id +
                ", allot_at=" + allot_at +
                ", resources='" + resources + '\'' +
                '}';
    }

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

}
