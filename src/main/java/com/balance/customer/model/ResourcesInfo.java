package com.balance.customer.model;

import java.util.Date;

/**
 * Created by liukai on 2018/3/27.
 */
public class ResourcesInfo {
    private String resources;//名单来源
    private Date create_at;//导入时间
    private Integer total;//同一来源名单数量

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ResourcesInfo{" +
                "resources='" + resources + '\'' +
                ", create_at=" + create_at +
                ", total=" + total +
                '}';
    }
}
