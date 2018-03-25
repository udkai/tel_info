package com.balance.statistics.VO;

import java.util.Date;

/**
 * Created by liukai on 2018/3/20.
 */
public class StatisticsSearchVO {
    private Date startTime;
    private Date endTime;
    private Integer user_id;
    private String resources;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getResources() {
        return resources;
    }
    public String getResourcesParm(){
        return "%"+resources+"%";
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "StatisticsSearchVO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", userId='" + user_id + '\'' +
                ", resources='" + resources + '\'' +
                '}';
    }
}
