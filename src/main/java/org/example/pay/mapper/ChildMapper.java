package org.example.pay.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pay.entity.Child;

import java.util.List;

/**
 * @author yxl
 * @date 2023/3/15 上午10:49
 */

@Mapper
public interface ChildMapper {

    @Insert("insert into child(business_num,business_name,parent_num,business_password,amount,all_amount,create_time,pic) " +
            "values(#{business_num},#{business_name},#{parent_num},#{business_password},#{amount},#{all_amount},#{create_time},#{pic})")
    int insertChild(Child child);

    @Select("select * from child where business_num = #{business_num}")
    Child findChildByBid(@Param("business_num") String business_num);

    @Select("select pic from child where business_num = #{business_num}")
    byte[] findPicByBid(@Param("business_num") String business_num);

    @Select("select count(1) form child where business_num = #{business_num} and business_password = #{business_password}")
    int findAllChildByNP(@Param("business_num") String business_num,@Param("business_password") String business_password);

    @Select("select * from child where parent_num = #{parent_num}")
    List<Child> findAllChildByPid(@Param("parent_num") String parent_num);

    @Delete("delete from child where business_num = #{business_num}")
    int deleteChildByBid(@Param("business_num") String business_num);

    @Update("update child set business_password = #{pwd} where business_num = #{num}")
    int updatePwdByNum(@Param("pwd") String pwd, @Param("num") String num);


    @Update("update child set amount = #{amount} where business_num = #{business_num}")
    int updateAmountByAccount(@Param("amount") float amount, @Param("business_num") String account);

    @Update("update child set all_amount = #{all_amount} where business_num = #{business_num}")
    int updateAllAmountByAccount(@Param("all_amount") float all_amount, @Param("business_num") String account);

}
