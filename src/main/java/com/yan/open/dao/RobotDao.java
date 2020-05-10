package com.yan.open.dao;

import com.yan.open.model.Robot;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface RobotDao {

    /**
     * 添加机器人信息
     * @param num
     * @return
     */
    @Insert("insert into robot (num,status) values(#{num},'0')")
    int addRobot(@Param("num") String num);

    /**
     * 修改Robot信息
     * @param num
     * @param id
     * @param status
     * @return
     */
    @Update("update robot set num = #{num}, status = #{status} where id = #{id}")
    int updateRobot(@Param("id")Integer id,@Param("num")String num,@Param("status")Integer status);

    /**
     * 根据num查看Robot信息
     * @param num
     * @return
     */
    @Select("select * from robot where num = #{num}")
    Robot findByNum(@Param("num")String num);

    /**
     * 查看所有robot信息
     * @return
     */
    @Select("select * from robot limit #{start},#{end}")
    List<Robot> findAllRobot(@Param("start")Integer start,@Param("end")Integer end);

    @Select("select count(id) from robot where status = '0'")
    int cout();
}
