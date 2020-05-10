package com.yan.open.dao;

import com.yan.open.model.Table;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface TableDao {

    /**
     * 添加餐桌信息
     * @param num
     * @return
     */
    @Insert("insert into `table` (num,status) values(#{num},'0')")
    int addTable(@Param("num") String num);

    /**
     * 修改餐桌信息
     * @param num
     * @param id
     * @param status
     * @return
     */
    @Update("update `table` set num = #{num}, status = #{status} where id = #{id}")
    int updateTable(@Param("id")Integer id,@Param("num")String num,@Param("status")Integer status);


    /**
     * 查看所有餐桌信息
     * @return
     */
    @Select("select * from `table` limit #{start},#{end}")
    List<Table> findAllTable(@Param("start")Integer start,@Param("end")Integer end);

    /**
     * 根据num查找table
     */
    @Select("select * from `table` where num = #{num}")
    Table findByNum(@Param("num")String num);

    /**
     * 计算总数
     * @return
     */
    @Select("select COUNT(id) from `table` where status = '0'")
    int countTable();

}
