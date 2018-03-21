package com.balance.statistics.service;

import com.balance.customer.model.User;
import com.balance.statistics.dao.StatisticsBusinessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liukai on 2018/3/20.
 */
@Service
public class StatisticsBusinessService {
    @Autowired
private StatisticsBusinessDao statisticsBusinessDao;
    public List<User> findUserList(){
        return statisticsBusinessDao.findUserList();
    }
}
