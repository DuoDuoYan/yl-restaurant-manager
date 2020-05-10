package com.yan.open.controller;

import com.alibaba.fastjson.JSON;
import com.yan.open.model.*;
import com.yan.open.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

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
    public @ResponseBody String exists(@RequestBody String phone) {

        String result = userService.exists(phone);

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
    public @ResponseBody String updateBalance(@RequestBody String phone,@RequestBody String price){
        int tag = userService.updateBalance(phone,price);
        if(tag>0){
            return "success";
        }
        log.info("移动端支付："+tag);
        return "error";
    }

    @RequestMapping("/createOrder")
    public @ResponseBody String createOrder(@RequestBody Order order){
        log.info("order:"+order);
        int tag = userService.createOrder(order);
        if(tag>0){
            return "success";
        }
        log.info("下单成功："+tag);
        return "error";
    }

}