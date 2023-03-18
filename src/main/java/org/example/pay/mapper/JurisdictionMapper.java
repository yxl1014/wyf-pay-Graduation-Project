package org.example.pay.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pay.entity.Jurisdiction;

/**
 * @author yxl
 * @date 2023/3/15 下午2:49
 */

@Mapper
public interface JurisdictionMapper {

    @Insert("insert into jurisdiction(child_num) values(#{child_num})")
    int insertJurisdiction(@Param("child_num") String child_num);

    @Select("select * from jurisdiction where child_num = #{child_num}")
    Jurisdiction findJurisdictionByChildNum(@Param("child_num") String child_num);

    @Update("update jurisdiction set changeName_j = #{status} where child_num = #{account}")
    int updateChangeNameByChildNum(@Param("status")int status,@Param("account") String account);

    @Update("update jurisdiction set changePassword_j = #{status} where child_num = #{account}")
    int updateChangePasswordByChildNum(@Param("status")int status,@Param("account") String account);

    @Update("update jurisdiction set using_j = #{status} where child_num = #{account}")
    int updateUsingByChildNum(@Param("status")int status,@Param("account") String account);
}
