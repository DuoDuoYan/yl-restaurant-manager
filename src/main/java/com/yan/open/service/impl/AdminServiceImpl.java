package com.yan.open.service.impl;

import com.google.common.collect.Lists;
import com.yan.open.dao.AdminDao;
import com.yan.open.model.*;
import com.yan.open.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminDao dao;

    @Override
    public User findByName(String phone){
        User user = dao.findByName(phone);
        return user;
    }

    @Override
    public List<Permission> getPermissions(User user) {
        //获取到用户角色roleId
        Integer roleId = dao.findRoleById(user.getId());
        List<Permission> permissions = Lists.newArrayList();
        if(roleId != null){
            //根据roleId获取permission
            permissions.addAll(dao.getPermissionsByRoleId(roleId));
        }
        return permissions;
    }

    @Override
    public Integer register(User user) {
        Integer tag = dao.register(user.getUsername(),user.getPassword(),user.getPhone(),user.getGender(),user.getAge(),user.getAddress());
        return tag;
    }

    @Override
    public User findByPhone(String phone) {
        User u = dao.selectByPhone(phone);
        return u;
    }

    @Override
    public Integer updateUser(User user,String originalPhone) {
        String username = user.getUsername();
//        String password = new SimpleHash("MD5",user.getPassword(), ByteSource.Util.bytes(user.getPhone()),1024).toHex();
        String gender = user.getGender();
        Integer age = user.getAge();
        String address = user.getAddress();
        return dao.updateUser(originalPhone,username,gender,age,address);
    }

    @Override
    public List<User> findAllAdmin(PageData pageData) {
        Integer start ;
        Integer end;
        Integer pageNo = pageData.getPageNo();
        Integer pageSize = pageData.getPageSize();
        if(pageNo == null || pageSize == null){
            start = null;
            end = null;
        }else{
            start = (pageNo-1)*pageSize;
            end = pageSize;
        }
        return dao.findAllAdmin(start,end);
    }

    @Override
    public List<User> findAllCooker(PageData pageData) {
        Integer start ;
        Integer end;
        Integer pageNo = pageData.getPageNo();
        Integer pageSize = pageData.getPageSize();
        if(pageNo == null || pageSize == null){
            start = null;
            end = null;
        }else{
            start = (pageNo-1)*pageSize;
            end = pageSize;
        }
        return dao.findAllCooker(start,end);
    }

    @Override
    public List<User> findAllCustomer(PageData pageData) {
        Integer start ;
        Integer end;
        Integer pageNo = pageData.getPageNo();
        Integer pageSize = pageData.getPageSize();
        if(pageNo == null || pageSize == null){
            start = null;
            end = null;
        }else{
            start = (pageNo-1)*pageSize;
            end = pageSize;
        }
        return dao.findAllCustomer(start,end);
    }

    @Override
    public Integer deleteUser(Integer id) {
        Integer role = dao.findRoleById(id);
        if(role == 3){
            return dao.deleteUser(id);
        }else if(role == 4){
            dao.deleteCookerOrder(id);
            return dao.deleteUser(id);
        }else{
            dao.deleteCustomerOrder(id);
            return dao.deleteUser(id);
        }
    }

    @Override
    public Integer addCooker(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String phone = user.getPhone();
        String gender = user.getGender();
        Integer age = user.getAge();
        String address = user.getAddress();
        return dao.addCooker(username,password,phone,gender,age,address);
    }

    @Override
    public Integer addCustomer(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String phone = user.getPhone();
        String gender = user.getGender();
        Integer age = user.getAge();
        String address = user.getAddress();
        return dao.addCustomer(username,password,phone,gender,age,address);
    }

    @Override
    public Integer countCustomer() {
        return dao.countCustomer();
    }

    @Override
    public Integer countAdmin() {
        return dao.countAdmin();
    }

    @Override
    public Integer countCooker() {
        return dao.countCooker();
    }
}
