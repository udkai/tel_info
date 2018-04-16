package com.balance.customer.service;

import com.balance.customer.dao.ResourcesDao;
import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.ResourcesInfo;
import com.balance.customer.model.UserSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liukai on 2018/3/27.
 */
@Service
public class ResourcesService {
    @Autowired
    private ResourcesDao resourcesDao;
    public int count(){
        return resourcesDao.count();
    }
    public List<ResourcesInfo> list(int pageIndex, int pageSize){
        return resourcesDao.list(pageIndex,pageSize);
    }
    public int updateRelieve(String resources,String create_at){
        return resourcesDao.relieve(resources,create_at);
    }
    public int delete(String resources,String create_at){
        resourcesDao.deleteResourceSection(resources,create_at);
        return resourcesDao.delete(resources,create_at);
    }
    public int updateRelieves(int id,String resources,String user_name,String allot_at,String id_start, String id_end) {

        resourcesDao.updateRelieves(resources,user_name,allot_at,id_start,id_end);
        resourcesDao.deleteResourceSectionById(id);
        return 1;
    }
    public List<UserSection> listAllAllot(String resources) {
        return resourcesDao.listAllAllot(resources);
    }

    /**
     * 查看该名单来源的所有业务员号段
     * @return
     */
//    public List<UserSection> listAllAllot(String resources,String create_at) {
//        List<UserSection> listSec = new ArrayList<>();
//        List<CustomerInfo> list = resourcesDao.listAllAllot(resources,create_at);
//        if (list.size() == 0) {
//            return null;
//        }
//        UserSection user = new UserSection();
//        String user_name1 = list.get(0).getUser_name();
//        Date allot_at1=list.get(0).getAllot_at();
////        int[]id=new int[]{};
//        List<Integer> ids = new ArrayList<>();
//          if (list.size() == 1) {
//            user.setUser_name(user_name1);
//            user.setResources(resources);
//            user.setAllot_at(list.get(0).getAllot_at());
//            ids.add(Integer.valueOf(list.get(0).getId()));
//            user.setId(ids);
//            listSec.add(user);
//        } else {
//            for (int i = 1; i < list.size(); i++) {
//                user.setUser_name(list.get(i - 1).getUser_name());
//                user.setAllot_at(list.get(i-1).getAllot_at());
//                user.setResources(resources);
//                ids.add(Integer.valueOf(list.get(i - 1).getId()));
//                user.setId(ids);
//                String name_i = list.get(i).getUser_name();
//                Date allot_at_i=list.get(i).getAllot_at();
//                if (i == list.size() - 1) {
//                    if (user_name1.equals(name_i)&&allot_at1.equals(allot_at_i)) {
//                        ids.add(Integer.valueOf(list.get(i).getId()));
//                        user.setId(ids);
//                        listSec.add(user);
//                    }
//                }
//
//                if (!user_name1.equals(name_i)||!allot_at1.equals(allot_at_i)) {
//                    listSec.add(user);
//                    user = new UserSection();
//                    user.setResources(resources);
//                    ids = new ArrayList<>();
//                    user_name1 = name_i;
//                    allot_at1=allot_at_i;
//                    if (i == list.size() - 1) {
//                        user.setUser_name(name_i);
//                        user.setAllot_at(allot_at_i);
//                        ids.add(Integer.valueOf(list.get(i).getId()));
//                        user.setId(ids);
//                        listSec.add(user);
//                    }
//                }
//            }
//            System.out.println("第一次：" + listSec);
//        }
//        List listSec2 = new ArrayList();
//        //判断id，连续的转成号段
//        for (int i = 0; i < listSec.size(); i++) {
//            List<Integer> list_id = listSec.get(i).getId();
//            String user_name = listSec.get(i).getUser_name();
//            String resource=listSec.get(i).getResources();
//            Date allot_at=listSec.get(i).getAllot_at();
//            List<String> listNum = toSection(list_id);
//            for (int j = 0; j < listNum.size(); j++) {
//                UserSection userSection = new UserSection();
//                userSection.setUser_name(user_name);
//                userSection.setAllot_at(allot_at);
//                userSection.setResources(resource);
//                userSection.setId_section(listNum.get(j));
//                listSec2.add(userSection);
//            }
//        }
//        System.out.println("第二次：" + listSec2);
//        return listSec2;
//    }
    /**
     * 数字转成号段  如：1，2，3，5,6,7
     *
     * @param arg
     * @return
     */
    public List<String> toSection(List<Integer> arg) {
        List<String> list = new ArrayList();
        List<Integer> ids = arg;
        int a = ids.get(0);
        int b, c;
        String str;
        if (ids.size() == 1) {
            str = String.format("%08d", a) + "-" + String.format("%08d", a);
            list.add(str);
            System.out.println("长度1：" + list);
            return list;
        }
        for (int i = 1; i < ids.size(); i++) {
            b = ids.get(i - 1);
            c = ids.get(i);
            if (b + 1 != c) {
                str = String.format("%08d", a) + "-" + String.format("%08d", b);
                list.add(str);
                a = c;
            }
            if (i == ids.size() - 1) {
                if (b + 1 != c) {
                    str = String.format("%08d", a) + "-" + String.format("%08d", c);
                    list.add(str);
                } else {
                    str = String.format("%08d", a) + "-" + String.format("%08d", c);
                    list.add(str);
                }
            }
        }
        return list;
    }
}
