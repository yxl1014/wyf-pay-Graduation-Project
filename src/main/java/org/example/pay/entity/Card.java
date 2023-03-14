package org.example.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yxl
 * @date 2023/3/14 下午3:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String card_num;
    private boolean card_status;
}
