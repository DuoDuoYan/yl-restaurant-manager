package com.yan.open.dao;

import com.yan.open.model.FoodCatalog;
import com.yan.open.model.Sales;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SaleDao {

    /**
     * 查看某个月的销售情况
     * @param date
     * @return
     */
    @Select("SELECT DAY(FROM_UNIXTIME(o.order_date/1000))as day,DATE_FORMAT(FROM_UNIXTIME(o.order_date/1000),\"%Y-%m-%d\")as date,c.catalog_name,SUM(d.price*d.quantity) as price,SUM(d.quantity) as quantity\n" +
            "FROM order_detail d \n" +
            "INNER JOIN orders o on d.order_num = o.id \n" +
            "INNER JOIN menu m on d.food = m.id \n" +
            "INNER JOIN catalog c on m.catalog = c.id \n" +
            "where date_format(FROM_UNIXTIME(o.order_date/1000),'%Y-%m')=date_format(#{date},'%Y-%m')\n" +
            "GROUP BY date,c.catalog_name")
    List<Sales> findSalesBy(@Param("date")String date);

    @Select("select catalog_name from catalog where status = '0'\n")
    List<Object> findAllCatalog();
}
