package com.yan.open.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class PageData<T>{

    /** 请求页码 **/
    private Integer pageNo;

    /** 每页条数 **/
    private Integer pageSize;

    /** 总记录数 **/
    private Integer total;

    /** 结果列表 **/
    private List<T> data = Lists.newArrayList();
}
