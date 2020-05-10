package com.yan.open.controller;

import com.yan.open.model.Result;
import com.yan.open.model.User;
import com.yan.open.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restaurant")
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @RequestMapping("/findByPhone")
    @ResponseBody
    public User findByPhone(String phone){
        User u = rechargeService.findByPhone(phone);
        return u;
    }

    @ResponseBody
    @RequestMapping("/addBanlance")
    public Result addBalance(String phone,String balance){
        Result result = new Result();
        result.setCode(rechargeService.addBalance(balance,phone));
        return result;
    }
}
