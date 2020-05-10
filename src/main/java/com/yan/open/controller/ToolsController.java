package com.yan.open.controller;

import com.yan.open.model.*;
import com.yan.open.service.ToolsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/restaurant")
public class ToolsController {

    @Autowired
    private ToolsService toolsService;

    @ResponseBody
    @RequestMapping("/findAllTable")
    public PageData findAllTAble(PageData pageData){
        PageData data = new PageData();
        if(null == pageData.getPageNo() || null == pageData.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(5);
        }else{
            data.setPageNo(pageData.getPageNo());
            data.setPageSize(pageData.getPageSize());
        }
        data.setTotal(toolsService.countTable());
        List<Table> menu = toolsService.findAllTable(data);

        data.setData(menu);
        return data;
    }

    @RequestMapping("/addTable")
    @ResponseBody
    public Result addTable(Table table){
        Result result = new Result();
        result.setCode(toolsService.addTable(table));
        if(result.getCode() == 0){
            result.setMessage("添加分类失败！");
        }else if(result.getCode() == 1){
            result.setMessage("添加分类成功！");
        }else{
            result.setMessage("该分类已存在！");
        }
        return result;
    }

    @RequestMapping("/updateTable")
    @ResponseBody
    public Result updateTable(Table table){
        Result result = new Result();
        result.setCode(toolsService.updateTable(table));
        if(result.getCode() == 0){
            result.setMessage("修改分类失败！");
        }else{
            result.setMessage("修改分类成功！");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/findAllRobot")
    public PageData findAllRobot(PageData pageData){
        PageData data = new PageData();
        if(null == pageData.getPageNo() || null == pageData.getPageSize()){
            data.setPageNo(1);
            data.setPageSize(5);
        }else{
            data.setPageNo(pageData.getPageNo());
            data.setPageSize(pageData.getPageSize());
        }
        data.setTotal(toolsService.countRobot());
        List<Robot> robot = toolsService.findAllRobot(data);

        data.setData(robot);
        return data;
    }

    @RequestMapping("/addRobot")
    @ResponseBody
    public Result addRobot(Robot robot){
        Result result = new Result();
        result.setCode(toolsService.addRobot(robot));
        if(result.getCode() == 0){
            result.setMessage("添加分类失败！");
        }else if(result.getCode() == 1){
            result.setMessage("添加分类成功！");
        }else{
            result.setMessage("该分类已存在！");
        }
        return result;
    }

    @RequestMapping("/updateRobot")
    @ResponseBody
    public Result updateRobot(Robot robot){
        Result result = new Result();
        result.setCode(toolsService.updateRobot(robot));
        if(result.getCode() == 0){
            result.setMessage("修改分类失败！");
        }else{
            result.setMessage("修改分类已存在！");
        }
        return result;
    }

}
