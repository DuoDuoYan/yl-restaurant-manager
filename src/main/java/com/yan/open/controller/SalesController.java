package com.yan.open.controller;

import com.google.common.collect.Lists;
import com.yan.open.model.Sales;
import com.yan.open.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/restaurant")
public class SalesController {

    @Autowired
    private SaleService saleService;

    @RequestMapping("findByDate")
    @ResponseBody
    public List<List<?>> findByMonth(String date){
        List<List<?>> lists = saleService.findByMonth(date);
        return lists;
    }


}
