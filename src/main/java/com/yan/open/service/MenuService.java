package com.yan.open.service;

import com.sun.org.apache.xml.internal.resolver.Catalog;
import com.yan.open.model.Discount;
import com.yan.open.model.FoodCatalog;
import com.yan.open.model.Menu;
import com.yan.open.model.PageData;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface MenuService {

    /**
     * 查看所有菜单信息
     * @return
     */
    List<Menu> findAllFood(PageData pageData);


    /**
     * 计算数据总数
     * @return
     */
    Integer countFood();


    Integer countDiscount();

    Integer countCatalog();


    /**
     * 修改餐单信息
     * @param menu
     * @return
     */
    Integer updateMenu(Menu menu);

    /**
     * 根据菜名查找菜品信息
     * @param food
     * @return
     */
    Menu findByFood(String food);

    /**
     * 添加菜品信息
     * @param menu
     * @return
     */
    Integer addMenu(Menu menu);

    /**
     * 根据id删除menu
     * @param id
     * @return
     */
    Integer deleteMenu(Integer id);

    /**
     * 根据id查找图片信息
     * @param id
     * @return
     */
    String findPhotoById(Integer id);

    /**
     * 根据catalog查找菜品
     * @param catalog
     * @return
     */
    List<Menu> findFoodsByCatalog(Integer catalog);

    /**
     * 根据id查找菜品价格
     * @param id
     * @return
     */
    String findPriceById(Integer id);
    /**
     * 查看菜品分类信息
     * @return
     */
    List<FoodCatalog> findAllCatalog(PageData pageData);

    /**
     * 添加菜品分类信息
     * @param foodCatalog
     * @return
     */
    Integer addCatalog(FoodCatalog foodCatalog);

    /**
     * 修改菜品分类信息
     * @param foodCatalog
     * @return
     */
    Integer updateCatalog(FoodCatalog foodCatalog);

    List<Menu> findAllDiscount(PageData pageData);

    int addDiscount(String food, Double price);

    int updateDiscount(String food,Double price);

    int recoveryPrice(Integer id);

    List<FoodCatalog> findAllCatalogName();

}
