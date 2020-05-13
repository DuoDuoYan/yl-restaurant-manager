package com.yan.open.dao;

import com.yan.open.model.*;
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
    @Insert("insert into orders (table_num,robot_num,order_date,order_status,total_price,customer,cooker) values(#{tableId},#{robotId},#{orderDate},#{orderStatus},#{totalPrice},#{customer},#{cooker})\n")
    int createOrder(@Param("tableId")String tableId,@Param("robotId")String robotId,@Param("orderDate")Long orderDate,@Param("orderStatus")int orderStatus,@Param("totalPrice")double totalPrice,@Param("customer")String customer,@Param("cooker")String cooker);

    /**
     *根据phone,date查找订单id
     */
    @Select("select id from orders where customer = #{customer} and order_date = #{date}")
    int findOrderIdByPhone(@Param("date")Long date,@Param("customer")String customer);

    @Select("select price from menu where id = #{id}")
    double findPrice(@Param("id")Integer id);

    @Select("select o.id,table_num,r.num,order_date,order_status,total_price,customer,cooker from orders o\n" +
            "LEFT JOIN robot r ON o.robot_num = r.id\n" +
            "where customer = #{phone} order by id desc")
    List<Order> getOrder(@Param("phone")String phone);

    @Select("select o.id,t.num as tables,r.num as robot,order_date,order_status,total_price,customer,cooker \n" +
            "from orders o\n" +
            "LEFT JOIN robot r ON o.robot_num = r.id\n" +
            "LEFT JOIN `table` t on t.id = o.table_num \n" +
            "order by id desc")
    List<Order> getOnOrders();

    @Select("select o.id,o.order_num,m.food as foodName,o.quantity,o.price from order_detail o \n" +
            "LEFT JOIN menu m on o.food = m.id\n" +
            "where order_num = #{id}")
    List<OrderDetail> getOrderDetail(@Param("id")Integer id);

    @Select("select * from orders where cooker = #{phone}")
    List<Order> getOrdersByCooker(@Param("phone")String phone);

    @Select("select m.food,t.num as tables,t.position,d.quantity " +
            "from order_detail d,`table` t,menu m\n" +
            "where d.food = m.id and t.num = #{tables} and d.order_num = #{orderNum}")
    List<OrderDetail> getOrderDetailByOrderNum(@Param("orderNum")Integer orderNum,@Param("tables")String tables);

    @Update("UPDATE orders set cooker = #{cooker} ,order_status= #{status} WHERE id = #{id}\n")
    int updateOrderCookerStatus(@Param("cooker")String cooker,@Param("status")Integer status,@Param("id")Integer id);

    @Update("UPDATE orders set robot_num = #{robot} ,order_status= #{status} WHERE id = #{id}\n")
    int updateOrderRobotStatus(@Param("robot")String robot,@Param("status")Integer status,@Param("id")Integer id);

    @Update("UPDATE orders set order_status= #{status} WHERE id = #{id}\n")
    int updateOrderStatus(@Param("status")Integer status,@Param("id")Integer id);

}
