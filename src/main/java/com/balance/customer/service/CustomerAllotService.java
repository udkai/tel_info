package com.balance.customer.service;

import com.balance.common.dao.CommonDao;
import com.balance.customer.VO.CustomerAllotSearchVO;
import com.balance.customer.VO.CustomerInfoSearchVO;
import com.balance.customer.dao.CustomerAllotDao;
import com.balance.customer.dao.CustomerInfoDao;
import com.balance.customer.model.CustomerInfo;
import com.balance.customer.model.User;
import com.balance.util.excel.Excel2007Util;
import com.balance.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

   public List<User>getUser(){
        return customerAllotDao.getUser();
   }

    /**
     *
     * @param customer_id_start
     * @param customer_id_end
     * @param user_id
     * @param username
     * @return
     */
    public int saveAllot(String customer_id_start, String customer_id_end,int user_id, String username) {
        customerAllotDao.saveAllot(customer_id_start,customer_id_end, user_id, username);
        return 1;
    }

    public int saveCancel(String customer_id_start, String customer_id_end,String realname){
        customerAllotDao.saveCancel(customer_id_start,customer_id_end,realname);
        return 1;
    }



}
