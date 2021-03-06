package com.yan.open.service;

import com.yan.open.model.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.util.List;

@Service
public interface UserService {


    User login(String phone,String password);

    String exists(String phone);

    int register(User user);

    int updateUser(User user);

    List<Foods> findAllFoods(HttpServletRequest request);

    List<String> findCatalog();

    List<Table> findTables();

    Double findBalance(String phone);

    int updateBalance(String phone,Double price);

    int createOrder(Order order);

    List<Order> getOrder(String phone);

    List<Order> getOnOrders();

    List<Order> getOrdersByCooker(String phone);

    List<OrderDetail> getOrderDetailByOrderNum(Integer orderNum,String tables);

    int updateOrder(Order order);
}
