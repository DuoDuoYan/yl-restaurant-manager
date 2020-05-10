package com.yan.open.service;

import com.yan.open.model.PageData;
import com.yan.open.model.Permission;
import com.yan.open.model.User;
import org.springframework.stereotype.Service;

import java.beans.PersistenceDelegate;
import java.util.List;

@Service
public interface AdminService {

    /**
     * 身份验证 根据账号获取账号密码
     * @param phone
     * @return
     */
    User findByName(String phone);

    /**
     * 根据账号获取该账号的权限
     * @param user
     * @return
     */
    List<Permission> getPermissions(User user);

    /**
     * 注册
     * @param user
     * @return
     */
    Integer register(User user);

    /**
     * 根据phone查找用户信息
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    Integer updateUser(User user,String phone);

    /**
     * 查找所有管理员用户
     * @return
     */
    List<User> findAllAdmin(PageData pageData);

    /**
     *查找所有厨师用户
     * @return
     */
    List<User> findAllCooker(PageData pageData);

    /**
     * 查找所有顾客用户
     * @return
     */
    List<User> findAllCustomer(PageData pageData);


    /**
     * 根据id删除用户信息
     * @param id
     * @return
     */
    Integer deleteUser(Integer id);

    /**
     * 添加厨师信息
     * @param user
     * @return
     */
    Integer addCooker(User user);

    /**
     * 添加顾客信息
     * @param user
     * @return
     */
    Integer addCustomer(User user);

    /**
     * 数据总数
     * @return
     */
    Integer countCustomer();

    Integer countAdmin();

    Integer countCooker();

}
