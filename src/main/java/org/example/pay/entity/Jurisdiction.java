package org.example.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yxl
 * @date 2023/3/15 下午2:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jurisdiction {
    private String child_num;
    private boolean using_j;
    private boolean changeName_j;
    private boolean changePassword_j;
}
