package com.yan.open.service.impl;

import com.yan.open.dao.OrderDao;
import com.yan.open.model.Order;
import com.yan.open.model.OrderDetail;
import com.yan.open.model.PageData;
import com.yan.open.model.Sales;
import com.yan.open.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.xml.ws.Action;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<OrderDetail> findOrderDetailById(int num) {
        return orderDao.findByOrderNum(num);
    }

    @Override
    public List<Order> findAllOrders(PageData pageData,String startTime,String endTime,String condition) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Integer start ;
        Integer end;
        Long startDate = 0L;
        Long endDate = 0L;
        Integer pageNo = pageData.getPageNo();
        Integer pageSize = pageData.getPageSize();
        if(pageNo == null || pageSize == null){
            start = null;
            end = null;
        }else{
            start = (pageNo-1)*pageSize;
            end = pageSize;
        }

        try {
            if(StringUtils.isEmpty(startTime)){
                startDate = null;
            }else{
                startDate = sdf.parse(startTime).getTime();
            }
            if(StringUtils.isEmpty(endTime)){
                endDate = null;
            }else{
                endDate = sdf.parse(endTime).getTime();
            }
            if(!StringUtils.isEmpty(condition)){
               condition = condition.trim();
            }else{
                condition = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Order> orders = orderDao.findOrders(start,end,startDate,endDate,condition);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getOrderStatus() == 0){
                orders.get(i).setStatus("已完成");
            }else{
                orders.get(i).setStatus("未完成");
            }
            Date date = new Date(orders.get(i).getOrderDate());
            orders.get(i).setDate(sd.format(date));
        }
        return orders;
    }

    @Override
    public Integer count() {
        return orderDao.countOrders();
    }
}
