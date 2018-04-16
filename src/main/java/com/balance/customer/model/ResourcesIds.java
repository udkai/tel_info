package com.balance.customer.model;

import java.util.List;

/**
 * 名单来源号段表
 * Created by liukai on 2018/4/16.
 */
public class ResourcesIds {
    private String resources;//
    private String id;//
    private List id_list;
private String id_section;

    public String getId_section() {
        return id_section;
    }

    public void setId_section(String id_section) {
        this.id_section = id_section;
    }

    public List getId_list() {
        return id_list;
    }

    public void setId_list(List id_list) {
        this.id_list = id_list;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ResourcesIds{" +
                "resources='" + resources + '\'' +
                ", id='" + id + '\'' +
                ", id_list=" + id_list +
                ", id_section='" + id_section + '\'' +
                '}';
    }
}
