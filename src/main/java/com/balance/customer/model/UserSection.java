package com.balance.customer.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liukai on 2018/3/25.
 */
public class UserSection {
    private String user_name;//用户名
    private int user_id;//业务员id
    private String id_section;//号码段  如：0000002-0000032
    private int id;
    private Date allot_at;//分配时间
    private String resources;//名单来源
private String id_section_start;//号段起
private String id_section_end;//号段止

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {

        return user_id;
    }

    @Override
    public String toString() {
        return "UserSection{" +
                "user_name='" + user_name + '\'' +
                ", user_id=" + user_id +
                ", id_section='" + id_section + '\'' +
                ", id=" + id +
                ", allot_at=" + allot_at +
                ", resources='" + resources + '\'' +
                ", id_section_start='" + id_section_start + '\'' +
                ", id_section_end='" + id_section_end + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getId_section_start() {
        return id_section_start;
    }

    public void setId_section_start(String id_section_start) {
        this.id_section_start = id_section_start;
    }

    public String getId_section_end() {
        return id_section_end;
    }

    public void setId_section_end(String id_section_end) {
        this.id_section_end = id_section_end;
    }

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

}
