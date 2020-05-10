package com.yan.open.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.open.model.PageData;
import com.yan.open.model.Result;
import com.yan.open.model.User;
import com.yan.open.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/restaurant",produces = "application/json;charset=UTF-8")
public class AdminController {
    @Autowired
    private AdminService adminService;

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/login")
    public ModelAndView login(HttpSession session){
        session.removeAttribute("msg");
        return new ModelAndView("login");
    }

    @RequestMapping("/register")
    public ModelAndView register(HttpSession session){
        session.removeAttribute("msg");
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/toLogin")
    public ModelAndView toLogin(User user,String verifyCode, HttpSession session) {
        try {
            if (StringUtils.isEmpty(user.getPhone()) || StringUtils.isEmpty(user.getPassword())) {
                session.setAttribute("msg","账号或密码不能为空！");
                return new ModelAndView("login");
            }
            session.removeAttribute("msg");
            String code = (String) session.getAttribute("verifyCode");
            log.info("code:"+code);
            if(!verifyCode.equals(code)){
                session.setAttribute("msg","验证码错误！");
                return new ModelAndView("login");
            }
            String phone = user.getPhone();
            String password = user.getPassword();
            //主体。当前状态为没有认证的状态“未认证”
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                return new ModelAndView("main");
            }
            //登录后存放进shiro token
            UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
            //remeberme
//            token.setRememberMe(true);
            //使用subject调用securityManager，安全管理器用Realm
            //需要开始调用到realm 执行登录.
            subject.login(token);
            session.setAttribute("user", subject.getPrincipal());
        } catch (AuthenticationException e) {
            log.info("登陆失败：");
            session.setAttribute("msg", "账号或密码错误，请重新输入！");
            return new ModelAndView("login");
        }
        return new ModelAndView("main");
    }

    @RequestMapping("/toRegister")
    public String register(User user,String repassword,HttpSession session){
        session.removeAttribute("msg");

        if(!user.getPassword().equals(repassword)){
            session.setAttribute("msg","两次输入的密码不一致！");
            return "register";
        }
        String phone = user.getPhone();
        String password = new SimpleHash("MD5",user.getPassword(), ByteSource.Util.bytes(user.getPhone()),1024).toHex();
        User u = adminService.findByPhone(phone);
        if(u == null){
            User admin = new User();
            admin.setPhone(phone);
            admin.setPassword(password);
            int tag = adminService.register(admin);
            return "login";
        }else{
            session.setAttribute("msg","该手机号已被注册");
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String loginOut(HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout(); //session会销毁，在SessionListener监听session销毁，清理权限缓存
        }
        return "login";
    }

    @RequestMapping("/unauthorized")
    public String noPermission(){
        return "unauthorized";
    }

    @RequestMapping("/findAllAdmin")
    @ResponseBody
    public PageData findAllAdmin(PageData page){

        PageData data = new PageData();
        if(null == page.getPageNo() || null == page.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(5);
        }else{
            data.setPageNo(page.getPageNo());
            data.setPageSize(page.getPageSize());
        }
        data.setTotal(adminService.countAdmin());
        List<User> admins = adminService.findAllAdmin(data);
        data.setData(admins);
        return data;
    }

    @ResponseBody
    @RequestMapping("/findAllCooker")
    public PageData findAllCooker(PageData pageData){
        PageData data = new PageData();
        if(null == pageData.getPageNo() || null == pageData.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(5);
        }else{
            data.setPageNo(pageData.getPageNo());
            data.setPageSize(pageData.getPageSize());
        }
        data.setTotal(adminService.countCooker());
        List<User> admins = adminService.findAllCooker(data);
        data.setData(admins);
        return data;
    }

    @ResponseBody
    @RequestMapping("/findAllCustomer")
    public PageData findAllCustomer(PageData pageData){
        PageData data = new PageData();
        if(null == pageData.getPageNo() || null == pageData.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(5);
        }else{
            data.setPageNo(pageData.getPageNo());
            data.setPageSize(pageData.getPageSize());
        }
        data.setTotal(adminService.countCustomer());
        List<User> admins = adminService.findAllCustomer(data);
        data.setData(admins);
        return data;
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public Result deleteById(String id){
        Result result = new Result();
        result.setCode(adminService.deleteUser(Integer.valueOf(id)));
        if(result.getCode() > 0){
            result.setMessage("删除成功！");
        }else{
            result.setMessage("删除失败！");
        }
        return result;
    }


    @RequestMapping("/findByPhone")
    @ResponseBody
    public String findByPhone(String phone){
        boolean flag = false;
        String stringJson = null;
        User u = adminService.findByPhone(phone);
        if(u == null) {
            flag = true;
        }
        Map<String,Boolean> map = new HashMap<>();
        map.put("valid",flag);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            stringJson = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return stringJson;
    }

    @RequestMapping("/findUserByPhone")
    @ResponseBody
    public User findUserByPhone(String phone){
        User user = adminService.findByPhone(phone.trim());
        return user;
    }

    @RequestMapping("/addAdmin")
    @ResponseBody
    public Result addAdmin(User user){
        Result result = new Result();
        String password = new SimpleHash("MD5",user.getPassword(), ByteSource.Util.bytes(user.getPhone()),1024).toHex();
        user.setPassword(password);
        result.setCode(adminService.register(user));
        return result;
    }

    @RequestMapping("/addCooker")
    @ResponseBody
    public Result addCooker(User user){
        Result result = new Result();
        result.setCode(adminService.addCooker(user));
        return result;
    }

    @RequestMapping("/addCustomer")
    @ResponseBody
    public Result addCustomer(User user){
        Result result = new Result();
        result.setCode(adminService.addCustomer(user));
        return result;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public Result updateAdmin(User user){
        Result result = new Result();
        result.setCode(adminService.updateUser(user,user.getPhone()));
        if(result.getCode() > 0){
            result.setMessage("修改成功！");
        }else{
            result.setMessage("修改失败！");
        }
        return result;
    }
    /**
     * 加载iframe
     * @return
     */
    @RequestMapping("/default")
    public String defaulIframe(){
        return "default";
    }

    @RequestMapping("/admin")
    public String adminIframe(){
        return "admin";
    }

    @RequestMapping("/catalog")
    public String catalogIframe(){
        return "catalog";
    }

    @RequestMapping("/cooker")
    public String cookerIframe(){
        return "cooker";
    }

    @RequestMapping("/customer")
    public String customerIframe(){
        return "customer";
    }

    @RequestMapping("/recharge")
    public String rechargeIframe(){
        return "recharge";
    }

    @RequestMapping("/discount")
    public String discountIframe(){
        return "discount";
    }

    @RequestMapping("/menu")
    public String menuIframe(){
        return "menu";
    }

    @RequestMapping("/order")
    public String orderIframe(){
        return "order";
    }

    @RequestMapping("/robot")
    public String robotIframe(){
        return "robot";
    }
    @RequestMapping("/sales")
    public String salesIframe(){
        return "sales";
    }

    @RequestMapping("/table")
    public String tableIfram(){
        return "table";
    }
}
