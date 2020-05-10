package com.yan.open.service.impl;

import com.yan.open.dao.RechargeDao;
import com.yan.open.model.User;
import com.yan.open.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private RechargeDao rechargeDao;

    @Override
    public User findByPhone(String phone) {
        User u = rechargeDao.findByPhone(phone.trim());
        return u;
    }

    @Override
    public Integer addBalance(String balance, String phone) {
        User u = rechargeDao.findByPhone(phone);
        Double sum = Double.valueOf(balance) + u.getBalance();
        return rechargeDao.addBalance(phone.trim(), sum);
    }
}
