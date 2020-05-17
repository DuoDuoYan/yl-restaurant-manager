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
import javax.swing.*;
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
    public int updateBalance(String phone,Double price) {
        Double origBalance = userDao.findBalance(phone);
        return userDao.invest(origBalance - price,phone);
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
        Long date = order.getOrderDate();
        Double totalPrice = order.getTotalPrice();
        int tag = 0;
        tag = userDao.createOrder(table,"-1",date,1,totalPrice,customer,"-1");
        if(tag >0){
            int orderID = userDao.findOrderIdByPhone(date,customer);

            //生成订单详情
            for (Map.Entry<Integer,Integer> entry : map.entrySet()){
                Double price =  userDao.findPrice(entry.getKey());
                tag = userDao.createOrderDetail(String.valueOf(orderID),entry.getKey(),entry.getValue(),price);
                Menu menu = userDao.getFoodSelledQuantity(entry.getKey());
                int quantity = menu.getQuantity();
                int selled = menu.getSelled();
                tag = userDao.updateFoodSelledQuantity(entry.getKey(),selled,quantity);
                if(tag < 0){
                    break;
                }
            }
            return orderID;
        }
        return -1;
    }

    @Override
    public List<Order> getOrder(String phone) {
        List<Order> list = userDao.getOrder(phone);

        for(int i=0;i<list.size();i++) {
            if (list.get(i).getOrderStatus() == 0) {
                list.get(i).setStatus("接单中");
            }
            if (list.get(i).getOrderStatus() == 1) {
                list.get(i).setStatus("制作中");
            }
            if (list.get(i).getOrderStatus() == 2) {
                list.get(i).setStatus("送餐中");
            }
            if (list.get(i).getOrderStatus() == 3) {
                list.get(i).setStatus("已完成");
            }
        }
        return list;
    }

    @Override
    public List<Order> getOnOrders() {
        List<Order> orders = userDao.getOnOrders(0);
        log.info("dao:"+orders);
        for(int i=0;i<orders.size();i++){
            List<OrderDetail> orderDetails = userDao.getOrderDetail(orders.get(i).getId());
            orders.get(i).setOrderDetail(orderDetails);
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByCooker(String phone) {
        return userDao.getOrdersByCooker(phone);
    }

    @Override
    public List<OrderDetail> getOrderDetailByOrderNum(Integer orderNum,String tables) {
        return userDao.getOrderDetailByOrderNum(orderNum,tables);
    }

    @Override
    public int updateOrder(Order order) {
        String tag = order.getTag();
        if("1".equals(tag)){
            String cooker = order.getCooker();
            Integer id = order.getId();
            Integer status = 1;
            return userDao.updateOrderCookerStatus(cooker,status,id);
        }
        if("2".equals(tag)){
            String robot = order.getRobotNum();
            Integer id = order.getId();
            Integer status = 2;
            return userDao.updateOrderRobotStatus(robot,status,id);
        }
        if("3".equals(tag)){
            Integer id = order.getId();
            Integer status = 3;
            return userDao.updateOrderStatus(status,id);
        }
        return -1;
    }
}
