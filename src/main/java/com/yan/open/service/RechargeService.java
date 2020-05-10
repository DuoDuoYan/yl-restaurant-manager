package com.yan.open.service;

import com.yan.open.model.User;
import org.springframework.stereotype.Service;

@Service
public interface RechargeService {

    User findByPhone(String phone);

    Integer addBalance(String balance,String phone);
}
