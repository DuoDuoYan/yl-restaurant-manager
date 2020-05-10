package com.yan.open.dao;

import com.yan.open.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RechargeDao {

    @Select("select * from user where phone = #{phone}\n")
    User findByPhone(@Param("phone")String phone);

    @Update("update user set balance = #{balance} where phone = #{phone}\n")
    Integer addBalance(@Param("phone")String phone,@Param("balance")Double balance);
}
