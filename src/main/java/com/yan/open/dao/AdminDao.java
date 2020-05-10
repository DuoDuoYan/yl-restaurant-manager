package com.yan.open.dao;

import com.yan.open.model.Order;
import com.yan.open.model.Permission;
import com.yan.open.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface AdminDao {

    /**
     * 身份验证
     */
    @Select("select * from user where phone = #{phone} and role_id = '3'")
    User findByName(@Param("phone")String phone);

    /**
     * 根据用户id获取用户角色id
     */
    @Select("select role_id from user where id = #{id}")
    int getRoleIdByUserId(@Param("id")Integer id);

    /**
     * 根据用户角色id获取permission
     */
    @Select("select * from permission where role_id = #{roleId}")
    List<Permission> getPermissionsByRoleId(@Param("roleId")Integer roleId);

    /**
     * 注册
     */
    @Select("select * from user where phone = #{phone}")
    User selectByPhone(@Param("phone")String phone);

    @Insert("insert into user (username,password,phone,gender,age,address,role_id) values(#{username},#{password},#{phone},#{gender},#{age},#{address},'3')")
    int register(@Param("username")String username, @Param("password")String password, @Param("phone")String phone, @Param("gender")String gender, @Param("age")Integer age, @Param("address")String address);

    /**
     * 添加厨师信息
     */
    @Insert("insert into user (username,password,phone,gender,age,address,role_id) values(#{username},#{password},#{phone},#{gender},#{age},#{address},'4')")
    int addCooker(@Param("username")String username, @Param("password")String password, @Param("phone")String phone, @Param("gender")String gender, @Param("age")Integer age, @Param("address")String address);

    /**
     * 添加顾客信息
     */
    @Insert("insert into user (username,password,phone,gender,age,address,role_id) values(#{username},#{password},#{phone},#{gender},#{age},#{address},'1')")
    int addCustomer(@Param("username")String username, @Param("password")String password, @Param("phone")String phone, @Param("gender")String gender, @Param("age")Integer age, @Param("address")String address);

    @Select("select role_id from user where id = #{id}")
    Integer findRoleById(@Param("id")Integer id);

    /**
     * 删除用户信息前先删除被依赖的数据
     */
    @Delete("delete from order_detail where order_id = (select o.id from orders o INNER JOIN user u on o.customer = u.phone where u.id = #{id}))")
    int deleteOrderDetail(@Param("id")Integer id);

    @Delete("delete from orders where customer in (select phone from user where id = #{id})")
    int deleteCustomerOrder(@Param("id")Integer id);

    @Delete("delete from orders where cooker in (select phone from user where id = #{id})")
    int deleteCookerOrder(@Param("id")Integer id);

    /**
     * 删除用户信息
     */
    @Delete("delete from user where id = #{id}")
    int deleteUser(@Param("id")Integer id);

    /**
     * 修改用户信息
     */
    @Update("<script>" +
            "update user" +
            "<trim prefix='set' suffixOverrides=','>" +
            "<if test='username != null'>username = #{username},</if>" +
            "<if test='gender != null'>gender = #{gender},</if>" +
            "<if test='age != null'>age = #{age},</if>" +
            "<if test='address != null'>address = #{address},</if>" +
            "</trim>" +
            "where phone = #{phone}" +
            "</script>")
    int updateUser(@Param("phone")String phone,@Param("username")String username,@Param("gender")String gender,@Param("age")Integer age,@Param("address")String address);

    /**
     * 查看所有管理员信息
     */
    @Select("select * from user where role_id = '3' LIMIT #{start},#{end}")
    List<User> findAllAdmin(@Param("start")Integer start,@Param("end")Integer end);

    /**
     * 查看所有厨师信息
     */
    @Select("select * from user where role_id = '4' LIMIT #{start},#{end}")
    List<User> findAllCooker(@Param("start")Integer start,@Param("end")Integer end);

    /**
     * 查看所有顾客员信息
     */
    @Select("select * from user where role_id = '1' LIMIT #{start},#{end}")
    List<User> findAllCustomer(@Param("start")Integer start,@Param("end")Integer end);

    /**
     * 计算数据总数
     */
    @Select("select COUNT(id) from user where role_id = '1'")
    int countCustomer();

    @Select("select COUNT(id) from user where role_id = '3'")
    int countAdmin();

    @Select("select COUNT(id) from user where role_id = '4'")
    int countCooker();

}
