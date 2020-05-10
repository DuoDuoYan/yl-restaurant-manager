package com.yan.open.service.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.yan.open.dao.MenuDao;
import com.yan.open.dao.SaleDao;
import com.yan.open.model.FoodCatalog;
import com.yan.open.model.Sales;
import com.yan.open.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleDao saleDao;

    @Override
    public List<List<?>> findByMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取某个月的销售数据
        List<Sales> lists = Lists.newArrayList();
        Calendar calendar = Calendar.getInstance();
        String[] str;
        if(StringUtils.isEmpty(date)){
            lists = saleDao.findSalesBy(sdf.format(new Date()));
            str = sdf.format(new Date()).split("-");
        }else{
            lists = saleDao.findSalesBy(date);
            str = date.split("-");
        }
        //获取当月的天数
        int year = Integer.valueOf(str[0]);
        int month = Integer.valueOf(str[1]);
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month+1);
        int days = calendar.getActualMaximum(Calendar.DATE);
        //获取食品分类
        List<Object> catalogList = saleDao.findAllCatalog();
        List<Map<String,Object>> catalogListMap = Lists.newArrayList();
        for(int i=0;i<catalogList.size();i++){
            Map<String,Object> catalogMap = new HashMap<>();
            catalogMap.put("catalog",catalogList.get(i));
            catalogListMap.add(catalogMap);
        }
        List<List<Map<String,Object>>> dataList= Lists.newArrayList();
        for(int i=1;i<=days;i++){
            List<Map<String,Object>> dayDataList = Lists.newArrayList();
            for(int j=0;j<lists.size();j++){
                if(String.valueOf(i).equals(lists.get(j).getDay())){
                    Map<String,Object> map = new HashMap<>();
                    map.put("catalog",lists.get(j).getCatalogName());
                    map.put("price",lists.get(j).getPrice());
                    dayDataList.add(map);
                }
            }
            //处理销售数据，转化成list<Map>格式
            List<Map<String,Object>> resultMap = catalogListMap.stream().map(catalogMap -> {
                catalogMap.put("price", 0);
                dayDataList.stream().filter(dataMap -> Objects.equals(dataMap.get("catalog"), catalogMap.get("catalog")))
                        .forEach(s -> catalogMap.put("price", s.get("price")));
                return catalogMap;
            }).collect(Collectors.toList());
            //map为引用传递，所以在将map赋值给其他变量前，先将map转化成string类型，然后string转map，最后赋值，就不会导致值被覆盖的问题
            List<Map<String,Object>> result = new ArrayList<>();
            for(int m=0;m<resultMap.size();m++){
                String string = resultMap.get(m).toString();
                Gson gson = new Gson();
                Map<String,Object> map = new HashMap<>();
                map = gson.fromJson(string,map.getClass());
                result.add(map);
            }
            dataList.add(result);
        }
        //将某个月的天数、食品分类、总价一次存放在list中返回
        List<List<?>> dataLists = Lists.newArrayList();
        List day = Lists.newArrayList();
        day.add(days);
        dataLists.add(day);
        dataLists.add(catalogList);
        for(int i=0;i<catalogList.size();i++){
            List<Object> priceList = Lists.newArrayList();
            for(int j=0;j<dataList.size();j++){
               priceList.add(dataList.get(j).get(i).get("price"));
            }
            dataLists.add(priceList);
        }
        return dataLists;
    }
}
