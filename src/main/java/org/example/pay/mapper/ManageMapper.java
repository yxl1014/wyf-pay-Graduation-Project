package org.example.pay.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yxl
 * @date 2023/3/16 上午9:53
 */

@Mapper
public interface ManageMapper {

    @Insert("insert into manage(manage_account,manage_password) values(#{a},#{p})")
    int insertManage(@Param("a") String account, @Param("p") String password);

    @Select("select count(1) from manage where manage_account = #{a}")
    int findManageByAccount(@Param("a") String account);

    @Select("select count(1) from manage where manage_account = #{a} and manage_password = #{p}")
    int findManageByAccountAndPassword(@Param("a") String account, @Param("p") String password);
}
