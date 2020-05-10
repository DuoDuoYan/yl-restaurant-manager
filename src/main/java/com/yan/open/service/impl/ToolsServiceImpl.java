package com.yan.open.service.impl;

import com.yan.open.dao.RobotDao;
import com.yan.open.dao.TableDao;
import com.yan.open.model.FoodCatalog;
import com.yan.open.model.PageData;
import com.yan.open.model.Robot;
import com.yan.open.model.Table;
import com.yan.open.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ToolsServiceImpl implements ToolsService {

    @Autowired
    private TableDao tableDao;

    @Autowired
    private RobotDao robotDao;

    @Override
    public List<Table> findAllTable(PageData pageData) {
        Integer start ;
        Integer end;
        Integer pageNo = pageData.getPageNo();
        Integer pageSize = pageData.getPageSize();
        if(pageNo == null || pageSize == null){
            start = null;
            end = null;
        }else{
            start = (pageNo-1)*pageSize;
            end = pageSize;
        }

        List<Table> table = tableDao.findAllTable(start,end);
        for (int i=0;i<table.size();i++){
            if(table.get(i).getStatus() == 0){
                table.get(i).setStatusName("使用");
            }else{
                table.get(i).setStatusName("禁用");
            }
        }
        return table;
    }

    @Override
    public Integer addTable(Table table) {
        String num = table.getNum();
        Integer tag = 2;
        if(tableDao.findByNum(num) == null){
           tag = tableDao.addTable(num);
        }
        return tag;
    }

    @Override
    public Integer updateTable(Table table) {
        Integer id = table.getId();
        String num = table.getNum();
        Integer status = table.getStatus();
        return tableDao.updateTable(id,num,status);
    }

    @Override
    public Integer countTable() {
        return tableDao.countTable();
    }

    @Override
    public List<Robot> findAllRobot(PageData pageData) {
        Integer start ;
        Integer end;
        Integer pageNo = pageData.getPageNo();
        Integer pageSize = pageData.getPageSize();
        if(pageNo == null || pageSize == null){
            start = null;
            end = null;
        }else{
            start = (pageNo-1)*pageSize;
            end = pageSize;
        }
        List<Robot> robot = robotDao.findAllRobot(start,end);
        for (int i=0;i<robot.size();i++){
            if(robot.get(i).getStatus() == 0){
                robot.get(i).setStatusName("使用");
            }else{
                robot.get(i).setStatusName("禁用");
            }
        }
        return robot;
    }

    @Override
    public Integer countRobot() {
        return robotDao.cout();
    }

    @Override
    public Integer addRobot(Robot robot) {
        String num = robot.getNum();
        if(robotDao.findByNum(num) == null){
            int tag = robotDao.addRobot(num);
            if(tag > 0){
                return 1;
            }else{
                return 0;
            }
        }
        return 2;
    }

    @Override
    public Integer updateRobot(Robot robot) {
        Integer id = robot.getId();
        String num = robot.getNum();
        Integer status = robot.getStatus();
        return robotDao.updateRobot(id,num,status);
    }
}
