package com.yan.open;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.yan.open.controller.UserController;
import com.yan.open.dao.MenuDao;
import com.yan.open.dao.SaleDao;
import com.yan.open.dao.UserDao;
import com.yan.open.model.Foods;
import com.yan.open.model.Order;
import com.yan.open.model.OrderDetail;
import com.yan.open.model.Sales;
import com.yan.open.service.MenuService;
import com.yan.open.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class UserTest extends BaseJunit4Test {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserController userController;

    @Autowired
    private MenuDao dao;

    @Autowired
    private SaleDao saleDao;

    @Autowired
    private MenuService menuService;
    private Order order;

    @Test
    public void testSales(){
        String date = "2020-4-1";
        List<Sales> lists = saleDao.findSalesBy(date);
        List<Object> catalogList = saleDao.findAllCatalog();
        List<Map<String,Object>> catalogListMap = Lists.newArrayList();
        for(int i=0;i<catalogList.size();i++){
            Map<String,Object> catalogMap = new HashMap<>();
            catalogMap.put("catalog",catalogList.get(i));
            catalogListMap.add(catalogMap);
        }
        List dataList = new ArrayList<>();
        for(int i=1;i<=3;i++){
            List<Map<String,Object>> dayDataList = Lists.newArrayList();
            for(int j=0;j<lists.size();j++){
                if(String.valueOf(i).equals(lists.get(j).getDay())){
                    Map<String,Object> map = new HashMap<>();
                    map.put("catalog",lists.get(j).getCatalogName());
                    map.put("price",lists.get(j).getPrice());
                    dayDataList.add(map);
                }
            }
            List<Map<String,Object>> resultMap = catalogListMap.stream().map(catalogMap ->{
                                                    catalogMap.put("price",null);
                                                    dayDataList.stream().filter(dataMap->Objects.equals(catalogMap.get("catalog"), dataMap.get("catalog")))
                                                                .forEach(s -> catalogMap.put("price",s.get("price")));
                                                    return catalogMap;
                                                }).collect(Collectors.toList());
            System.out.println(i+":"+resultMap);
            List<Map> result = new ArrayList<>();
            for(int m=0;m<resultMap.size();m++){
                String string = resultMap.get(m).toString();
                Gson gson = new Gson();
                Map<String,Object> map = new HashMap<>();
                map = gson.fromJson(string,map.getClass());
                result.add(map);
            }
            dataList.add(result);
        }
        System.out.println("===========================");
        dataList.stream().forEach(s -> System.out.println(s));
    }
    @Test
    public void hashPwd(){
        List<String> names = Lists.newArrayList();
        List<Object> salts = Lists.newArrayList();
        names.add("111");
//        for(int i=0;i<names.size();i++){

//        ByteSource salt = ByteSource.Util.bytes(user.getPhone());
//        SimpleHash hash = new SimpleHash("MD5",user.getPassword(),salt,1024);
            ByteSource salt = ByteSource.Util.bytes("1111");
            SimpleHash hash = new SimpleHash("MD5","123",ByteSource.Util.bytes("1111"),1024);
            System.out.println(salt);
            System.out.println(hash);
//        }
    }

    @Test
    public void testUserMenu(){

        String str = "[{'id':167,'food':'双人牛排套餐','info':'日式温泉蛋、土豆水果沙拉、海藻沙拉、澳洲草饲西冷扒','price':'138.00','nowPrice':null,'quantity':1000,'selled':0,'catalogName':'精品套餐'},{'id':168,'food':'单人牛排套餐','info':'日式温泉蛋、土豆水果沙拉、海藻沙拉、澳洲草饲西冷扒','price':'68.00','nowPrice':null,'quantity':1000,'selled':0,'catalogName':'精品套餐'},{'id':169,'food':'单人午餐','info':'经典拌饭、小菜、汤','price':'28.00','nowPrice':null,'quantity':1000,'selled':0,'catalogName':'精品套餐'}]";

        String strs = str.substring(1,str.length()-1);
        String[] arr = strs.split("},");
        List<String> list = Lists.newArrayList();
        for (int i =0;i<arr.length-1;i++) {
            String aa = arr[i]+"}";
            list.add(aa);
        }
        list.add(arr[arr.length-1]);
//        for(int j=0;j<list.size();j++){
//            Foods foods = JSONObject.parseObject(list.get(j),Foods.class);
//            log.info("Foods:"+foods);
//        }
    }
    @Test
    public void test2(){
        int[] arr = {1, 2, 5, 5, 5, 5, 6, 6, 7, 2, 9, 2};

        Map<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < arr.length; i ++){
            if(map.containsKey(arr[i])){
                map.put(arr[i], map.get(arr[i])+1);
            }else{
                map.put(arr[i], 1);
            }
        }

        for (Map.Entry<Integer,Integer> entry : map.entrySet()){
            System.out.println("元素 "+entry.getKey());
//            if(entry.getValue()>1){
//                System.out.println("元素 "+entry.getKey()+" 重复出现"+entry.getValue()+"次");
//            }else{
//                System.out.println("元素 "+entry.getKey()+" 只出现1次 无重复");
//            }
        }

        System.out.println(new Date().getTime());

    }
    @Test
    public void testOrder(){

        Gson gson1 = new Gson();
        String str = gson1.toJson(userService.getOnOrders());

        System.out.println(userService.getOnOrders());
        System.out.println(str);

        //gson
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonParser().parse(str).getAsJsonArray();
        for(int i=0;i<jsonArray.size();i++){
            Order order = gson.fromJson(jsonArray.get(i),Order.class);
            System.out.println(order);
        }
    }

        @Test
        public void testsd(){
            Order order = new Order();
            order.setCooker("44");
            order.setRobotNum("2");
            order.setId(90);
//            userDao.updateOrderCookerStatus(order.getCooker(),1,order.getId());
//            userDao.updateOrderRobotStatus(order.getRobotNum(),2,order.getId());
//            userDao.updateOrderStatus(3,order.getId());
            log.info("orders:"+userDao.getOrder("111"));
        }



}
