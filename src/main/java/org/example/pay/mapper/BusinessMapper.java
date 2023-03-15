package org.example.pay.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pay.entity.Business;

/**
 * @author yxl
 * @date 2023/3/15 上午10:49
 */

@Mapper
public interface BusinessMapper {

    @Select("select count(1) form business where id_num = #{id}")
    int findAllBusinessByIdNum(@Param("id") String id_num);

    @Select("select * from business where id_num = #{id} and people = #{people}")
    Business findBusinessByIdAndPeople(@Param("id") String id_num, @Param("people") String people);

    @Select("select * from business where parent_num = #{parent_num}")
    Business findBusinessByPn(@Param("parent_num") String parent_num);

    @Insert("insert into business(id_num,people,phone_num,shop_name,amount,parent_num,parent_password,create_time) " +
            "values(#{id_num},#{people},#{phone_num},#{shop_name},#{amount},#{parent_num},#{parent_password},#{create_time})")
    int insertBusiness(Business business);

    @Select("select count(1) form business where parent_num = #{parent_num} and parent_num = #{parent_password}")
    int findAllBusinessByNP(@Param("parent_num") String parent_num, @Param("parent_password") String parent_password);

    @Update("update business set parent_password = #{pwd} where parent_num = #{num}")
    int updatePwdByNum(@Param("pwd") String pwd, @Param("num") String num);
}
