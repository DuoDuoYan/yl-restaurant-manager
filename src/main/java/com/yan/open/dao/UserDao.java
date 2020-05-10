package com.yan.open.dao;

import com.yan.open.model.Foods;
import com.yan.open.model.Menu;
import com.yan.open.model.Table;
import com.yan.open.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    /**
     *根据phone查看用户
     */
    @Select("select * from user where phone = #{phone}")
    User findByPhone(@Param("phone")String phone);

    @Select("select username from user where phone = #{phone}")
    String exists(@Param("phone")String phone);

    /**
     * 用户登录
     */
    @Select("select * from user where phone = #{phone} and password = #{password}")
    User login(@Param("phone")String phone,@Param("password")String password);

    /**
     * 注册用户
     */
    @Insert("insert into user (username,password,phone,role_id) values(#{username},#{password},#{phone},'1')")
    int register(@Param("username")String username,@Param("password")String password,@Param("phone")String phone);

    /**
     * 修改用户信息
     */
    @Update("<script>"+
            "update user"+
            "<set>"+
            "<if test='username != null'>username = #{username}</if>"+
            "<if test='password != null'>password = #{password}</if>" +
            "<if test='gender != null'>gender = #{gender}</if>" +
            "<if test='age != null'>age = #{age}</if>" +
            "<if test='address != null'>address = #{address}</if>"+
            "</set>" +
            "where phone = #{phone}"+
            "</script>")
    int update(@Param("phone")String phone,@Param("username")String username,@Param("password")String password,@Param("gender")String gender,@Param("age")Integer age,@Param("address")String address);

    /**
     * 查看余额
     */
    @Select("select balance from user where phone = #{phone}")
    Double findBalance(@Param("phone")String phone);

    /**
     * 余额充值
     */
    @Update("update user set balance = #{balance} where phone = #{phone}")
    int invest(@Param("balance")Double balance,@Param("phone") String phone);

    /**
     * 查看所有菜品
     */
    @Select("select m.id,m.food,m.info,m.price,d.now_price,c.catalog_name,m.catalog,m.selled,m.image,m.quantity from menu m \n" +
            "INNER JOIN catalog c on m.catalog = c.id\n" +
            "LEFT JOIN discount d on m.food = d.food ")
    List<Foods> findAllFood();

    @Select("SELECT catalog_name from catalog where status = '0' \n")
    List<String> findCatalog();

    @Select("SELECT * FROM `table` \n")
    List<Table> findTables();


    @Select("select image from menu")
    List<String> findAllImage();
    /**
     * 生成订单详情
     */
    @Insert("insert into order_detail (order_num,food,quantity,price) values(#{orderNum},#{food},#{quantity},#{price})")
    int createOrderDetail(@Param("orderNum")String orderNum,@Param("food")int food,@Param("quantity")int quantity,@Param("price")double price);

    /**
     * 生成订单
     */
    @Insert("insert into orders (table_id,robot_id,order_date,order_status,total_price,customer,cooker) values(#{tableId},#{robotId},#{orderDate},#{orderStatus},#{totalPrice},#{customer},#{cooker})\n")
    int createOrder(@Param("tableId")String tableId,@Param("robotId")String robotId,@Param("orderDate")String orderDate,@Param("orderStatus")int orderStatus,@Param("totalPrice")double totalPrice,@Param("customer")String customer,@Param("cooker")String cooker);

    /**
     *根据phone查找订单id
     */
    @Select("select id from orders where phone = #{phone}")
    int findOrderIdByPhone(@Param("phone")String phone);

    @Select("select price from food where id = #{id}")
    double findPrice(@Param("id")int id);

}
