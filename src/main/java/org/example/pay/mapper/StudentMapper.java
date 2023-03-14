package org.example.pay.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pay.entity.Student;

/**
 * @author yxl
 * @date 2023/3/14 下午2:42
 */

@Mapper
public interface StudentMapper {

    @Select("select count(1) from stu where stu_account = #{account}")
    int findAllUserByAccount(@Param("account") String account);

    @Select("select count(1) from stu where stu_card_num = #{card}")
    int findAllUserByCardNum(@Param("card") String card_num);

    @Insert("insert into stu(stu_account,stu_password,stu_card_num,stu_amount) " +
            "values(#{stu_account},#{stu_password},#{stu_card_num},#{stu_amount})")
    int insertStu(Student student);


    @Select("select * from stu where stu_account = #{account} and stu_password = #{password}")
    Student findUserByAccountAndPassword(@Param("account") String account, @Param("password") String password);

    @Select("select * from stu where stu_account = #{account}")
    Student findUserByAccount(@Param("account") String account);

    @Update("update stu set stu_card_num = #{card_num} where stu_account = #{account}")
    int updateCardNumByAccount(@Param("card_num") String card_num, @Param("account") String account);

    @Update("update stu set stu_amount = #{stu_amount} where stu_account = #{account}")
    int updateAmountByAccount(@Param("stu_amount") float stu_amount, @Param("account") String account);

    @Update("update stu set stu_password = #{stu_password} where stu_account = #{account}")
    int updatePasswordByAccount(@Param("stu_password") String stu_password, @Param("account") String account);
}
