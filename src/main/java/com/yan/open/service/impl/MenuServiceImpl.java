package com.yan.open.service.impl;

import com.mysql.jdbc.StringUtils;
import com.yan.open.dao.MenuDao;
import com.yan.open.model.Discount;
import com.yan.open.model.FoodCatalog;
import com.yan.open.model.Menu;
import com.yan.open.model.PageData;
import com.yan.open.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findAllFood(PageData pageData) {
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
        List<Menu> menus = menuDao.findAllFood(start,end);
        for(int i=0;i<menus.size();i++){
            menus.get(i).setImage("upload\\"+menus.get(i).getImage());
        }
        return menus;
    }

    @Override
    public Integer deleteMenu(Integer id) {
        return menuDao.deleteFood(id);
    }

    @Override
    public String findPhotoById(Integer id) {
        return menuDao.findPhotoById(id);
    }

    @Override
    public List<Menu> findFoodsByCatalog(Integer catalog) {
        return menuDao.findFoodsByCatalog(catalog);
    }

    @Override
    public String findPriceById(Integer id) {
        String price = String.valueOf(menuDao.findPriceById(id));
        return price;
    }

    @Override
    public Integer updateMenu(Menu menu) {
        Integer id = menu.getId();
        String name= menu.getFood();
        String info = menu.getInfo();
        Double price = menu.getPrice();
        Integer quantity = menu.getQuantity();
        String image = "";
        if(!StringUtils.isNullOrEmpty(menu.getImage())){
            image = menu.getImage();
        }
        Integer catalog = menu.getCatalog();
        return menuDao.updateMenu(id,name,info,price,quantity,image,catalog);
    }

    @Override
    public Menu findByFood(String food) {
        return menuDao.findByName(food);
    }

    @Override
    public Integer addMenu(Menu menu) {
        String food = menu.getFood();
        if(menuDao.findByName(food) == null){
            String info = menu.getInfo();
            String image = "";
            if(!StringUtils.isNullOrEmpty(menu.getImage())){
                image = menu.getImage();
            }
            Double price = menu.getPrice();
            Integer catalog = menu.getCatalog();
            Integer quantity = menu.getQuantity();
            return menuDao.addFood(food,info,price,quantity,image,catalog);
        }else{
            return 0;
        }
    }

    @Override
    public List<FoodCatalog> findAllCatalog(PageData pageData) {
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
        List<FoodCatalog> lists = menuDao.findAllCatalog(start,end);
        for (int i=0;i<lists.size();i++){
            if(lists.get(i).getStatus() == 1){
                lists.get(i).setStatusName("禁用");
            }else{
                lists.get(i).setStatusName("使用");
            }
        }
        return lists;
    }

    @Override
    public Integer addCatalog(FoodCatalog foodCatalog) {
        String name = foodCatalog.getCatalogName();
        if(menuDao.findByCatalogName(name) == null){
            menuDao.addCatalog(name);
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public Integer updateCatalog(FoodCatalog foodCatalog) {
        Integer id = foodCatalog.getId();
        String name = foodCatalog.getCatalogName();
        Integer status = foodCatalog.getStatus();
        return menuDao.updateCatalog(id,name,status);
    }

    @Override
    public Integer countFood() {
        return menuDao.countMenu();
    }

    @Override
    public Integer countCatalog() {
        return menuDao.countCatalog();
    }



    @Override
    public Integer countDiscount() {
        return menuDao.countDiscount();
    }

    @Override
    public List<Menu> findAllDiscount(PageData pageData) {
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
        List<Menu> menus = menuDao.findAllDiscount(start,end);
        List<FoodCatalog> catalogs = menuDao.findAllFoodCatalogs();
        for(int i=0;i<menus.size();i++){
            menus.get(i).setImage("upload\\"+menus.get(i).getImage());
            for(int j =0;j<catalogs.size();j++){
                if(catalogs.get(j).getId().equals(menus.get(i).getCatalog())){
                    menus.get(j).setCatalogName(catalogs.get(i).getCatalogName());
                }
            }
        }
        return menus;
    }

    @Override
    public int addDiscount(String food, Double price) {
        Discount discount = menuDao.selectByFood(Integer.valueOf(food));
        if(discount == null){
            menuDao.updateMenuByFood(Integer.valueOf(food));
            menuDao.addDiscount(Integer.valueOf(food),price);
        }else{
            return 0;
        }
        return 1;
    }

    @Override
    public int updateDiscount(String food, Double price) {
        return menuDao.updateDiscount(price,Integer.valueOf(food));
    }

    @Override
    public int recoveryPrice(Integer id) {
        int tag = menuDao.recoveryPrice(id);
        int result = menuDao.deleteDiscount(id);
        return result;
    }

    @Override
    public List<FoodCatalog> findAllCatalogName() {

        return menuDao.findAllCatalogName();
    }

}
