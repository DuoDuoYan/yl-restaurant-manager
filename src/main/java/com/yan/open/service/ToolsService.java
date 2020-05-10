package com.yan.open.service;

import com.yan.open.model.PageData;
import com.yan.open.model.Robot;
import com.yan.open.model.Table;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ToolsService {
    /**
     * 查看所有餐桌信息
     * @return
     */
    List<Table> findAllTable(PageData pageData);

    /**
     * 计算数据总数
     * @return
     */
    Integer countTable();

    /**
     * 添加餐桌信息
     * @param table
     * @return
     */
    Integer addTable(Table table);

    /**
     * 修改餐桌信息
     * @param table
     * @return
     */
    Integer updateTable(Table table);

    /**
     * 查看所有机器人信息
     * @return
     */
    List<Robot> findAllRobot(PageData pageData);

    /**
     * 计算数据总数
     * @return
     */
    Integer countRobot();

    /**
     * 添加机器人信息
     * @param robot
     * @return
     */
    Integer addRobot(Robot robot);

    /**
     * 修改机器人信息
     * @param robot
     * @return
     */
    Integer updateRobot(Robot robot);
}
