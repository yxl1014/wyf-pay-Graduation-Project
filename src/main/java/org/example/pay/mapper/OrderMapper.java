package org.example.pay.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.pay.entity.Order;

import java.util.List;

/**
 * @author yxl
 * @date 2023/3/15 下午4:15
 */
@Mapper
public interface OrderMapper {

    @Insert("insert into order_(business_num,parent_num,student_num,amount,create_time) " +
            "values(#{business_num},#{parent_num},#{student_num},#{amount},#{create_time})")
    int insertOrder(Order order);

    @Select("select * from order_ where parent_num = #{p}")
    List<Order> findAllOrderByParentNum(@Param("p") String parent_num);

    @Select("select * from order_ where business_num = #{business_num}")
    List<Order> findAllOrderByBusinessNum(@Param("business_num") String business_num);

    @Select("select * from order_ where order_num = #{order_num}")
    Order findOrderByOrderNum(@Param("order_num") int order_num);

    @Select("select * from order_ where student_num = #{student_num}")
    List<Order> findAllOrderByStuAccount(@Param("student_num") String stu_account);

    @Select("select * from order_")
    List<Order> findAllOrder();
}
