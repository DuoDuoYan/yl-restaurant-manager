package com.yan.open.controller;

import com.yan.open.model.Order;
import com.yan.open.model.OrderDetail;
import com.yan.open.model.PageData;
import com.yan.open.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/restaurant")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/findAllOrder")
    @ResponseBody
    public PageData findAllOrder(PageData pageData,String startTime,String endTime,String condition){
        PageData data = new PageData();
        if(null == pageData.getPageNo() || null == pageData.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(10);
        }else{
            data.setPageNo(pageData.getPageNo());
            data.setPageSize(pageData.getPageSize());
        }
        data.setTotal(orderService.count());
        List<Order> orders = orderService.findAllOrders(data,startTime,endTime,condition);
        data.setData(orders);
        return data;
    }

    @RequestMapping("/findOrderByNum")
    @ResponseBody
    public List<OrderDetail> findOrdersBy(String num){
        List<OrderDetail> list = orderService.findOrderDetailById(Integer.valueOf(num));
        log.info("list:"+list);
        return list;
    }

}
