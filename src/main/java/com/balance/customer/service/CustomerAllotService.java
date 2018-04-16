package com.balance.customer.service;

import com.balance.customer.VO.CustomerAllotSearchVO;
import com.balance.customer.dao.CustomerAllotDao;
import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.ResourcesIds;
import com.balance.customer.model.User;
import com.balance.customer.model.UserSection;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liukai on 2017/10/18.
 */
@Service
public class CustomerAllotService {
    @Autowired
    private CustomerAllotDao customerAllotDao;

    /**
     * 查询
     */
    public List<CustomerInfo> search(CustomerAllotSearchVO customerAllotSearchVO, int pageIndex, int pageSize) {
        return customerAllotDao.search(customerAllotSearchVO, pageIndex, pageSize);
    }

    public int count(CustomerAllotSearchVO customerAllotSearchVO) {

        return customerAllotDao.count(customerAllotSearchVO);
    }

    public List<User> getUser() {
        return customerAllotDao.getUser();
    }

    /**
     * @param customer_id_start
     * @param customer_id_end
     * @param user_id
     * @param username
     * @return
     */
    public int saveAllot(String customer_id_start, String customer_id_end, int user_id, String username, String allot_by, Date allot_at) {

        customerAllotDao.saveAllot(customer_id_start, customer_id_end, user_id, username, allot_by, allot_at);
        //修改业务员号段表
        toUpdateUserSection(customer_id_start, customer_id_end, user_id, username, allot_at);
       //修改名单来源号段表
        List<ResourcesIds> list=listResourceSectionById(customer_id_start, customer_id_end);
        toUpdateResourceSection(list, user_id, username, allot_at);
        return 1;
    }

    /**
     * 查询某个范围内的来源并转为号段
     * @param customer_id_start
     * @param customer_id_end
     * @return
     */
    private List listResourceSectionById(String customer_id_start, String customer_id_end) {
        List<ResourcesIds> list = customerAllotDao.listResourcesSectionByID(customer_id_start, customer_id_end);
        ResourcesIds res=new ResourcesIds();
        List<String >list1=new ArrayList<>();
        String resources1=list.get(0).getResources();
        String resourcesi;
        List<ResourcesIds>listTemp=new ArrayList<>();
        for (int i=1;i<list.size();i++) {

            list1.add(list.get(i-1).getId());
            res.setResources(resources1);
            res.setId_list(list1);
            resourcesi=list.get(i).getResources();
            if(!resources1.equals(resourcesi)){
                listTemp.add(res);
                res=new ResourcesIds();
                list1=new ArrayList<>();
            }
            if(i==list.size()-1){
                list1.add(list.get(i).getId());
                res.setResources(resources1);
                res.setId_list(list1);
                listTemp.add(res);
            }
            resources1=resourcesi;
        }
        List<ResourcesIds>listResourcesSection=new ArrayList<>();
        for(int i = 0; i < listTemp.size(); i++){
          List id_list=listTemp.get(i).getId_list();
          String resources=listTemp.get(i).getResources();
            List<String> listNum = toSection(id_list);
            for (int j = 0; j < listNum.size(); j++) {
                ResourcesIds resourceSection=new ResourcesIds();
                resourceSection.setResources(resources);
                resourceSection.setId_section(listNum.get(j));
                listResourcesSection.add(resourceSection);
            }
        }
        return listResourcesSection;
    }

