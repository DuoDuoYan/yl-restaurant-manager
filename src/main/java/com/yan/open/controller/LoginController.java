package com.yan.open.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpSession;

@Controller
@Scope(value="prototype")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public ModelAndView login(HttpSession session){
        log.info("login");
        session.removeAttribute("msg");
        return new ModelAndView("restaurant/toLogin");

    }

    @RequestMapping("/register")
    public ModelAndView register(HttpSession session){
        return new ModelAndView("restaurant/toRegister");
    }
}
