package com.yan.open.dao;

import com.yan.open.model.Discount;
import com.yan.open.model.FoodCatalog;
import com.yan.open.model.Menu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface MenuDao {

    //菜单
    /**
     * 根据name查看menu信息
     * @param food
     * @return
     */
    @Select("select * from menu where food = #{food}")
    Menu findByName(@Param("food")String food);

    /**
     * 根据id查看图片信息
     * @param id
     * @return
     */
    @Select("select image from menu where id = #{id}")
    String findPhotoById(@Param("id")Integer id);

    /**
     * 添加菜品信息
     */
    @Insert("insert into menu (food,info,price,quantity,image,catalog,discount) values(#{name},#{info},#{price},#{quantity},#{image},#{catalog},'0')")  //status :1 上架 0：下架
    int addFood(@Param("name")String name, @Param("info")String info, @Param("price")Double rice, @Param("quantity")Integer quantity, @Param("image")String image, @Param("catalog")Integer catalog);

    @Delete("delete from menu where id = #{id}")
    int deleteFood(@Param("id")Integer id);

    /**
     * 修改菜品信息
     */
    @Update("<script>"+
            "update menu"+
            "<trim prefix='set' suffixOverrides=','>" +
            "<if test='food != null'>food = #{food}, </if>"+
            "<if test='info != info'>null = #{info}, </if>"+
            "<if test='price != null'>price = #{price}, </if>"+
            "<if test='quantity != null'>quantity = #{quantity}, </if>"+
            "<if test='image != null'>image = #{image}, </if>"+
            "<if test='catalog != null'>catalog = #{catalog}, </if>"+
            "</trim>" +
            "where id = #{id}"+
            "</script>")
    int updateMenu(@Param("id")Integer id,@Param("food")String food,@Param("info")String info,@Param("price")Double price,@Param("quantity")Integer quantity,@Param("image")String image,@Param("catalog")Integer catalog);

    /**
     * 查看所有餐单信息
     * @return
     */
    @Select("select m.id,m.food,c.catalog_name,m.catalog,m.info,m.quantity,m.selled,m.image,m.price " +
            "from menu m " +
            "INNER JOIN catalog c on m.catalog = c.id " +
            "limit #{start},#{end}")
    List<Menu> findAllFood(@Param("start")Integer start,@Param("end")Integer end);

    /**
     * 根据catalog查找菜品
     * @param catalog
     * @return
     */
    @Select("select id,food from menu where catalog = #{catalog} and discount = '0' ")
    List<Menu> findFoodsByCatalog(@Param("catalog")Integer catalog);

    @Select("select price from menu where id = #{id}")
    Double findPriceById(@Param("id")Integer id);

    /**
     * 数据总数
     */
    @Select("select COUNT(id) from menu")
    int countMenu();


    //分类
    /**
     * 查看所有有效分类信息
     * @return
     */
    @Select("select * from catalog limit #{start},#{end}")
    List<FoodCatalog> findAllCatalog(@Param("start")Integer start, @Param("end")Integer end);

    @Select("select * from catalog ")
    List<FoodCatalog> findAllFoodCatalogs();


    /**
     * 添加分类
     * @param name
     * @return
     */
    @Select("select * from catalog where catalog_name = #{name}")
    FoodCatalog findByCatalogName(@Param("name")String name);

    @Insert("insert into catalog (catalog_name,status) values(#{name},'0')")
    int addCatalog(@Param("name")String name);

    @Update("<script>"+
            "update catalog"+
            "<trim prefix='set' suffixOverrides=','>" +
            "<if test='catalog_name != null'>catalog_name = #{catalog_name}, </if>"+
            "<if test='status != null'>status = #{status} </if>"+
            "</trim>" +
            "where id = #{id}"+
            "</script>")
    int updateCatalog(@Param("id")Integer id,@Param("catalog_name")String catalog_name,@Param("status")Integer status);

    @Select("select COUNT(id) from catalog where status = '0' ")
    int countCatalog();

    @Select("select food from menu where catalog = #{catalog}")
    List<String> findAllFoodName(@Param("catalog")String catalog);

    @Select("select * from catalog where status = '0'")
    List<FoodCatalog> findAllCatalogName();

    //折扣

    /**
     * 计算折扣数据总数
     * @return
     */
    @Select("select COUNT(id) from discount")
    int countDiscount();

    /**
     * 添加折扣菜品
     * @return
     */

    @Select("select * from discount where food = #{food}")
    Discount selectByFood(@Param("food")Integer food );

    @Insert("insert into discount (food,now_price) values(#{food},#{nowPrice})")
    int addDiscount(@Param("food")Integer food,@Param("nowPrice")Double nowPrice);


    @Update("update menu set discount = '1' where id = #{id} ")
    int updateMenuByFood(@Param("id")Integer food);

    @Select("select food from menu where id = #{id}")
    String findById(@Param("id")Integer id);

    /**
     * 查看所有折扣信息
     * @return
     */
    @Select("select m.id,m.food,c.catalog_name,m.info,m.quantity,m.selled,m.image,m.price,d.now_price " +
            "from discount d " +
            "INNER JOIN menu m on d.food = m.id " +
            "INNER JOIN catalog c on m.catalog = c.id " +
            "limit #{start},#{end}")
    List<Menu> findAllDiscount(@Param("start")Integer start,@Param("end")Integer end);

    /**
     * 恢复原价
     * @param id
     * @return
     */
    @Update("update menu set discount = '0' where id = #{id} ")
    int recoveryPrice(@Param("id")Integer id);

    @Delete("delete from discount where food = #{food}")
    int deleteDiscount(@Param("food")Integer food);

    /**
     * 修改折扣金额
     * @param price
     * @return
     */
    @Update("update discount set now_price = #{price} where food = #{food} ")
    int updateDiscount(@Param("price")Double price,@Param("food")Integer food);
}