    /**
     * 数字转成号段  如：1，2，3，5,6,7
     *
     * @param arg
     * @return
     */
    public List<String> toSection(List<String> arg) {
        List<String> list = new ArrayList();
        List<Integer> ids = new ArrayList();
        for(String str:arg){
            Integer num=Integer.valueOf(str);
            ids.add(num);
        }

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
    /**
     * 修改业务员号段表
     * @param customer_id_start
     * @param customer_id_end
     * @param user_id
     * @param username
     * @param allot_at
     */
    private void toUpdateUserSection(String customer_id_start, String customer_id_end, int user_id, String username, Date allot_at) {
        //分6种情况 修改业务员号段表
        List<UserSection> list = customerAllotDao.listUserSection();
        int id_start = Integer.valueOf(customer_id_start);
        int id_end = Integer.valueOf(customer_id_end);
        UserSection userSectionOld1 = new UserSection();
        UserSection userSectionOld2 = new UserSection();
        UserSection userSectionNew = new UserSection();
        userSectionOld1.setAllot_at(allot_at);
        userSectionNew.setAllot_at(allot_at);
        if(list.size()==0){
            userSectionNew.setUser_name(username);
            userSectionNew.setUser_id(user_id);
            userSectionNew.setId_section_start(customer_id_start);
            userSectionNew.setId_section_end(customer_id_end);
        }
        for (UserSection userSection : list) {
            String idSectionStartStr = userSection.getId_section_start();
            int idSectionStart = Integer.valueOf(idSectionStartStr);
            String idSectionEndStr = userSection.getId_section_end();
            int idSectionEnd = Integer.valueOf(idSectionEndStr);
            int user_id_old = userSection.getUser_id();
            String user_name_old = userSection.getUser_name();
            int id = userSection.getId();
            if (id_start < idSectionStart) {
                if (id_end < idSectionEnd) {
                    userSectionNew.setUser_name(username);
                    userSectionNew.setUser_id(user_id);
                    userSectionNew.setId_section_start(customer_id_start);
                    userSectionNew.setId_section_end(customer_id_end);
                }
                if (id_end > idSectionStart && id_end < idSectionEnd) {
                    customerAllotDao.deleteUserSectionById(id);
                    if (!user_name_old.equals(username)) {
                        userSectionOld2.setUser_name(user_name_old);
                        userSectionOld2.setUser_id(user_id_old);
                        userSectionOld2.setId_section_start(customer_id_end);
                        userSectionOld2.setId_section_end(idSectionEndStr);

                        userSectionNew.setUser_name(username);
                        userSectionNew.setUser_id(user_id);
                        userSectionNew.setId_section_start(customer_id_start);
                        userSectionNew.setId_section_end(customer_id_end);
                    } else {
                        userSectionNew.setUser_name(username);
                        userSectionNew.setUser_id(user_id);
                        userSectionNew.setId_section_start(customer_id_start);
                        userSectionNew.setId_section_end(idSectionEndStr);
                    }
                }
                if (id_end > idSectionEnd) {
                    customerAllotDao.deleteUserSectionById(id);
                    userSectionNew.setUser_name(username);
                    userSectionNew.setUser_id(user_id);
                    userSectionNew.setId_section_start(customer_id_start);
                    userSectionNew.setId_section_end(customer_id_end);
                }
            }


            if (id_start > idSectionStart && id_start < idSectionEnd) {
                if (id_end < idSectionStart) {
                    customerAllotDao.deleteUserSectionById(id);
                    if (!user_name_old.equals(username)) {
                        userSectionOld1.setUser_name(user_name_old);
                        userSectionOld1.setUser_id(user_id_old);
                        userSectionOld1.setId_section_start(idSectionStartStr);
                        userSectionOld1.setId_section_end(StringUtils.leftPad(String.valueOf(id_start - 1), 8, '0'));

                        userSectionOld2.setUser_name(user_name_old);
                        userSectionOld2.setUser_id(user_id_old);
                        userSectionOld2.setId_section_start(StringUtils.leftPad(String.valueOf(id_end + 1), 8, '0'));
                        userSectionOld2.setId_section_end(idSectionEndStr);

                        userSectionNew.setUser_name(username);
                        userSectionNew.setUser_id(user_id);
                        userSectionNew.setId_section_start(customer_id_start);
                        userSectionNew.setId_section_end(customer_id_end);
                    }
                }
                if (id_end > idSectionEnd) {
                    customerAllotDao.deleteUserSectionById(id);
                    if (!user_name_old.equals(username)) {
                        userSectionOld1.setUser_name(user_name_old);
                        userSectionOld1.setUser_id(user_id_old);
                        userSectionOld1.setId_section_start(idSectionStartStr);
                        userSectionOld1.setId_section_end(StringUtils.leftPad(String.valueOf(id_start - 1), 8, '0'));

                        userSectionNew.setUser_name(username);
                        userSectionNew.setUser_id(user_id);
                        userSectionNew.setId_section_start(customer_id_start);
                        userSectionNew.setId_section_end(customer_id_end);
                    } else {
                        userSectionNew.setUser_name(username);
                        userSectionNew.setUser_id(user_id);
                        userSectionNew.setId_section_start(idSectionStartStr);
                        userSectionNew.setId_section_end(customer_id_end);
                    }
                }
            }
            if (id_start > idSectionEnd) {
                userSectionNew.setUser_name(username);
                userSectionNew.setUser_id(user_id);
                userSectionNew.setId_section_start(customer_id_start);
                userSectionNew.setId_section_end(customer_id_end);
            }
        }
        if (userSectionOld1.getId_section_start() != null) {
            customerAllotDao.saveUserSection(userSectionOld1);
        }
        if (userSectionOld2.getId_section_start() != null) {
            customerAllotDao.saveUserSection(userSectionOld2);
        }
        if (userSectionNew.getId_section_start() != null) {
            customerAllotDao.saveUserSection(userSectionNew);
        }
    }


    /**
     * 修改来源号段表
     * @param user_id
     * @param username
     * @param allot_at
     */
    private void toUpdateResourceSection(List<ResourcesIds> listCustomerSection,int user_id, String username, Date allot_at) {
        //分6种情况 修改业务员号段表
        List<UserSection> list = customerAllotDao.listResourcesSection();
//        int id_start = Integer.valueOf(customer_id_start);
//        int id_end = Integer.valueOf(customer_id_end);
        UserSection userSectionOld1 = new UserSection();
        UserSection userSectionOld2 = new UserSection();
        UserSection userSectionNew = new UserSection();
        userSectionOld1.setAllot_at(allot_at);
        userSectionNew.setAllot_at(allot_at);
        for(ResourcesIds resourcesIds:listCustomerSection) {
            String[] ids = resourcesIds.getId_section().split("-");
            String customer_id_start=ids[0];
            String customer_id_end=ids[0];
            String resources=resourcesIds.getResources();
            int id_start = Integer.valueOf(ids[0]);
            int id_end = Integer.valueOf(ids[1]);
            if (list.size() == 0) {
                userSectionNew.setUser_name(username);
                userSectionNew.setUser_id(user_id);
                userSectionNew.setAllot_at(allot_at);
                userSectionNew.setResources(resources);
                userSectionNew.setId_section_start(customer_id_start);
                userSectionNew.setId_section_end(customer_id_end);
            }
            for (UserSection userSection : list) {
                String idSectionStartStr = userSection.getId_section_start();
                int idSectionStart = Integer.valueOf(idSectionStartStr);
                String idSectionEndStr = userSection.getId_section_end();
                int idSectionEnd = Integer.valueOf(idSectionEndStr);
                int user_id_old = userSection.getUser_id();
                String resources_old=userSection.getResources();
                String user_name_old = userSection.getUser_name();
                int id = userSection.getId();
                if (id_start < idSectionStart) {
                    if (id_end < idSectionEnd) {
                        userSectionNew.setUser_name(username);
                        userSectionNew.setUser_id(user_id);
                        userSectionNew.setResources(resources);
                        userSectionNew.setAllot_at(allot_at);
                        userSectionNew.setId_section_start(customer_id_start);
                        userSectionNew.setId_section_end(customer_id_end);
                    }
                    if (id_end > idSectionStart && id_end < idSectionEnd) {
                        customerAllotDao.deleteUserSectionById(id);
                        if (!user_name_old.equals(username)) {
                            userSectionOld2.setUser_name(user_name_old);
                            userSectionOld2.setUser_id(user_id_old);
                            userSectionOld2.setAllot_at(allot_at);
                            userSectionOld2.setResources(resources_old);
                            userSectionOld2.setId_section_start(customer_id_end);
                            userSectionOld2.setId_section_end(idSectionEndStr);

                            userSectionNew.setUser_name(username);
                            userSectionNew.setUser_id(user_id);
                            userSectionNew.setAllot_at(allot_at);
                            userSectionNew.setResources(resources);
                            userSectionNew.setId_section_start(customer_id_start);
                            userSectionNew.setId_section_end(customer_id_end);
                        } else {
                            userSectionNew.setUser_name(username);
                            userSectionNew.setUser_id(user_id);
                            userSectionNew.setAllot_at(allot_at);
                            userSectionNew.setResources(resources);
                            userSectionNew.setId_section_start(customer_id_start);
                            userSectionNew.setId_section_end(idSectionEndStr);
                        }
                    }
                    if (id_end > idSectionEnd) {
                        customerAllotDao.deleteUserSectionById(id);
                        userSectionNew.setUser_name(username);
                        userSectionNew.setUser_id(user_id);
                        userSectionNew.setResources(resources);
                        userSectionNew.setId_section_start(customer_id_start);
                        userSectionNew.setId_section_end(customer_id_end);
                    }
                }


                if (id_start > idSectionStart && id_start < idSectionEnd) {
                    if (id_end < idSectionStart) {
                        customerAllotDao.deleteUserSectionById(id);
                        if (!user_name_old.equals(username)) {
                            userSectionOld1.setUser_name(user_name_old);
                            userSectionOld1.setUser_id(user_id_old);
                            userSectionOld1.setAllot_at(allot_at);
                            userSectionOld1.setResources(resources_old);
                            userSectionOld1.setId_section_start(idSectionStartStr);
                            userSectionOld1.setId_section_end(StringUtils.leftPad(String.valueOf(id_start - 1), 8, '0'));

                            userSectionOld2.setUser_name(user_name_old);
                            userSectionOld2.setUser_id(user_id_old);
                            userSectionOld2.setAllot_at(allot_at);
                            userSectionOld2.setResources(resources_old);
                            userSectionOld2.setId_section_start(StringUtils.leftPad(String.valueOf(id_end + 1), 8, '0'));
                            userSectionOld2.setId_section_end(idSectionEndStr);

                            userSectionNew.setUser_name(username);
                            userSectionNew.setUser_id(user_id);
                            userSectionNew.setAllot_at(allot_at);
                            userSectionNew.setResources(resources);
                            userSectionNew.setId_section_start(customer_id_start);
                            userSectionNew.setId_section_end(customer_id_end);
                        }
                    }
                    if (id_end > idSectionEnd) {
                        customerAllotDao.deleteUserSectionById(id);
                        if (!user_name_old.equals(username)) {
                            userSectionOld1.setUser_name(user_name_old);
                            userSectionOld1.setUser_id(user_id_old);
                            userSectionOld1.setAllot_at(allot_at);
                            userSectionOld1.setResources(resources_old);
                            userSectionOld1.setId_section_start(idSectionStartStr);
                            userSectionOld1.setId_section_end(StringUtils.leftPad(String.valueOf(id_start - 1), 8, '0'));

                            userSectionNew.setUser_name(username);
                            userSectionNew.setUser_id(user_id);
                            userSectionNew.setAllot_at(allot_at);
                            userSectionNew.setResources(resources);
                            userSectionNew.setId_section_start(customer_id_start);
                            userSectionNew.setId_section_end(customer_id_end);
                        } else {
                            userSectionNew.setUser_name(username);
                            userSectionNew.setUser_id(user_id);
                            userSectionNew.setAllot_at(allot_at);
                            userSectionNew.setResources(resources);
                            userSectionNew.setId_section_start(idSectionStartStr);
                            userSectionNew.setId_section_end(customer_id_end);
                        }
                    }
                }
                if (id_start > idSectionEnd) {

                    userSectionNew.setUser_name(username);
                    userSectionNew.setUser_id(user_id);
                    userSectionNew.setAllot_at(allot_at);
                    userSectionNew.setResources(resources);
                    userSectionNew.setId_section_start(customer_id_start);
                    userSectionNew.setId_section_end(customer_id_end);
                }
            }
            if (userSectionOld1.getId_section_start() != null) {
                customerAllotDao.saveResourceSection(userSectionOld1);
            }
            if (userSectionOld2.getId_section_start() != null) {
                customerAllotDao.saveResourceSection(userSectionOld2);
            }
            if (userSectionNew.getId_section_start() != null) {
                customerAllotDao.saveResourceSection(userSectionNew);
            }
        }
    }

    public int saveCancel(String customer_id_start, String customer_id_end, Integer user_id, String realname) {
        customerAllotDao.saveCancel(customer_id_start, customer_id_end, user_id, realname);
        List<UserSection> list = customerAllotDao.listUserSection();
        int id_start = Integer.valueOf(customer_id_start);
        int id_end = Integer.valueOf(customer_id_end);
        UserSection userSectionOld1 = new UserSection();
        UserSection userSectionOld2 = new UserSection();
        for (UserSection userSection : list) {
            String idSectionStartStr = userSection.getId_section_start();
            int idSectionStart = Integer.valueOf(idSectionStartStr);
            String idSectionEndStr = userSection.getId_section_end();
            int idSectionEnd = Integer.valueOf(idSectionEndStr);
            int user_id_old = userSection.getUser_id();
            String user_name_old = userSection.getUser_name();
            int id = userSection.getId();
            if (id_start < idSectionStart) {

                if (id_end > idSectionStart && id_end < idSectionEnd) {
                    customerAllotDao.deleteUserSectionById(id);
                    userSectionOld2.setUser_name(user_name_old);
                    userSectionOld2.setUser_id(user_id_old);
                    userSectionOld2.setId_section_start(StringUtils.leftPad(String.valueOf(id_end + 1), 8, '0'));
                    userSectionOld2.setId_section_end(idSectionEndStr);
                }
                if (id_end > idSectionEnd) {
                    customerAllotDao.deleteUserSectionById(id);
                }
            }
            if (id_start > idSectionStart && id_start < idSectionEnd) {
                customerAllotDao.deleteUserSectionById(id);
                userSectionOld1.setUser_name(user_name_old);
                userSectionOld1.setUser_id(user_id_old);
                userSectionOld1.setId_section_start(idSectionStartStr);
                userSectionOld1.setId_section_end(StringUtils.leftPad(String.valueOf(id_start - 1), 8, '0'));

                if (id_end < idSectionEnd) {
                    userSectionOld2.setUser_name(user_name_old);
                    userSectionOld2.setUser_id(user_id_old);
                    userSectionOld2.setId_section_start(StringUtils.leftPad(String.valueOf(id_end + 1), 8, '0'));
                    userSectionOld2.setId_section_end(idSectionEndStr);
                }
            }
        }
        return 1;
    }


}
