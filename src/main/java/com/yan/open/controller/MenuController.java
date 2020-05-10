package com.yan.open.controller;

import com.yan.open.model.*;
import com.yan.open.model.Menu;
import com.yan.open.service.MenuService;
import com.yan.open.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/restaurant")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping("/findAllValidMenu")
    public PageData findAllValidMenu(PageData pageData){
        PageData data = new PageData();
        if(null == pageData.getPageNo() || null == pageData.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(5);
        }else{
            data.setPageNo(pageData.getPageNo());
            data.setPageSize(pageData.getPageSize());
        }
        data.setTotal(menuService.countFood());
        List<Menu> menu = menuService.findAllFood(data);

        data.setData(menu);
        return data;
    }

    @ResponseBody
    @RequestMapping("/deleteMenu")
    public Result deleteMenu(String id,HttpServletRequest request){
        Result result = new Result();
        String photo = menuService.findPhotoById(Integer.valueOf(id));
        ImageUtil.deleteFile(photo,request);
        result.setCode(menuService.deleteMenu(Integer.valueOf(id)));
        return result;
    }


    @RequestMapping("/addMenu")
    @ResponseBody
    public Result addMenu(MultipartFile photo, Menu menu, HttpServletRequest request){
        String filePath = null;
        try {
            filePath  = ImageUtil.upload(request,photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu.setImage(filePath);
        Result result = new Result();
        result.setCode(menuService.addMenu(menu));
        if(result.getCode() > 0){
            result.setMessage("添加菜单成功！");
        }else {
            result.setMessage("添加菜单失败！");
        }
        return result;
    }

    @RequestMapping("/updateMenu")
    @ResponseBody
    public Result updateMenu(MultipartFile photo, Menu menu, HttpServletRequest request){
        String filePath = null;
        try {
            filePath  = ImageUtil.upload(request,photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu.setImage(filePath);
        Result result = new Result();
        result.setCode(menuService.updateMenu(menu));
        if(result.getCode()>0){
            result.setMessage("修改菜单成功！");
        }else{
            result.setMessage("修改菜单失败！");
        }
        return result;
    }

    @RequestMapping("/findFoodsByCatalog")
    @ResponseBody
    public List<Menu> findFoodsByCatalog(String catalog){
        List<Menu> data = menuService.findFoodsByCatalog(Integer.valueOf(catalog));
        return data;
    }

    @RequestMapping("/findPriceById")
    @ResponseBody
    public String findPriceById(String id){
        String price = menuService.findPriceById(Integer.valueOf(id));
        return price;
    }

    /**
     * Catalog
     */
    @ResponseBody
    @RequestMapping("/findAllCatalogName")
    public List<FoodCatalog> findAllCatalogName(){
        List<FoodCatalog> list = menuService.findAllCatalogName();
        return list;
    }

    @ResponseBody
    @RequestMapping("/findAllCatalog")
    public PageData findAllValidCatalog(PageData pageData){
        PageData data = new PageData();
        if(null == pageData.getPageNo() || null == pageData.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(5);
        }else{
            data.setPageNo(pageData.getPageNo());
            data.setPageSize(pageData.getPageSize());
        }
        data.setTotal(menuService.countCatalog());
        List<FoodCatalog> foodCatalogs = menuService.findAllCatalog(data);
        data.setData(foodCatalogs);
        return data;
    }

    @RequestMapping("/addCatalog")
    @ResponseBody
    public Result addCatalog(FoodCatalog foodCatalog){
        Result result = new Result();
        result.setCode(menuService.addCatalog(foodCatalog));
        if(result.getCode() > 0){
            result.setMessage("添加分类成功！");
        }else {
            result.setMessage("添加分类失败！");
        }
        return result;
    }

    @RequestMapping("/updateCatalog")
    @ResponseBody
    public Result updateCatalog(FoodCatalog foodCatalog){
        Result result = new Result();
        result.setCode(menuService.updateCatalog(foodCatalog));
        if(result.getCode()>0){
            result.setMessage("修改分类成功！");
        }else{
            result.setMessage("修改分类失败！");
        }
        return result;
    }


    /**
     * 折扣
     */

    @ResponseBody
    @RequestMapping("/findAllDiscount")
    public PageData findAllDiscount(PageData pageData){
        PageData data = new PageData();
        if(null == pageData.getPageNo() || null == pageData.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(5);
        }else{
            data.setPageNo(pageData.getPageNo());
            data.setPageSize(pageData.getPageSize());
        }
        data.setTotal(menuService.countDiscount());
        List<Menu> discounts = menuService.findAllDiscount(data);
        data.setData(discounts);
        return data;
    }

    @RequestMapping("/addDiscount")
    @ResponseBody
    public Result addDiscount(String food,String nowPrice){
        Result result = new Result();
        result.setCode(menuService.addDiscount(food, Double.valueOf(nowPrice)));
        if(result.getCode() == 1){
            result.setMessage("添加折扣成功！");
        }else if(result.getCode() == 0){
            result.setMessage("改折扣已存在！");
        }
        return result;
    }

    @RequestMapping("/updateDiscount")
    @ResponseBody
    public Result updateDiscount(String food,String nowPrice){
        Result result = new Result();
        result.setCode(menuService.updateDiscount(food,Double.valueOf(nowPrice)));
        if(result.getCode()>0){
            result.setMessage("修改折扣成功！");
        }else{
            result.setMessage("修改折扣失败！");
        }
        return result;
    }

   @ResponseBody
    @RequestMapping("/recoveryPrice")
    public Result deleteDiscount(String id){
        Result result = new Result();
        result.setCode(menuService.recoveryPrice(Integer.valueOf(id)));
        return result;
    }
}
