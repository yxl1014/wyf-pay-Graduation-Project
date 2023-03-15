package org.example.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author yxl
 * @date 2023/3/15 下午4:03
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply {
    private int aid;
    private String account;
    private int type;
    private Timestamp create_time;
    private int status;
    private String msg;
}
