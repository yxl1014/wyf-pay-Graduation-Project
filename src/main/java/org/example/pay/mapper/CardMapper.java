package org.example.pay.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pay.entity.Card;

/**
 * @author yxl
 * @date 2023/3/14 下午3:19
 */
@Mapper
public interface CardMapper {

    @Insert("insert into card(card_num,card_status) values(#{card_num},#{card_status})")
    int insertCard(Card card);

    @Select("select card_status from card where card_num = #{card_num}")
    Boolean findCardStatusByCardNum(String card_num);

    @Delete("delete from card where card_num = #{card_num}")
    int deleteCardByCardNum(@Param("card_num") String card_num);
}
