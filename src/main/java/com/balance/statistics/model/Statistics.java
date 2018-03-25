package com.balance.statistics.model;

/**
 * Created by liukai on 2018/3/21.
 */
public class Statistics {
    private String resources;//名单来源
    private String user_name;;//业务员姓名
    private int total;//名单总数
    private int notAlloted;//尚未分配名单
    private int alloted;//已经分配的名单数
    private int called;//已呼叫的客户数
    private int status0;//未呼叫的客户数
    private int status1;//空号数
    private int status2;//拒接数量
    private int status3;//无人接听数量
    private int status4;//尝试家微信
    private int status5;//加微信通过
    private int status6;//已经邀约数量
    private int status7;//不需要数量

    @Override
    public String toString() {
        return "Statistics{" +
                "resources='" + resources + '\'' +
                ", user_name='" + user_name + '\'' +
                ", total=" + total +
                ", notAlloted=" + notAlloted +
                ", alloted=" + alloted +
                ", called=" + called +
                ", status0=" + status0 +
                ", status1=" + status1 +
                ", status2=" + status2 +
                ", status3=" + status3 +
                ", status4=" + status4 +
                ", status5=" + status5 +
                ", status6=" + status6 +
                ", status7=" + status7 +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNotAlloted() {
        return notAlloted;
    }

    public void setNotAlloted(int notAlloted) {
        this.notAlloted = notAlloted;
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

    public int getAlloted() {
        return alloted;
    }

    public void setAlloted(int alloted) {
        this.alloted = alloted;
    }

    public int getCalled() {
        return called;
    }

    public void setCalled(int called) {
        this.called = called;
    }

    public int getStatus0() {
        return status0;
    }

    public void setStatus0(int status0) {
        this.status0 = status0;
    }

    public int getStatus1() {
        return status1;
    }

    public void setStatus1(int status1) {
        this.status1 = status1;
    }

    public int getStatus2() {
        return status2;
    }

    public void setStatus2(int status2) {
        this.status2 = status2;
    }

    public int getStatus3() {
        return status3;
    }

    public void setStatus3(int status3) {
        this.status3 = status3;
    }

    public int getStatus4() {
        return status4;
    }

    public void setStatus4(int status4) {
        this.status4 = status4;
    }

    public int getStatus5() {
        return status5;
    }

    public void setStatus5(int status5) {
        this.status5 = status5;
    }

    public int getStatus6() {
        return status6;
    }

    public void setStatus6(int status6) {
        this.status6 = status6;
    }

    public int getStatus7() {
        return status7;
    }

    public void setStatus7(int status7) {
        this.status7 = status7;
    }

}
