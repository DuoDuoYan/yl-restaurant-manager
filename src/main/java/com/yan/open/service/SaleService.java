package com.yan.open.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface SaleService {

    /**
     * 查看某个月的销售情况
     */
    List<List<?>> findByMonth(String date);

}
