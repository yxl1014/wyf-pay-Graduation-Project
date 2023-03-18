package org.example.pay.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pay.entity.Apply;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author yxl
 * @date 2023/3/15 下午3:40
 */

@Mapper
public interface ApplyMapper {
    @Insert("insert into apply(account,type,create_time,status) values(#{account},#{type},#{create_time},#{status},#{msg})")
    int insertApply(@Param("account") String account, @Param("type") int type,
                    @Param("create_time") Timestamp create_time, @Param("status") int status, @Param("msg") String msg);

    @Select("select * from apply where account = #{account} and type = #{type}")
    Apply findApplyByAccountAndType(@Param("account") String account, @Param("type") int type);
    @Update("delete from apply where account = #{account} and type = #{type}")
    int removeApplyByAccountAndType(@Param("account") String account, @Param("type") int type);

    @Select("select * from apply")
    List<Apply> findAllApply();

    @Select("select * from apply where type = #{type}")
    List<Apply> findAllApplyByType(@Param("type")int type);

    @Select("select * from apply where aid = #{aid}")
    Apply findApplyById(@Param("aid")int type);
}
