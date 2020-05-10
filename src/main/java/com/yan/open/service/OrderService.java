package com.yan.open.service;

import com.yan.open.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    /**
     * 查看所有订单信息
     * @return
     */
    List<Order> findAllOrders(PageData pageData,String startTime,String endTime,String condition);

    /**
     * 查看某个订单详情
     * @param num
     * @return
     */
    List<OrderDetail> findOrderDetailById(int num);

    /**
     * 计算数据总数
     * @return
     */
    Integer count();


}
