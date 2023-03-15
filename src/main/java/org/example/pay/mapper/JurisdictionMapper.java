package org.example.pay.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}
