package com.yan.open.controller;

import com.yan.open.model.*;
import com.yan.open.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/register")
    public @ResponseBody String register(@RequestBody User user) {

        int tag = userService.register(user);
        if(tag == 1){
            return "success";
        }
        return "error";
    }

    @RequestMapping("/findByPhone")
    public @ResponseBody String exists(@RequestBody User user) {

        String result = userService.exists(user.getPhone());

        return result;
    }

    @RequestMapping("/login")
    public @ResponseBody User login(@RequestBody User user) {
        String phone = user.getPhone();
        String password = user.getPassword();
        User u = userService.login(phone, password);
        if(u == null){
            return null;
        }
        return u;
    }

    @RequestMapping("/update")
    public @ResponseBody String updateUser(){

        return "success";
    }

    @RequestMapping("/findAllFoods")
    public @ResponseBody List<Foods> findAllFoods(HttpServletRequest httpServletRequest){
        List<Foods> lists = userService.findAllFoods(httpServletRequest);
        return lists;
    }

    @RequestMapping("/catalogs")
    public @ResponseBody List<String> findCatalog(){
        List<String> catalogs = userService.findCatalog();
        return catalogs;
    }

    @RequestMapping("/findTables")
    public @ResponseBody List<Table> findTables(){
        List<Table> table = userService.findTables();
        return table;
    }

    @RequestMapping("/findBalance")
    public @ResponseBody Double findBalance(@RequestBody User user){
        log.info("findBAlance:"+user.getPhone());
        Double balance = userService.findBalance(user.getPhone());
        return balance;
    }

    @RequestMapping("/updateBalance")
    public @ResponseBody String updateBalance(@RequestBody User user){
        log.info("updateBalance:"+user.getPhone()+";"+user.getBalance());
        int tag = userService.updateBalance(user.getPhone(),user.getBalance());
        if(tag>0){
            return "success";
        }
        log.info("移动端支付："+tag);
        return "error";
    }

    @RequestMapping("/createOrder")
    public @ResponseBody int createOrder(@RequestBody Order order){
        log.info("order:"+order);
        int orderId = userService.createOrder(order);

        log.info("下单成功："+orderId);
        return orderId;
    }

    @RequestMapping("/getOrderByCustomer")
    public @ResponseBody List<Order> getOrderStatus(@RequestBody Order order){
        log.info("order:"+order.getCustomer());
        List<Order> orders = userService.getOrder(order.getCustomer());

        log.info("订单："+orders);
        return orders;
    }

    @RequestMapping("/getOrdersByCooker")
    public @ResponseBody List<Order> getOrdersByCooker(@RequestBody Order order){
        List<Order> orders = userService.getOrdersByCooker(order.getCooker());

        log.info("已接订单："+orders);
        return orders;
    }

    @RequestMapping("/getOnOrders")
    public @ResponseBody List<Order> getOnOrders(){
        List<Order> orders = userService.getOnOrders();

        log.info("代接订单："+orders);
        return orders;
    }

    @RequestMapping("/getOrderDetailByOrderNum")
    public @ResponseBody List<OrderDetail> getOrderDetailByOrderNum(@RequestBody OrderDetail orderDetail){
        List<OrderDetail> orderDetails = userService.getOrderDetailByOrderNum(Integer.valueOf(orderDetail.getOrderNum()),orderDetail.getTables());

        log.info("订单详情cooker："+orderDetails);
        return orderDetails;
    }

    @RequestMapping("/updateOrderStatus")
    public @ResponseBody String updateOrder(@RequestBody Order order){
        int tag = userService.updateOrder(order);
        if(tag>0){
            return "success";
        }
        return "error";
    }

}