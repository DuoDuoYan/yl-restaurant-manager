package com.yan.open.dao;

import com.yan.open.model.Order;
import com.yan.open.model.OrderDetail;
import com.yan.open.model.Sales;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderDao {

    /**
     *查看所有订单
     */
    @Select("<script>" +
            "SELECT o.id,t.num table_num,r.num robot_num,o.order_date,o.order_status,o.total_price,a.username as customer,b.username as cooker \n" +
            "FROM user as a,user as b,orders as o,`table` t,robot r\n" +
            "where a.phone = o.customer and b.phone = o.cooker and o.table_id = t.id and o.robot_id = r.id\n" +
            "<if test='startDate != null'>and o.order_date &gt;= #{startDate}</if>\n" +
            "<if test='endDate != null'>and o.order_date &lt;= #{endDate}</if>\n" +
            "<if test='condition != null'>and o.id = #{condition}</if>\n" +
            "limit #{start},#{end}" +
            "</script>")
    List<Order> findOrders(@Param("start")Integer start,@Param("end")Integer end,@Param("startDate")Long startDate,@Param("endDate")Long endDate,@Param("condition")String condition);

    /**
     * 查看订单详情
     * @param num
     * @return
     */
    @Select("select m.food,d.quantity,d.price from order_detail d INNER JOIN menu m on m.id = d.food \n" +
            "where d.order_num = #{num}")
    List<OrderDetail> findByOrderNum(@Param("num")int num);

    /**
     * 查看某个月的销售情况
     * @param date
     * @return
     */
    @Select("SELECT DAY(o.order_date ) as days,c.catalog_name,m.price,d.quantity,SUM(m.price*d.quantity) as total_price " +
            "FROM order_detail d " +
            "INNER JOIN orders o on d.order_num = o.order_num " +
            "INNER JOIN menu m on d.food_id = m.id " +
            "INNER JOIN catalog c on m.catalog = c.id " +
            "WHERE DATE_FORMAT( o.order_date, '%Y%m' ) = DATE_FORMAT( #{date} , '%Y%m' )" +
            "GROUP BY DAY(o.order_date),c.catalog_name ")
    List<Sales> findSales(@Param("date")String date);


    /**
     * 计算数据总数
     * @return
     */
    @Select("select COUNT(id) from orders")
    int countOrders();

}
