package com.yan.open.service.impl;

import com.google.common.collect.Lists;
import com.yan.open.dao.UserDao;
import com.yan.open.model.*;
import com.yan.open.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;


    @Override
    public User login(String phone, String password) {
        String pass = new SimpleHash("MD5",password, ByteSource.Util.bytes(phone),1024).toHex();

        return userDao.login(phone,pass);
    }

    @Override
    public String exists(String phone) {
        return userDao.exists(phone.trim());
    }

    @Override
    public int register(User user) {
        String username = user.getUsername();
        String password = new SimpleHash("MD5",user.getPassword(), ByteSource.Util.bytes(user.getPhone()),1024).toHex();
        String phone = user.getPhone();
        return userDao.register(username,password,phone);
    }

    @Override
    public int updateUser(User user){
        String phone = user.getPhone();
        String username = user.getUsername();
        String password = new SimpleHash("MD5",user.getPassword(),ByteSource.Util.bytes(user.getPhone()),1024).toHex();
        String gender = user.getGender();
        Integer age = user.getAge();
        String address = user.getAddress();
        return userDao.update(phone,username,password,gender,age,address);
    }

    @Override
    public List<Foods> findAllFoods(HttpServletRequest request) {
        List<Foods> menus = userDao.findAllFood();
        return menus;
    }

    @Override
    public List<String> findCatalog() {
        return userDao.findCatalog();
    }

    @Override
    public List<Table> findTables() {
        return userDao.findTables();
    }

    @Override
    public Double findBalance(String phone) {
        return userDao.findBalance(phone);
    }

    @Override
    public int updateBalance(String phone,String price) {
        Double origBalance = userDao.findBalance(phone);
        return userDao.invest(origBalance-Double.valueOf(price),phone);
    }


    @Override
    public int createOrder(Order order) {
        String[] id = order.getIds().split(",");
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < id.length; i ++){
            if(map.containsKey(id[i])){
                map.put(Integer.valueOf(id[i]), map.get(id[i])+1);
            }else{
                map.put(Integer.valueOf(id[i]), 1);
            }
        }


        String table = order.getTableNum();
        String customer = order.getCustomer();
        String date = order.getDate();
        Double totalPrice = order.getTotalPrice();

        userDao.createOrder(table,"-1",date,0,totalPrice,customer,"-1");
        int orderID = userDao.findOrderIdByPhone(customer);
        int tag = 0;
        for (Map.Entry<Integer,Integer> entry : map.entrySet()){
            Double price =  userDao.findPrice(entry.getKey());
            tag = userDao.createOrderDetail(String.valueOf(orderID),entry.getKey(),entry.getValue(),price);
        }
        return tag;
    }

    @Override
    public int createOrderDetail(OrderDetail orderDetail) {
        return 0;
    }
}
